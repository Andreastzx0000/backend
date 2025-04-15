package com.ass.citysparkapplication.controller;

import com.ass.citysparkapplication.dto.AuthRequest;
import com.ass.citysparkapplication.dto.AuthResponse;
import com.ass.citysparkapplication.model.User;
import com.ass.citysparkapplication.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }
}
