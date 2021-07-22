package com.controller;

import com.rateLimit.Limit;
import com.rateLimit.RateLimitType;
import com.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class WebController {
    @Autowired
    private TestService testService;

    @GetMapping("/fallback")
    public String globalFallback() {
        return "global fallback";
    }

    @Limit(name = "notfound", key = "404", period = 10, count = 5, limitType = RateLimitType.IP)
    @GetMapping("/notfound")
    public String globalNotFound() {
        return "global fallback";
    }

    @GetMapping("/testRateLimit")
    public String testRateLimit() {
        testService.testRateLimit();
        return new Date().toString();
    }
}
