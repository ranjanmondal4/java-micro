package com.micro.cabinet.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CabinetController {

    @RequestMapping("/user/{userId}/cabinet")
    public String getCabinet(@PathVariable("userId") String userId){
        return "Cabinet for user - " + userId;
    }
}
