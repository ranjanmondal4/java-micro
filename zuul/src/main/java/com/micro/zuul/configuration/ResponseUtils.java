package com.micro.zuul.configuration;

import com.micro.zuul.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {
    public static ResponseEntity<Object> generate(boolean success, Object data, String message){
        return new ResponseEntity<>(
                new AppResponse<>(success, data, message),
                success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<Object> generate(ResponseUtils.Response<? extends Object> response){
        return new ResponseEntity<>(
                new AppResponse<>(response.success, response.data, response.message),
                response.success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    static public class Response<T> {
        private boolean success;
        private T data;
        private String message;
    }

    @Data
    @AllArgsConstructor
    static public class AppResponse<T> {
        private boolean success;
        private T data;
        private String message;
    }
}
