package com.example.eVoting.repositories;

import com.example.eVoting.entities.Election;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ElectionRepository extends JpaRepository<Election,Integer> {
    public Optional<Election> findByName(String name);
}
