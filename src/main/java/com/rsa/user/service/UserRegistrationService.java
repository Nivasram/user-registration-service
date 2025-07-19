package com.rsa.user.service;

import com.rsa.user.dto.AuthResponse;
import com.rsa.user.dto.LoginRequest;
import com.rsa.user.dto.RegisterRequest;
import com.rsa.user.entity.User;
import com.rsa.user.enumerator.Role;
import com.rsa.user.repository.UserRepo;
import com.rsa.user.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthResponse registerUser(RegisterRequest registerRequest){
        Role role = Role.valueOf(registerRequest.getRole().toUpperCase());
        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .role(role)
                .build();
        User savedUser = userRepo.save(user);
        String token = jwtUtils.generateToken(savedUser.getUsername());
        return new AuthResponse(token , "User registered successfully");
    }

    public AuthResponse loginUser(LoginRequest loginRequest) {
        User user = userRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtUtils.generateToken(user.getEmail());
        return new AuthResponse(token, "Login successful");
    }
}
