package com.example.vacancyimportservice.service;

import com.example.vacancyimportservice.dto.hh.Item;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProduceService {
    private final RabbitTemplate rabbitTemplate;

    public ProduceService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void produceAnswer(Item item) {
        rabbitTemplate.convertAndSend(item);
    }
}
