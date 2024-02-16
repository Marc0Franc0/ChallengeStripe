package com.app.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {
    @ExceptionHandler(Exception.class)
    public ProblemDetail captureException(Exception e){
        ProblemDetail problemDetail;
        //log
        log.error(e.getMessage());
        e.printStackTrace();
        //Response body
        problemDetail = ProblemDetail
                .forStatusAndDetail
                        (HttpStatusCode.valueOf(500),e.getMessage());
        return problemDetail;
    }
}
