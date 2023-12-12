package com.mehmet.Weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mehmet.Weather.constants.Constants;
import com.mehmet.Weather.dto.WeatherDto;
import com.mehmet.Weather.dto.WeatherResponse;
import com.mehmet.Weather.model.WeatherEntity;
import com.mehmet.Weather.repository.WeatherRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = {"weathers"})
public class WeatherService {
    private final ObjectMapper objectMapper=new ObjectMapper();
    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;
   // private static final String API_URL= "http://api.weatherstack.com/current?access_key=c7bfe9c3c851ebcb7f23fe2dc4326262&query=";
    public WeatherService(WeatherRepository weatherRepository, RestTemplate restTemplate) {
        this.weatherRepository = weatherRepository;
        this.restTemplate = restTemplate;
    }

    @Cacheable(key = "#city")
    public WeatherDto getWeatherByCityName(String city)
    {
        Optional<WeatherEntity> weatherEntityOptional =weatherRepository.findFirstByRequestedCityNameOrderByUpdateTimeDesc(city);

        /*if(!weatherEntityOptional.isPresent())
        {
            return WeatherDto.convert(getWeatherFromWeatherStack(city));
        }
        if(weatherEntityOptional.get().getUpdateTime().isBefore(LocalDateTime.now().minusSeconds(30)))
        {
            return WeatherDto.convert(getWeatherFromWeatherStack(city));
        }
            return WeatherDto.convert(weatherEntityOptional.get());*/

        return weatherEntityOptional.map(weather -> {
            if(weather.getUpdateTime().isBefore(LocalDateTime.now().minusSeconds(30)))
            {
                return WeatherDto.convert(getWeatherFromWeatherStack(city));
            }
            return WeatherDto.convert(weather);
        }).orElseGet(()->WeatherDto.convert(getWeatherFromWeatherStack(city)));

    }


    private WeatherEntity getWeatherFromWeatherStack(String city)
    {
        ResponseEntity<String> responseEntity= restTemplate.getForEntity(getApiUrl(city), String.class);
        try {
            WeatherResponse weatherResponse = objectMapper.readValue(responseEntity.getBody(),WeatherResponse.class);
            return saveWeatherEntity(city,weatherResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
    @CacheEvict(allEntries = true)
    @PostConstruct
    @Scheduled(fixedRate = 10000)
    public void clearCatch()
    {}
    private String getApiUrl(String city)
    {
        return Constants.API_URL+Constants.ACCES_KEY+Constants.API_KEY+Constants.QUERY+city;
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
