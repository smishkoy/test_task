package com.mishkoy.authapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    // Реєстрація: POST /api/auth/register
    // Логін: POST /api/auth/login -> повертає JWT
}