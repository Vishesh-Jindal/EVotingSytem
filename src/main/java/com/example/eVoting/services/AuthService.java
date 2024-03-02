package com.example.eVoting.services;

import com.example.eVoting.dto.JwtResponse;
import com.example.eVoting.dto.LoginRequest;
import com.example.eVoting.entities.User;
import com.example.eVoting.exceptions.NotFoundException;
import com.example.eVoting.jwt.JwtAuthenticationHelper;
import com.example.eVoting.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @Autowired
    JwtAuthenticationHelper jwtAuthenticationHelper;

    public JwtResponse doLogin(LoginRequest loginRequest) throws BadCredentialsException, UsernameNotFoundException {
        this.doAuthentication(loginRequest.getUsername(), loginRequest.getPassword());
        User user = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtAuthenticationHelper.generateToken(user);
        user.setAuthToken(token);
        User updatedUser = userRepository.save(user);
        return JwtResponse.builder()
                .username(updatedUser.getUsername())
                .jwtToken(updatedUser.getAuthToken())
                .build();
    }
    public void doAuthentication(String username, String password) throws BadCredentialsException{
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try{
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException exception){
            throw new BadCredentialsException("Invalid Username or Password");
        }
    }
    public void doLogout(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User:"+username+" doesn't exist"));
        user.setAuthToken(null);
        userRepository.save(user);
    }
}
