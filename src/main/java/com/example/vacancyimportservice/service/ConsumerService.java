package com.example.vacancyimportservice.service;

import com.example.vacancyimportservice.dto.VacancyImportScheduledTaskDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
    @RabbitListener(queues = "vacancy-import-scheduled-tasks-queue")
    public void consumeScheduledQueue(VacancyImportScheduledTaskDto taskDto) {
        System.out.println(taskDto);
    }
}
