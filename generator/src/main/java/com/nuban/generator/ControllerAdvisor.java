package com.nuban.generator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(InvalidDetailsException.class)
    public ResponseEntity<?> handle(InvalidDetailsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
