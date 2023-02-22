package com.example.laziertest.exception.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException e) throws IOException {
		log.info("CustomAuthenticationEntryPoint");

		Object errorObject = request.getAttribute("exception");
		if (errorObject != null) {
			sendErrorUnauthorized(response);
		} else {
			sendErrorDenied(response);
		}
	}

	private void sendErrorUnauthorized(HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json,charset=utf-8");
		makeResultResponse(
			response,
			new UnauthorizedMemberException("로그인이 필요합니다."),
			HttpStatus.UNAUTHORIZED //401
		);
	}

	private void sendErrorDenied(HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType("application/json,charset=utf-8");
		makeResultResponse(
			response,
			new UnauthorizedMemberException("권한이 없는 요청입니다."),
			HttpStatus.FORBIDDEN //403
		);
	}

	private static void makeResultResponse(
		HttpServletResponse response,
		Exception exception,
		HttpStatus httpStatus) throws IOException {

		try (OutputStream os = response.getOutputStream()) {
			JavaTimeModule javaTimeModule = new JavaTimeModule();
			LocalDateTimeSerializer localDateTimeSerializer = new LocalDateTimeSerializer(
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Seoul"))
			);
			javaTimeModule.addSerializer(LocalDateTime.class, localDateTimeSerializer);
			ObjectMapper objectMapper = new ObjectMapper().registerModule(javaTimeModule);
			objectMapper.writeValue(os, ErrorMessage.of(exception, httpStatus));
			os.flush();
		}
	}
}

