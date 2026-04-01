package com.ecommerce.backend.service;

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

    public User register(User user) {

        user.setPassword(encoder.encode(user.getPassword()));

        if (user.getRole() == null) {
            user.setRole("USER");
        }

        return repo.save(user);
    }

    public String login(User user) {

        User existingUser = repo.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (encoder.matches(user.getPassword(), existingUser.getPassword())) {
            return jwtUtil.generateToken(
                    existingUser.getUsername(),
                    existingUser.getRole()
            );
        } else {
            throw new RuntimeException("Invalid password");
        }
    }
}
