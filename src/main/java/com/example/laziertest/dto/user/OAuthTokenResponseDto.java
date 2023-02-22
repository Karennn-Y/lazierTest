package com.example.laziertest.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OAuthTokenResponseDto {

	@JsonProperty("access_token")
	private String accessToken;
	private String scope;

	@JsonProperty("token_type")
	private String tokenType;

}
