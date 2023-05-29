package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DVDNotFoundException extends RuntimeException{
    public DVDNotFoundException() {
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "DVD not found");
    }
}
