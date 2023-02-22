package com.example.laziertest.controller;

import com.example.laziertest.dto.module.UserQuotesInput;
import com.example.laziertest.service.Impl.UserQuotesService;
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
@RequestMapping("/userQuotes")
public class UserQuotesController {

    private final UserQuotesService userQuotesService;

    @PostMapping
    public ResponseEntity<?> add(HttpServletRequest request,
        @RequestBody @Valid UserQuotesInput parameter) {

        userQuotesService.add(request, parameter);
        return ResponseEntity.status(HttpStatus.CREATED).body("저장되었습니다.");
    }

    @GetMapping
    public ResponseEntity<?> get(HttpServletRequest request) {

        return new ResponseEntity<>(userQuotesService.get(request), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(HttpServletRequest request,
        @RequestBody @Valid UserQuotesInput parameter) {

        userQuotesService.update(request, parameter);

        return ResponseEntity.ok().body("업데이트 되었습니다.");
    }

    @DeleteMapping
    public ResponseEntity<?> delete(HttpServletRequest request) {

        userQuotesService.delete(request);
        return ResponseEntity.ok().body("삭제되었습니다.");
    }
}
