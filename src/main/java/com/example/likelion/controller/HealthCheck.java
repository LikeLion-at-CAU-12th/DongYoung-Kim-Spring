package com.example.likelion.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {
    @GetMapping("/api/health-check")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Server is up and running!");
    }
}
