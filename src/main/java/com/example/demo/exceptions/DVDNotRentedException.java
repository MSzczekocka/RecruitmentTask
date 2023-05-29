package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DVDNotRentedException extends RuntimeException{
    public DVDNotRentedException() {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "DVD is Available");
    }

}
