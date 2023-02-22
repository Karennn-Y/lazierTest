package com.example.laziertest.dto.module;

import com.example.laziertest.persist.entity.module.Weather;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDto {

    long userId;
    String cityName; // 시/군/구 이름
    String locationName; // 동 이름
    String temperature;
    String effectiveTemperature; // 체감 온도
    String highestTemperature;
    String lowestTemperature;
    String weatherInformation;
    String weatherComparison; // 어제 날씨 비교 온도
    String humidity;
    String ultraviolet; // 자외선
    String fineParticle; // 미세먼지
    String ultrafineParticle; // 초미세먼지
    String windSpeed;
    String windDirection;
    String updatedAt;

    public static WeatherDto of(Weather weather) {
        return WeatherDto.builder()
            .userId(weather.getLazierUser().getUserId())
            .cityName(weather.getCityName())
            .locationName(weather.getLocationName())
            .temperature(weather.getTemperature())
            .effectiveTemperature(weather.getEffectiveTemperature())
            .highestTemperature(weather.getHighestTemperature())
            .lowestTemperature(weather.getLowestTemperature())
            .weatherInformation(weather.getWeatherInformation())
            .weatherComparison(weather.getWeatherComparison())
            .humidity(weather.getHumidity())
            .ultraviolet(weather.getUltraviolet())
            .fineParticle(weather.getFineParticle())
            .ultrafineParticle(weather.getUltrafineParticle())
            .windSpeed(weather.getWindSpeed())
            .windDirection(weather.getWindDirection())
            .updatedAt(weather.getUpdatedAt())
            .build();
    }
}
