package com.example.tokenbucket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tokenbucket.service.RedisBucketService;

@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private RedisBucketService bucketService;

    @GetMapping("/test")
    public String test(@RequestParam String userId) {

        boolean allowed = bucketService.allowRequest(userId);

        if (allowed) {
            return "✅Request Allowed";
        } else {
            return "❌ Rate Limit Exceeded";
        }
    }
}