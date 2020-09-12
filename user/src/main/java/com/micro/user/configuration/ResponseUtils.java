package com.micro.user.configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtils {
    public static ResponseEntity<Object> generate(boolean success, Object data, String message){
        Map<String, Object> response = new HashMap<>();
        response.put("data", data);
        response.put("message", message);
        return new ResponseEntity<>(
                response,
                success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    public static <T> Response<T> generateResponse(boolean success, T data, String message){
//        return Response.builder().success(success).data(data).message(message).build();
        return new Response<>(success, data, message);
    }

    @Data
//    @Builder
    @AllArgsConstructor
    static public class Response<T> {
        private boolean success;
        private T data;
        private String message;
    }
}
