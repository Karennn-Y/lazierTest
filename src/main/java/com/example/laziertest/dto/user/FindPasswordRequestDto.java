package com.example.laziertest.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class FindPasswordRequestDto {

	@Email
	@NotBlank
	private String userEmail;

	@NotBlank
	@Pattern(regexp = "[0-9]{10,11}", message = "010-xxxx-xxxx 형식으로 입력해주세요.")
	private String phoneNumber;

}
