package com.example.demo.controller;

import com.example.demo.DTO.WeatherData;
import com.example.demo.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("api")
public class WeatherApiController {
    private final WeatherService weatherService;

    @Autowired
    public WeatherApiController(WeatherService weatherService) {
        this.weatherService = weatherService;

    }

    @GetMapping("/currentWeather")
    public ResponseEntity<ModelAndView> getCurrentWeather(@RequestParam("location") String location) {
        WeatherData weatherData = weatherService.getWeatherData(location).block();
        WeatherData.CurrentTime currentTime = weatherData.getCurrentTime();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("currentTime", currentTime);
        return new ResponseEntity<>(modelAndView, HttpStatus.OK);
    }

    @GetMapping("/forecast")
    public ResponseEntity<ModelAndView> getForecast(@RequestParam("location") String location) {
        WeatherData weatherData = weatherService.getWeatherData(location).block();
        List<WeatherData.Day> days = weatherData.getDays();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("currentTime", days);
        return new ResponseEntity<>(modelAndView, HttpStatus.OK);
    }

    @GetMapping("/{day}/forecast")
    public ResponseEntity<ModelAndView> getDayForecast(@RequestParam("location") String location, @PathVariable("day") String day) {
        WeatherData weatherData = weatherService.getWeatherData(location).block();
        List<WeatherData.Day> days = weatherData.getDays();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("days", days.get(Integer.parseInt(day)));
        return new ResponseEntity<>(modelAndView, HttpStatus.OK);
    }
}
