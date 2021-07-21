package com.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @GetMapping("/fallback")
    public String globalFallback() {
        return "global fallback";
    }

    @GetMapping("/notfound")
    public String globalNotFound() {
        return "global fallback";
    }
}
