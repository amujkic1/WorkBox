package com.example.hr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

@RestController
public class TestController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/api/info")
    public String getInfo() {
        return "Instanca na portu: " + port;
    }
}
