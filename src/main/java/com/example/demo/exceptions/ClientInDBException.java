package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClientInDBException extends RuntimeException {
    public ClientInDBException() {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client is in DB");
    }
}
