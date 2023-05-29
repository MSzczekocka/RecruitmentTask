package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClientNotFoundexception extends RuntimeException{
    public ClientNotFoundexception() {
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Client not found");
    }
}
