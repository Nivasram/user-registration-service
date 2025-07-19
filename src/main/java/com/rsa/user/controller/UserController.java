package com.rsa.user.controller;

import com.rsa.user.dto.AuthResponse;
import com.rsa.user.dto.LoginRequest;
import com.rsa.user.dto.RegisterRequest;
import com.rsa.user.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRegistrationService userRegistrationService;

    @PostMapping("/register")
    public AuthResponse registerUser(@RequestBody RegisterRequest request) {
        return userRegistrationService.registerUser(request);
    }

    @PostMapping("/login")
    public AuthResponse loginUser(@RequestBody LoginRequest request) {
        return userRegistrationService.loginUser(request);
    }
}
