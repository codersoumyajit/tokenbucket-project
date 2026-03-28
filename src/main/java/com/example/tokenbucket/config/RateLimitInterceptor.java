package com.example.tokenbucket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.tokenbucket.service.RedisBucketService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisBucketService redisBucketService;

    @Override
public boolean preHandle(HttpServletRequest request,
                         HttpServletResponse response,
                         Object handler) throws Exception {

    String userId = request.getParameter("userId");

    if (userId == null) {
        response.sendError(400, "userId is required");
        return false;
    }

    // 🔴 1. Check Blacklist
    if (redisBucketService.isBlacklisted(userId)) {
        response.sendError(403, "User Blacklisted");
        return false;
    }

    // 🟡 2. Check Temporary Ban
    if (redisBucketService.isBanned(userId)) {
        response.sendError(403, "User Banned");
        return false;
    }

    // 🟢 3. Check Rate Limit
    boolean allowed = redisBucketService.allowRequest(userId);

    if (!allowed) {
        response.sendError(429, "Too Many Requests");
        return false;
    }

    return true;
}
}