package com.example.eVoting.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenNotValidException extends RuntimeException{
    public TokenNotValidException(String message){
        super(message);
    }
}
