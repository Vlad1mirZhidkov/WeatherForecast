package com.example.demo.controller;

import com.example.demo.DTO.UpdateContactInfoRequest;
import com.example.demo.DTO.WeatherData;
import com.example.demo.DTO.WeatherUserDTO;
import com.example.demo.model.WeatherUser;
import com.example.demo.service.WeatherService;
import com.example.demo.service.WeatherUserService;
import com.example.demo.utils.AuthenticationUtils;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("api")
public class WeatherApiController {
    private final WeatherService weatherService;
    private final WeatherUserService weatherUserService;


    @Autowired
    public WeatherApiController(WeatherService weatherService, WeatherUserService weatherUserService) {
        this.weatherService = weatherService;
        this.weatherUserService = weatherUserService;
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

    @GetMapping("profile")
    public ResponseEntity<ModelAndView> getInfoAboutUser(){
        String username = AuthenticationUtils.getUsernameFromAuthentication();
        ModelAndView modelAndView = new ModelAndView();
        if(username != null){
            WeatherUser user = weatherUserService.getUserByUsername(username);
            modelAndView.addObject("userInfo", user);
            return new ResponseEntity<>(modelAndView, HttpStatus.OK);
        }
        modelAndView.addObject("error", "User not authenticated");
        return new ResponseEntity<>(modelAndView, HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("profile")
    public ResponseEntity<ModelAndView> updateContactInfo(@RequestBody UpdateContactInfoRequest request){
        String username = AuthenticationUtils.getUsernameFromAuthentication();
        WeatherUser user = weatherUserService.getUserByUsername(username);
        ModelAndView modelAndView = new ModelAndView();
        if(username != null){
            WeatherUser updatedUser = weatherUserService.updateContactInfo(user.getId(), request);
            modelAndView.addObject("userInfo", updatedUser);
            return new ResponseEntity<>(modelAndView, HttpStatus.OK);
        }
        modelAndView.addObject("error", "User not authenticated");
        return new ResponseEntity<>(modelAndView, HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("profile")
    public ResponseEntity<ModelAndView> deleteProfile(){
        String username = AuthenticationUtils.getUsernameFromAuthentication();
        WeatherUser user = weatherUserService.getUserByUsername(username);
        ModelAndView modelAndView = new ModelAndView();
        if(username != null){
            weatherUserService.deleteUser(user.getId());
            modelAndView.addObject("message", "User deleted");
            return new ResponseEntity<>(modelAndView, HttpStatus.OK);
        }
        modelAndView.addObject("error", "User not authenticated");
        return new ResponseEntity<>(modelAndView, HttpStatus.UNAUTHORIZED);
    }
}
