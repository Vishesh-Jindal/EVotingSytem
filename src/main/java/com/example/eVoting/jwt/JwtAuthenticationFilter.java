package com.example.eVoting.jwt;

import com.example.eVoting.entities.User;
import com.example.eVoting.exceptions.TokenException;
import com.example.eVoting.services.CustomUserDetailsService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    JwtAuthenticationHelper jwtAuthenticationHelper;
    @Autowired
    CustomUserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if(authHeader != null){
            if(!authHeader.startsWith("Bearer")){
                throw new TokenException("Token not given in proper format");
            }
            String token = authHeader.split(" ")[1];
            if(jwtAuthenticationHelper.isTokenExpired(token)){
                throw new TokenException("Token Expired, Please Login Again");
            }
            String username = jwtAuthenticationHelper.getUsername(token);
            User user = userDetailsService.loadUserByUsername(username);
            if(!token.equals(user.getAuthToken())){
                throw new TokenException("Token Not Valid");
            }
            if(SecurityContextHolder.getContext().getAuthentication() == null){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(token, null, user.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
