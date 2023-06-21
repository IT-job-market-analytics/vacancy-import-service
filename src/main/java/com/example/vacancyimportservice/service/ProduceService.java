package com.example.vacancyimportservice.service;

import com.example.vacancyimportservice.dto.RabbitmqDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProduceService {
    private final RabbitTemplate rabbitTemplate;

    public ProduceService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void produceAnswer(RabbitmqDto rabbitmqDto) {
        rabbitTemplate.convertAndSend(rabbitmqDto);
    }
}
