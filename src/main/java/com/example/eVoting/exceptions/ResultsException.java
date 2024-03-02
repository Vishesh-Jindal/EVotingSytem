package com.example.eVoting.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ResultsException extends RuntimeException{
    public ResultsException(String message){
        super(message);
    }
}
