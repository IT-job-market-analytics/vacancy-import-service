package com.example.vacancyimportservice.service;

import com.example.vacancyimportservice.dto.hh.Vacancy;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProduceService {
    @Value("${rabbitmq.vacancies_imported_exchange}")
    private String vacanciesImportedExchange;
    private final RabbitTemplate rabbitTemplate;

    public ProduceService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishVacancy(Vacancy vacancy) {
        rabbitTemplate.convertAndSend(vacanciesImportedExchange,"",vacancy);
    }
}
