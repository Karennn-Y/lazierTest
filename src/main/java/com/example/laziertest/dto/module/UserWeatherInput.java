package com.example.laziertest.dto.module;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserWeatherInput {
    long userId;
    @NotBlank
    String cityName;
    @NotBlank
    String locationName;

}
