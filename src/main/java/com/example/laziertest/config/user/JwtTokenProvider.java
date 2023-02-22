package com.example.laziertest.config.user;

import com.example.laziertest.dto.user.TokenResponseDto;
import com.example.laziertest.persist.entity.user.RefreshToken;
import com.example.laziertest.service.user.LoginService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.token-validity-in-seconds}")
	private long accessTokenValidTime; //30 * 60 * 1000L 30분

	@Value("${jwt.refreshToken-validity-in-seconds}")
	private long refreshTokenValidTime; //24 * 60 * 60 * 1000L 1일

	public static final String AUTHORIZATION = "Authorization";
	public static final String BEARER_TYPE = "Bearer";
	public static final String REFRESH_TOKEN = "RefreshToken";

	//private long accessTokenValidTime = 1 * 60 * 1000L; //1분 (test)
	//private long refreshTokenValidTime = 5 * 60 * 1000L; //5분 (test)

	private final LoginService loginService;

	@PostConstruct //종속성 주입이 완료된 후 실행되는 메서드 <- 호출되지 않아도 수행
	protected void init() {
		secretKey = Base64.getEncoder()
			.encodeToString(secretKey.getBytes()); //객체 조기화, secretKey를 Base64로 인코딩
	}

	public String getUserPk(String token) {
		if (token.isEmpty()) {
			throw new JwtException("유효하지 않는 토큰입니다.");
		}

		String username = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject(); //username
		log.info("getUserPk: " + username);
		return username;
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = loginService.loadUserByUsername(this.getUserPk(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "",
			userDetails.getAuthorities()); //authentication
	}

	//token
	public TokenResponseDto createAccessToken(String userId) {

		Claims claims = Jwts.claims().setSubject(userId); //user_id
		Date now = new Date();

		//Access Token
		String accessToken = Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + accessTokenValidTime))
			.signWith(SignatureAlgorithm.HS256, secretKey) //signature에 들어갈 secretKey
			.compact();

		//Refresh Token
		String refreshToken = Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + refreshTokenValidTime))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();

		return TokenResponseDto.builder()
			.accessToken(accessToken)
			.grantType(BEARER_TYPE)
			.refreshToken(refreshToken)
			.build();
	}

	public String resolveToken(HttpServletRequest request) {
		String token = request.getHeader(AUTHORIZATION); //"Authorization" : "Token"
		if (!ObjectUtils.isEmpty(token) && token.toLowerCase()
			.startsWith(BEARER_TYPE.toLowerCase())) {
			return token.substring(BEARER_TYPE.length()).trim();
		}
		return null;
	}

	public boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (ExpiredJwtException e) {
			log.warn("만료된 JWT 토큰입니다.");
		} catch (UnsupportedJwtException e) {
			log.warn("지원되지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException e) {
			log.warn("JWT 토큰이 잘못되었습니다.");
		}
		return false;
	}

	//refresh token
	public String recreationAccessToken(String userId) {

		Claims claims = Jwts.claims().setSubject(userId);
		Date now = new Date();

		return Jwts.builder() //new access Token
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + refreshTokenValidTime))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

	public String resolveRefreshToken(HttpServletRequest request) {
		String token = request.getHeader(REFRESH_TOKEN); //"RefreshToken" : "Token"
		if (!ObjectUtils.isEmpty(token) && token.toLowerCase()
			.startsWith(BEARER_TYPE.toLowerCase())) {
			return token.substring(BEARER_TYPE.length()).trim();
		}
		return null;
	}

	public String validateRefreshToken(RefreshToken token) {
		String refreshToken = token.getRefreshToken();

		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey)
				.parseClaimsJws(refreshToken);
			if (!claims.getBody().getExpiration().before(new Date())) {
				return recreationAccessToken(claims.getBody().getSubject());
			}
		} catch (ExpiredJwtException e) {
			log.warn("만료된 refreshToken 입니다.");
			throw new JwtException("Refresh 토큰이 만료되었습니다. 로그인이 필요합니다.");
		}
		return null;
	}


}
