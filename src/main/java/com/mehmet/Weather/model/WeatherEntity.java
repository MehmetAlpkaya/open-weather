package com.mehmet.Weather.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
public class WeatherEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String requestedCityName;
    private  String cityName;
    private String country;
    private  Integer temperature;
    private LocalDateTime updateTime;
    private LocalDateTime responseTime;

    public WeatherEntity(String id, String requestedCityName, String cityName, String country, Integer temperature, LocalDateTime updateTime, LocalDateTime responseTime) {
        this.id = id;
        this.requestedCityName = requestedCityName;
        this.cityName = cityName;
        this.country = country;
        this.temperature = temperature;
        this.updateTime = updateTime;
        this.responseTime = responseTime;
    }
    public WeatherEntity( String requestedCityName, String cityName, String country, Integer temperature, LocalDateTime updateTime, LocalDateTime responseTime) {
        this.id = "";
        this.requestedCityName = requestedCityName;
        this.cityName = cityName;
        this.country = country;
        this.temperature = temperature;
        this.updateTime = updateTime;
        this.responseTime = responseTime;
    }

    public WeatherEntity() {

    }

    public String getId() {
        return id;
    }

    public String getRequestedCityName() {
        return requestedCityName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountry() {
        return country;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public LocalDateTime getResponseTime() {
        return responseTime;
    }
}
