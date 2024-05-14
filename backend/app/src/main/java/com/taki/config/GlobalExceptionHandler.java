package com.taki.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequestMapping
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> exception(Exception e) {
        e.printStackTrace();
        return ResponseEntity.ok("发现错误: " + e.getClass().getName());
    }
}
