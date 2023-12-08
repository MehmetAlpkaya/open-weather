package com.mehmet.Weather.dto;

public record Request(
        String type,
        String query,
        String language,
        String unit
) {
}
