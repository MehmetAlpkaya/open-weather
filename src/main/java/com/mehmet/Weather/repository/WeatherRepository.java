package com.mehmet.Weather.repository;

import com.mehmet.Weather.model.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface WeatherRepository extends JpaRepository<WeatherEntity, String>
{
    //Select * from entity where requestedCityName order by updateTime desc limit 1;
    Optional<WeatherEntity> findFirstByRequestedCityNameOrderByUpdateTimeDesc(String city);
}
