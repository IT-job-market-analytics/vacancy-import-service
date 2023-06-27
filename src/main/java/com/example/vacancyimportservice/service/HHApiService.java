package com.example.vacancyimportservice.service;

import com.example.vacancyimportservice.dto.VacancyImportScheduledTaskDto;
import com.example.vacancyimportservice.dto.hh.Vacancies;
import com.example.vacancyimportservice.exception.ApiResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
public class HHApiService {
    @Value("${api.url}")
    private String url;

    void query(VacancyImportScheduledTaskDto query) {
        try {
            Vacancies vacancies = requestToApi(query);
            System.out.println(vacancies.getItems().size());
        } catch (ApiResponseException e) {

        }
    }

    private Vacancies requestToApi(VacancyImportScheduledTaskDto query) {
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("text", query.getQuery())
                .queryParam("page", query.getPageNumber())
                .queryParam("per_page", query.getPageSize());

        ResponseEntity<Vacancies> response = restTemplate.getForEntity(builder.toUriString(), Vacancies.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new ApiResponseException("Ошибка при выполнении запроса: " + response.getStatusCode());
        }
    }

}
