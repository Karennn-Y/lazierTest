package com.example.laziertest.dto.module;


import com.example.laziertest.persist.entity.module.UserWeather;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserWeatherDto {

    long userId;
    String cityName;
    String locationName;

    public static UserWeatherDto of(UserWeather userWeather) {
        return UserWeatherDto.builder()
            .userId(userWeather.getLazierUser().getUserId())
            .cityName(userWeather.getCityName())
            .locationName(userWeather.getLocationName())
            .build();
    }
}
