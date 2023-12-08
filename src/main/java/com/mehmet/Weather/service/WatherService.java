package com.mehmet.Weather.service;

import com.mehmet.Weather.dto.WeatherDto;
import com.mehmet.Weather.model.WeatherEntity;
import com.mehmet.Weather.repository.WeatherRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class WatherService {
    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;

    public WatherService(WeatherRepository weatherRepository, RestTemplate restTemplate) {
        this.weatherRepository = weatherRepository;
        this.restTemplate = restTemplate;
    }

    public WeatherDto getWeatherByCityName(String cityName)
    {
        Optional<WeatherEntity> weatherEntityOptional =weatherRepository.findFirstByRequestedCityNameOOrderByUpdateTimeDesc(cityName);

        if(!weatherEntityOptional.isPresent())
        {
            return WeatherDto.convert(getWeatherFromWeatherStack());
        }
            return WeatherDto.convert(weatherEntityOptional.get());

    }

    private WeatherEntity getWeatherFromWeatherStack() {
    }

}
