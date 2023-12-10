package com.mehmet.Weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mehmet.Weather.dto.WeatherDto;
import com.mehmet.Weather.dto.WeatherResponse;
import com.mehmet.Weather.model.WeatherEntity;
import com.mehmet.Weather.repository.WeatherRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class WeatherService {
    private final ObjectMapper objectMapper=new ObjectMapper();
    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;
    private static final String API_URL= "http://api.weatherstack.com/current?access_key=c7bfe9c3c851ebcb7f23fe2dc4326262&query=";
    public WeatherService(WeatherRepository weatherRepository, RestTemplate restTemplate) {
        this.weatherRepository = weatherRepository;
        this.restTemplate = restTemplate;
    }

    public WeatherDto getWeatherByCityName(String city)
    {
        Optional<WeatherEntity> weatherEntityOptional =weatherRepository.findFirstByRequestedCityNameOrderByUpdateTimeDesc(city);

        if(!weatherEntityOptional.isPresent())
        {
            return WeatherDto.convert(getWeatherFromWeatherStack(city));
        }
            return WeatherDto.convert(weatherEntityOptional.get());

    }

    private WeatherEntity getWeatherFromWeatherStack(String city)
    {
        ResponseEntity<String> responseEntity= restTemplate.getForEntity(API_URL+city, String.class);
        try {
            WeatherResponse weatherResponse = objectMapper.readValue(responseEntity.getBody(),WeatherResponse.class);
            return saveWeatherEntity(city,weatherResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
    private WeatherEntity saveWeatherEntity(String city , WeatherResponse weatherResponse)
    {
        DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        WeatherEntity weatherEntity=new WeatherEntity(
                city,
                weatherResponse.location().name(),
                weatherResponse.location().country(),
                weatherResponse.current().temperature(),
                LocalDateTime.now(),
                LocalDateTime.parse(weatherResponse.location().localtime(),dateTimeFormatter)
                );
           return weatherRepository.save(weatherEntity);
    }

}
