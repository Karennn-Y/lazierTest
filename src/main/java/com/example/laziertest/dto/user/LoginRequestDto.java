package com.example.laziertest.dto.user;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {

    @NotBlank(message = "이메일 항목은 필수 입니다.")
    private String userEmail;

    @NotBlank(message = "비밀번호 항목은 필수 입니다")
    private String password;
}
