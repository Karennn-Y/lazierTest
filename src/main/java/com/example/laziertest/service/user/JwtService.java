package com.example.laziertest.service.user;

import com.example.laziertest.config.user.JwtTokenProvider;
import com.example.laziertest.dto.user.AccessTokenResponseDto;
import com.example.laziertest.exception.user.InvalidTokenException;
import com.example.laziertest.exception.user.UnauthorizedRefreshTokenException;
import com.example.laziertest.persist.entity.user.RefreshToken;
import io.jsonwebtoken.JwtException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    public String getRefreshToken(String userId) {
        return redisService.getValues(userId);
    }

    public AccessTokenResponseDto validateRefreshToken(HttpServletRequest request) { //refresh 유효성 검사

        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        String userId = jwtTokenProvider.getUserPk(refreshToken);
        String redisToken = getRefreshToken(userId);

        if (!redisToken.equals(refreshToken)) {
            throw new InvalidTokenException("유효하지 않는 토큰입니다.");
        }

        RefreshToken token = RefreshToken.builder()
            .refreshToken(refreshToken)
            .keyId(userId)
            .build();

        String createdAccessToken = "";
        try {
            createdAccessToken = jwtTokenProvider.validateRefreshToken(token);
        } catch (JwtException e) {
            throw new UnauthorizedRefreshTokenException(e.getMessage());
        }
        return createdRefreshJson(createdAccessToken);
    }

    //token -> dto
    public AccessTokenResponseDto createdRefreshJson(String createdAcessToken) {

        return AccessTokenResponseDto.builder()
                .accessToken(createdAcessToken)
                .grantType("Bearer")
                .build();

    }

}
