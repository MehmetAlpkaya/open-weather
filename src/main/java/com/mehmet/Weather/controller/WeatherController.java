package com.mehmet.Weather.controller;


import com.mehmet.Weather.dto.WeatherDto;
import com.mehmet.Weather.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/weather")
public class WeatherController
{
    private final WeatherService weatherService;


    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}")
    public ResponseEntity<WeatherDto> getWeather(@PathVariable("city") String city)
    {
        return ResponseEntity.ok(weatherService.getWeatherByCityName(city));
    }
}
