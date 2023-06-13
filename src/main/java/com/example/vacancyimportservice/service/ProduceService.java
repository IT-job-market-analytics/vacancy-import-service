package com.example.vacancyimportservice.service;

import com.example.vacancyimportservice.RabbitmqDto;

public interface ProduceService {
    void produceAnswer(RabbitmqDto rabbitmqDto);
}
