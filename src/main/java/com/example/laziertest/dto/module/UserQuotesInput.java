package com.example.laziertest.dto.module;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserQuotesInput {

    @NotBlank
    String content;
}
