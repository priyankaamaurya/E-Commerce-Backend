package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.LoginRequest;
import com.ecommerce.backend.dto.RegisterRequest;
import com.ecommerce.backend.model.User;
import com.ecommerce.backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "App APIs", description = "Login and Registration APIs")
public class AuthController {

    @Autowired
    private AuthService service;

    @Operation(summary = "Register new user")
    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return service.register(request);
    }

    @Operation(summary = "User login")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        String token = service.login(request);

        User user = service.getUserByUsername(request.getUsername());

        return ResponseEntity.ok(
                java.util.Map.of(
                        "token", token,
                        "role", user.getRole()  // ✅ send role too
                )
        );
    }
}
