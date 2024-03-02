package com.example.eVoting.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class VotingNotAllowedException extends RuntimeException {
    public VotingNotAllowedException(String message){
        super(message);
    }
}
