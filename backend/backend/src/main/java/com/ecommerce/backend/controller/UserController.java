package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ChangePasswordRequest;
import com.ecommerce.backend.model.User;
import com.ecommerce.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User APIs", description = "User profile and password management")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Get user profile")
    @GetMapping("/profile")
    public User getProfile(Authentication auth) {
        return userService.getProfile(auth.getName());
    }

    @Operation(summary = "Change password")
    @PutMapping("/change-password")
    public String changePassword(@RequestBody ChangePasswordRequest request,
                                 Authentication auth) {
        return userService.changePassword(auth.getName(), request);
    }
}
