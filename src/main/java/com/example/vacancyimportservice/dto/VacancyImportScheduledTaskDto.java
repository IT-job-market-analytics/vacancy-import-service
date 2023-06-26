package com.example.vacancyimportservice.dto;

import lombok.Data;

@Data
public class VacancyImportScheduledTaskDto {
    int pageSize;
    int pageNumber;
    String query;
}
