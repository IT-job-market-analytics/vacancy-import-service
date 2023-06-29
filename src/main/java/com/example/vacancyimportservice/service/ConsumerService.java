package com.example.vacancyimportservice.service;

import com.example.vacancyimportservice.dto.VacancyImportScheduledTaskDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    private final HHApiService hhApiService;

    public ConsumerService(HHApiService hhApiService) {
        this.hhApiService = hhApiService;
    }

    @RabbitListener(queues = "${rabbitmq.vacancy_import_scheduled_tasks_queue}")
    public void consumeScheduledQueue(VacancyImportScheduledTaskDto vacancyImportScheduledTaskDto) {
        hhApiService.query(vacancyImportScheduledTaskDto);
    }
}
