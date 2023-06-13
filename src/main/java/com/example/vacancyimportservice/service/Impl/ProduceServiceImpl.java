package com.example.vacancyimportservice.service.Impl;

import com.example.vacancyimportservice.RabbitmqDto;
import com.example.vacancyimportservice.service.ProduceService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProduceServiceImpl implements ProduceService {
    @Value("${rabbitmq.topic}")
    private String topic;

    @Value("${rabbitmq.routing_key}")
    private String routing_key;

    private final RabbitTemplate rabbitTemplate;

    public ProduceServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produceAnswer(RabbitmqDto rabbitmqDto) {
        rabbitTemplate.convertAndSend(topic, routing_key, rabbitmqDto);
    }
}
