package com.example.vacancyimportservice.exception;

public class HhApiQuotaExceededException extends HhApiException {
    public HhApiQuotaExceededException(String message){
        super(message);
    }
}
