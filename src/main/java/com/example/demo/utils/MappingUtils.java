package com.example.demo.utils;

import com.example.demo.DTO.WeatherUserDTO;
import com.example.demo.model.WeatherUser;

public class MappingUtils {

        private MappingUtils() {}

        public static WeatherUserDTO mapToWeatherUserDTO(WeatherUser user) {
            return WeatherUserDTO.builder()
                    .id(user.getId())
                    .fullName(user.getFullName())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .phone(user.getPhone())
                    .email(user.getEmail())
                    .city(user.getCity())
                    .country(user.getCountry())
                    .birthDate(user.getBirthDate())
                    .build();
        }
}
