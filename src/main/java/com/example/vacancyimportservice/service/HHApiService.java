package com.example.vacancyimportservice.service;

import com.example.vacancyimportservice.dto.VacancyImportScheduledTaskDto;
import com.example.vacancyimportservice.dto.hh.Vacancies;
import com.example.vacancyimportservice.exception.HhApiBadRequestException;
import com.example.vacancyimportservice.exception.HhApiQuotaExceededException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
public class HHApiService {
    @Value("${api.url}")
    private String url;

    private final ProduceService produceService;

    private final QuotaService quotaService;

    public HHApiService(ProduceService produceService, QuotaService quotaService) {
        this.produceService = produceService;
        this.quotaService = quotaService;
    }

    void query(VacancyImportScheduledTaskDto query) {
        log.info("Receive scheduled query: " + query);
        try {
            quotaService.requestQuota();

            log.debug("Has quota");
            Vacancies vacancies = requestToApi(query);
            log.debug("Returned vacancies count: " + vacancies.getItems().size());

            log.debug("Setting query to DTOs and publishing to the next queue");
            vacancies.getItems().forEach(item -> {
                item.setQuery(query.getQuery());
                produceService.publishVacancy(item);
            });

            log.info("Query handled successfully");
        } catch (HhApiQuotaExceededException e) {
            log.warn("We exceeded HH.ru API quota, swallowing exception, so the message will not be re-queued");
        } catch (HhApiBadRequestException e) {
            log.warn("Bad request to HH.ru API, swallowing exception, so the message will not be re-queued");
        } catch (Exception e) {
            log.warn("Unknown exception occurred, propagating it, so the message will be re-queued: " + e.getMessage());
            throw e;
        }
    }

    private Vacancies requestToApi(VacancyImportScheduledTaskDto query) {
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("text", query.getQuery())
                .queryParam("page", query.getPageNumber())
                .queryParam("per_page", query.getPageSize());

        log.debug("Requesting vacancies from HH API");
        try {
            ResponseEntity<Vacancies> response = restTemplate.getForEntity(builder.toUriString(), Vacancies.class);

            log.debug("Request completed successfully");
            return response.getBody();
        } catch (HttpClientErrorException.Forbidden e) {
            log.error(e.getMessage());
            throw new HhApiQuotaExceededException(e.getMessage());
        } catch (HttpClientErrorException.BadRequest e) {
            log.error(e.getMessage());
            throw new HhApiBadRequestException(e.getMessage());
        }
    }
}
