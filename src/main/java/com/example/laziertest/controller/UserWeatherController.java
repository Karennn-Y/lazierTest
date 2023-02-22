package com.example.laziertest.controller;

import com.example.laziertest.dto.module.UserWeatherInput;
import com.example.laziertest.service.Impl.UserWeatherService;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/weather/user-info")
public class UserWeatherController {

    private final UserWeatherService userWeatherService;

    @ApiOperation(value = "사용자 정보 (위치 정보) 를 저장 할 수 있는 api 입니다.")
    @PostMapping
    public ResponseEntity<?> addUserInfo(HttpServletRequest request, @RequestBody @Valid
    UserWeatherInput parameter) {
        userWeatherService.add(request, parameter);

        return ResponseEntity.status(HttpStatus.CREATED).body("저장되었습니다.");
    }

    @ApiOperation(value = "사용자 정보 (위치 정보) 를 조회 할 수 있는 api 입니다.")
    @GetMapping
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        return new ResponseEntity<>(userWeatherService.detail(request), HttpStatus.OK);
    }

    @ApiOperation(value = "사용자 정보 (위치 정보) 를 업데이트 할 수 있는 api 입니다.")
    @PutMapping
    public ResponseEntity<?> updateUserInfo(HttpServletRequest request,
        @RequestBody @Valid UserWeatherInput parameter) {
        userWeatherService.update(request, parameter);

        return ResponseEntity.ok().body("업데이트 되었습니다.");
    }

    @ApiOperation(value = "사용자 정보 (위치 정보) 를 삭제 할 수 있는 api 입니다.")
    @DeleteMapping
    public ResponseEntity<?> deleteUserInfo(HttpServletRequest request) {
        userWeatherService.delete(request);

        return ResponseEntity.ok().body("삭제되었습니다.");
    }
}
