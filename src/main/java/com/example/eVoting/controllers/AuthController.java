package com.example.eVoting.controllers;

import com.example.eVoting.dto.JwtResponse;
import com.example.eVoting.dto.LoginRequest;
import com.example.eVoting.exceptions.NotFoundException;
import com.example.eVoting.services.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> doLogin(@RequestBody @Valid LoginRequest loginRequest, BindingResult bindingResult){
        log.info("Request recevied to login user:"+loginRequest.getUsername());
        if(bindingResult.hasErrors()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        try{
            JwtResponse jwtResponse = authService.doLogin(loginRequest);
            return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
        }catch (BadCredentialsException badCredentialsException){
            throw badCredentialsException;
        }catch (UsernameNotFoundException usernameNotFoundException){
            throw usernameNotFoundException;
        }
    }
    @PostMapping("/logout/{username}")
    public ResponseEntity<String> doLogout(@PathVariable("username") String username){
        log.info("Request recevied to logout user:"+username);
        try {
            authService.doLogout(username);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully logged out user:"+username);
        } catch (NotFoundException notFoundException){
            throw notFoundException;
        }
    }
}
