package com.example.eVoting.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class VotingException extends RuntimeException {
    public VotingException(String message){
        super(message);
    }
}
