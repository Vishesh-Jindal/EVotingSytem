package com.example.eVoting.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ResultsNotAllowedException extends RuntimeException{
    public ResultsNotAllowedException(String message){
        super(message);
    }
}
