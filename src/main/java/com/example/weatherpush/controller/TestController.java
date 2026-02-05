package com.example.weatherpush.controller;

import com.example.weatherpush.service.CozeApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private CozeApiService cozeApiService;

    @GetMapping("/test-coze")
    public String testCozeApi() {
        return cozeApiService.callCozeWorkflow("深圳");
    }

    @GetMapping("/health")
    public String health() {
        return "Application is running!";
    }
}