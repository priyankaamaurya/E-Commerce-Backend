package com.ecommerce.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOrderConfirmation( String toEmail, String username, Double amount, Long orderId) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject("Order Confirmed 🎉");

        message.setText(
                        "Hello " + username + ",\n\n" +
                        "Your order has been placed successfully.\n\n" +
                        "Order ID: " + orderId + "\n" +
                        "Total Amount: ₹" + amount + "\n\n" +
                        "Thank you for shopping with us! ☺️"
        );

        mailSender.send(message);
    }
}
