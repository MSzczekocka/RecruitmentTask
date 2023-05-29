package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClientBadRequestException extends RuntimeException{

    public ClientBadRequestException() {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ClientBadRequestException");
    }
}
