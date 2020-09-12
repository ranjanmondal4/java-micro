package com.micro.user.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    LocaleService localeService;


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseUtils.Response<? extends Object> methodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.error("Exception - Method Argument {}", e.getMessage());
        return ResponseUtils.generateResponse(false, null, localeService.getMessage(e.getMessage()));
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseUtils.Response<? extends Object> DataNotFoundException(final DataNotFoundException e) {
        log.error("Exception - Data not found {}", e.getMessage());
        return ResponseUtils.generateResponse(false, null, localeService.getMessage(e.getMessage()));
    }


    @ExceptionHandler(Exception.class)
    public ResponseUtils.Response<? extends Object> exception(final Exception e) {
        log.error("Exception {}", e.getMessage());
        return ResponseUtils.generateResponse(false, null, localeService.getMessage(e.getMessage()));
    }
}
