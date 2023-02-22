package com.example.laziertest.controller;

import com.example.laziertest.service.Impl.QuotesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/quotes")
public class QuotesController {

    public final QuotesService quotesService;

    @GetMapping
    public ResponseEntity<?> getQuotes() {
        return new ResponseEntity<>(quotesService.get(), HttpStatus.OK);
    }
}
