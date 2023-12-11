package com.mehmet.Weather.controller;


import com.mehmet.Weather.constants.validations.CityNameConstraint;
import com.mehmet.Weather.dto.WeatherDto;
import com.mehmet.Weather.service.WeatherService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/weather")
@Validated
public class WeatherController
{
    private final WeatherService weatherService;


    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}")
    @RateLimiter(name = "basic")
    public ResponseEntity<WeatherDto> getWeather(@PathVariable("city") @CityNameConstraint @NotBlank String city)
    {
        return ResponseEntity.ok(weatherService.getWeatherByCityName(city));
    }
}
