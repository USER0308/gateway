package com.service.impl;

import com.rateLimit.RateLimitHandler;
import com.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private RateLimitHandler rateLimitHandler;

    @Override
    public void testRateLimit() {
        rateLimitHandler.handlePath();
    }
}
