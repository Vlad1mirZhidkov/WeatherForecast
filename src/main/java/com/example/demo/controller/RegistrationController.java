package com.example.demo.controller;

import com.example.demo.DTO.AuthResponse;
import com.example.demo.DTO.RegisterUserRequest;
import com.example.demo.DTO.WeatherUserDTO;
import com.example.demo.Role;
import com.example.demo.model.UserEntity;
import com.example.demo.model.WeatherUser;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtGenerator;
import com.example.demo.service.WeatherUserService;
import com.example.demo.utils.MappingUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RegistrationController {
    private final WeatherUserService weatherUserService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator tokenGenerator;
    private final UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody RegisterUserRequest request){
        if (Boolean.TRUE.equals(userRepository.existsByUsername(request.getUsername()))) {
            throw new IllegalArgumentException("User with login " + request.getUsername() + " already exists.");
        }
        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        WeatherUserDTO weatherUserDTO = MappingUtils.mapToWeatherUserDTO(weatherUserService.registerUser(request));

        Map<String, Object> response = new HashMap<>();
        response.put("redirectUrl", "/login");
        response.put("weatherUserDTO", weatherUserDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> getLogin(@RequestBody RegisterUserRequest request){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenGenerator.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse(token);

        Map<String, Object> response = new HashMap<>();
        response.put("redirectUrl", "/");
        response.put("authResponse", authResponse);
        log.info("{} is logged", request.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
