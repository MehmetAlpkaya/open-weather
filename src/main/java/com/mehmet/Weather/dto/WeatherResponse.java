package com.mehmet.Weather.dto;

public record WeatherResponse(
        Request request,
        Location location,
        Current current

)
{

}
