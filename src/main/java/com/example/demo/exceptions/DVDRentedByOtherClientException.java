package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DVDRentedByOtherClientException extends RuntimeException{
    public DVDRentedByOtherClientException() {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "DVD rented by different client");
    }
}
