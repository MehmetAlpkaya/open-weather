package com.mehmet.Weather.dto;

import com.mehmet.Weather.model.WeatherEntity;

public record WeatherDto(
        String cityName,
        String country,
        Integer temperature
) {
   public static WeatherDto convert (WeatherEntity from)
    {
        return new WeatherDto(from.getCityName(), from.getCountry(), from.getTemperature());
    }
}
