package com.example.eVoting.entities;

import com.example.eVoting.enums.Gender;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
@Getter
@Setter
@Slf4j
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @Column(name = "username")
    String username;
    @Column(name = "password")
    String password;
    @Column(name = "authToken")
    String authToken;
    @Column(name = "name")
    String name;
    @Column(name = "address")
    String address;
    @Column(name = "dob")
    LocalDate dob;
    @Column(name= "gender")
    @Enumerated(EnumType.STRING)
    Gender gender;
    @Column(name = "roles")
    String roles = "";
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(roles.isEmpty()) return new ArrayList<>();
        return Arrays.stream(roles.split(",")).map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
