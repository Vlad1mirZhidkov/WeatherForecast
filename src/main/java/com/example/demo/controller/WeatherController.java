package com.example.demo.controller;

import com.example.demo.DTO.WeatherData;
import com.example.demo.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class WeatherController {
    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;

    }

    @GetMapping
    public String getPlace() {
        return "index";
    }

    @GetMapping("/currentWeather")
    public String getCurrentWeather(Model model, @RequestParam("location") String location) {
        System.out.println("Received location: " + location); // Для отладки
        WeatherData weatherData = weatherService.getWeatherData(location).block();
        WeatherData.CurrentTime currentTime = weatherData.getCurrentTime();
        model.addAttribute("currentTime", currentTime);
        model.addAttribute("location", location);
        return "currentWeather";
    }

    @GetMapping("/forecast")
    public String getForecast(Model model, @RequestParam("location") String location) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            return "redirect:/login";
        }
        WeatherData weatherData = weatherService.getWeatherData(location).block();
        List<WeatherData.Day> days = weatherData.getDays();
        model.addAttribute("days", days);
        model.addAttribute("location", location);
        return "forecast";
    }
}
