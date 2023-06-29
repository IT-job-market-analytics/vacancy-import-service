package com.example.vacancyimportservice.exception;

public class HhApiQuotaExceededException extends RuntimeException {
    public HhApiQuotaExceededException(String message){
        super(message);
    }
}
