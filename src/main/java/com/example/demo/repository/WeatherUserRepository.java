package com.example.demo.repository;

import com.example.demo.model.WeatherUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface WeatherUserRepository extends JpaRepository<WeatherUser, Long>, JpaSpecificationExecutor<WeatherUser> {
    Optional<WeatherUser> findByUsername(String username);
}