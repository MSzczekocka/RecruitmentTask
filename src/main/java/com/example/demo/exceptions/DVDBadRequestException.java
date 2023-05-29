package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DVDBadRequestException extends RuntimeException{
    public  DVDBadRequestException() {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "DVDBadRequestException");
    }

}
