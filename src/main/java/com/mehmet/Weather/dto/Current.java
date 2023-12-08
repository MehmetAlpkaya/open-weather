package com.mehmet.Weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Current(

        Integer temperature
) {
}
