package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DVDNotAvailableException extends RuntimeException{
    public DVDNotAvailableException() {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "DVD not Available");
    }
}
