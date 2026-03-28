package com.example.tokenbucket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tokenbucket.service.RedisBucketService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RedisBucketService redisBucketService;

    // 🔴 Blacklist user permanently
    @PostMapping("/blacklist")
    public ResponseEntity<String> blacklist(@RequestParam String userId) {
        redisBucketService.addToBlacklist(userId);
        return ResponseEntity.ok("User blacklisted successfully");
    }

    // 🟡 Ban user temporarily
    @PostMapping("/ban")
    public ResponseEntity<String> ban(@RequestParam String userId,
                                      @RequestParam long minutes) {
        redisBucketService.banUser(userId, minutes);
        return ResponseEntity.ok("User banned for " + minutes + " minutes");
    }

    // 🟢 Remove ban
    @PostMapping("/unban")
    public ResponseEntity<String> unban(@RequestParam String userId) {
        redisBucketService.unbanUser(userId);
        return ResponseEntity.ok("User unbanned successfully");
    }

    // 🔵 Remove from blacklist
    @PostMapping("/remove-blacklist")
    public ResponseEntity<String> removeBlacklist(@RequestParam String userId) {
        redisBucketService.removeFromBlacklist(userId);
        return ResponseEntity.ok("User removed from blacklist");
    }
}