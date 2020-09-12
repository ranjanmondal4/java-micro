package com.micro.user.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Optional;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    LocaleService localeService;

//    private static ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidException(final MethodArgumentNotValidException e) {
//        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
//        Optional<FieldError> message = fieldErrors.stream().findFirst();
        return ResponseUtils.generate(false, null, localeService.getMessage(e.getMessage()));
    }

    /*@ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> DataNotFoundException(final DataNotFoundException e) {
        return ResponseUtils.generate(false, null, localeService.getMessage(e.getMessage()));
    }*/

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseUtils.Response<? extends Object> DataNotFoundException(final DataNotFoundException e) {
        return ResponseUtils.generateResponse(false, null, localeService.getMessage(e.getMessage()));
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(final Exception e) {
//        StackTraceElement[] tracks = e.getStackTrace();
//
//        Map<String, Object> data = new HashMap<>();
//        for (int index = 0; index < tracks.length && index < 5; index++){
//            data.put(Integer.toString(index) , tracks[index]);
//        }
        return ResponseUtils.generate(false, null, localeService.getMessage(e.getMessage()));
    }
}
