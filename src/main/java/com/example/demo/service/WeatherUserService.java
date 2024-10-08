package com.example.demo.service;

import com.example.demo.DTO.RegisterUserRequest;
import com.example.demo.DTO.UpdateContactInfoRequest;
import com.example.demo.DTO.WeatherData;
import com.example.demo.model.WeatherUser;
import com.example.demo.repository.WeatherUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherUserService {
    private final WeatherUserRepository weatherUserRepository;
    private final PasswordEncoder passwordEncoder;

    public WeatherUser registerUser(RegisterUserRequest request){
        weatherUserRepository.findByUsername(request.getUsername())
                .ifPresent(u -> new IllegalArgumentException("User with login " + request.getUsername() + " already exists."));

        WeatherUser newUser = WeatherUser.builder()
                .username(request.getUsername())
                .build();
        weatherUserRepository.save(newUser);
        log.info("User with id {} saved", newUser.getId());
        return newUser;
    }

    public WeatherUser updateContactInfo(Long userId, UpdateContactInfoRequest request){
        WeatherUser user = weatherUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found."));
        if (request.getBirthDate() != null){
            user.setBirthDate(request.getBirthDate());
        }
        if (request.getPhone() != null){
            user.setPhone(request.getPhone());
        }
        if (request.getFullName() != null){
            user.setFullName(request.getFullName());
        }
        if (request.getEmail() != null){
            user.setEmail(request.getEmail());
        }
        if (request.getCity() != null){
            user.setCity(request.getCity());
        }
        if (request.getCountry() != null){
            user.setCountry(request.getCountry());
        }
        weatherUserRepository.save(user);
        return user;
    }
}
