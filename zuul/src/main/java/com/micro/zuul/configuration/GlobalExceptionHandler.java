package com.micro.zuul.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    LocaleService localeService;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidException(final MethodArgumentNotValidException e) {
        return ResponseUtils.generate(false, null, localeService.getMessage(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(final Exception e) {
        StackTraceElement[] tracks = e.getStackTrace();

        Map<String, Object> data = new HashMap<>();
        for (int index = 0; index < tracks.length && index < 5; index++){
            data.put(Integer.toString(index) , tracks[index]);
        }
        return ResponseUtils.generate(false, data, localeService.getMessage(e.getMessage()));
    }
}
