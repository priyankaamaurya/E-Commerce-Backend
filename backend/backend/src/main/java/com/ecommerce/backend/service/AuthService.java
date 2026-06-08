package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.LoginRequest;
import com.ecommerce.backend.dto.RegisterRequest;
import com.ecommerce.backend.model.User;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    public User register(RegisterRequest request) {

        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));

        if(request.getRole() == null || request.getRole().isEmpty()){
            user.setRole("USER");
        } else {
            user.setRole(request.getRole());
        }

        return repo.save(user);

    }

    public String login(LoginRequest request) {

        User existingUser = repo.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (encoder.matches(request.getPassword(), existingUser.getPassword())) {

            System.out.println("DB ROLE: " + existingUser.getRole());

            return jwtUtil.generateToken(
                    existingUser.getUsername(),
                    existingUser.getRole()
            );

        } else {
            throw new RuntimeException("Invalid password");
        }
    }

    public User getUserByUsername(String username) {
        return repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
