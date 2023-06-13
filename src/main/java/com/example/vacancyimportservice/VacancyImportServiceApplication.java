package com.example.vacancyimportservice;

import com.example.vacancyimportservice.service.ProduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class VacancyImportServiceApplication {

	@Autowired
	private ProduceService produceService;
	public static void main(String[] args) {
		SpringApplication.run(VacancyImportServiceApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void test(){
		RabbitmqDto rabbitmqDto =  new RabbitmqDto();
		rabbitmqDto.setMessage("Test message");
		produceService.produceAnswer(rabbitmqDto);
	}
}
