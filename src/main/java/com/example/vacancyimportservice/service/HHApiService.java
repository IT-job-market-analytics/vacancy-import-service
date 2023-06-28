package com.example.vacancyimportservice.service;

import com.example.vacancyimportservice.dto.VacancyImportScheduledTaskDto;
import com.example.vacancyimportservice.dto.hh.Vacancies;
import com.example.vacancyimportservice.exception.ApiRequestException;
import com.example.vacancyimportservice.exception.ApiResponseException;
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

    public HHApiService(ProduceService produceService) {
        this.produceService = produceService;
    }

    void query(VacancyImportScheduledTaskDto query) {
        log.info("Receive schedule query: " + query);
        try {
            Vacancies vacancies = requestToApi(query);
            log.info("Returned vacancies count:  " + vacancies.getItems().size());
            vacancies.getItems().forEach(item -> {
                item.setQuery(query.getQuery());
                produceService.publishVacancy(item);
            });

        } catch (ApiResponseException e) {
            log.info(e.getMessage());
            throw e;
        } catch (ApiRequestException e) {
            log.error(e.getMessage());
        }
    }

    private Vacancies requestToApi(VacancyImportScheduledTaskDto query) {
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("text", query.getQuery())
                .queryParam("page", query.getPageNumber())
                .queryParam("per_page", query.getPageSize());

        log.info("Request to HH API");
        try {
            ResponseEntity<Vacancies> response = restTemplate.getForEntity(builder.toUriString(), Vacancies.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Request completed successfully");
                return response.getBody();
            } else {
                log.info("Request failed. Response code: " + response.getStatusCode());
                throw new ApiResponseException("Ошибка при выполнении запроса: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException.BadRequest e) {
            log.info("Request failed. Bad request!");
            throw new ApiRequestException(e.getMessage());
        } catch (HttpClientErrorException.Forbidden e) {
            log.info("Request failed. Too many request request!");
            throw new ApiRequestException(e.getMessage());
        }
    }

}
