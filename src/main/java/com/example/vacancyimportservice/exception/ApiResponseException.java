package com.example.vacancyimportservice.exception;

public class ApiResponseException extends RuntimeException{
    public ApiResponseException(String message){
        super(message);
    }
}
