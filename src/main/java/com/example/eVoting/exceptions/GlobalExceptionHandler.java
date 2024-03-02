package com.example.eVoting.exceptions;

import com.example.eVoting.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse.builder()
                                .errorCode(HttpStatus.BAD_REQUEST.value())
                                .message(ex.getMessage())
                                .build()
                );
    }
    @ExceptionHandler(VotingNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleVotingNotAllowedException(VotingNotAllowedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(
                        ErrorResponse.builder()
                                .errorCode(HttpStatus.FORBIDDEN.value())
                                .message(ex.getMessage())
                                .build()
                );
    }
    @ExceptionHandler(ResultsNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleResultsNotAllowedException(ResultsNotAllowedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(
                        ErrorResponse.builder()
                                .errorCode(HttpStatus.FORBIDDEN.value())
                                .message(ex.getMessage())
                                .build()
                );
    }
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyExistsException(AlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.builder()
                                .errorCode(HttpStatus.CONFLICT.value())
                                .message(ex.getMessage())
                                .build()
                );
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        ErrorResponse.builder()
                                .errorCode(HttpStatus.UNAUTHORIZED.value())
                                .message(ex.getMessage())
                                .build()
                );
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse.builder()
                                .errorCode(HttpStatus.BAD_REQUEST.value())
                                .message(ex.getMessage())
                                .build()
                );
    }
}
