package com.unibite.unibit_backend.exceptions;

public class BadRequestException extends  RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}
