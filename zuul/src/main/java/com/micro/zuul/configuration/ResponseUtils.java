package com.micro.zuul.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class ResponseUtils {
    public static ResponseEntity<Object> generate(boolean success, Object data, String message){
        return new ResponseEntity<>(
                new AppResponse<>(success, data, message, null),
                success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<Object> generate(ResponseUtils.Response<? extends Object> response){

        log.info("Response {}", response);
        return new ResponseEntity<>(
                new AppResponse<>(response.success, response.data, response.message, response.stackTrace),
                response.success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    static public class Response<T> {
        private boolean success;
        private T data;
        private String message;
        private String stackTrace;
    }

    @Data
    @AllArgsConstructor
    static public class AppResponse<T> {
        private boolean success;
        private T data;
        private String message;
        private String stackTrace;
    }
}
