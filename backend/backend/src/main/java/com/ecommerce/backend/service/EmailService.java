package com.ecommerce.backend.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    @Value("${sendgrid.from.email}")
    private String fromEmail;

    @Async
    public void sendOrderConfirmation(String toEmail, String username, Double amount, Long orderId) {
        try {
            Email from = new Email(fromEmail, "Cartify Store"); // Add sender name
            Email to = new Email(toEmail);
            String subject = "Order Confirmed #" + orderId + " - Cartify";

            // HTML content looks more legitimate
            Content content = new Content("text/html",
                    "<div style='font-family:Arial,sans-serif;max-width:600px;margin:0 auto;padding:20px;'>" +
                            "<h2 style='color:#ff6b35;'>🛒 Cartify</h2>" +
                            "<h3>Order Confirmed! 🎉</h3>" +
                            "<p>Hello <b>" + username + "</b>,</p>" +
                            "<p>Your order has been placed successfully!</p>" +
                            "<table style='width:100%;border-collapse:collapse;margin:20px 0;'>" +
                            "<tr style='background:#f5f5f5;'>" +
                            "<td style='padding:10px;border:1px solid #ddd;'><b>Order ID</b></td>" +
                            "<td style='padding:10px;border:1px solid #ddd;'>#" + orderId + "</td>" +
                            "</tr>" +
                            "<tr>" +
                            "<td style='padding:10px;border:1px solid #ddd;'><b>Total Amount</b></td>" +
                            "<td style='padding:10px;border:1px solid #ddd;color:#ff6b35;'><b>₹" + amount + "</b></td>" +
                            "</tr>" +
                            "</table>" +
                            "<p>Thank you for shopping with <b>Cartify</b>! 😊</p>" +
                            "<hr style='border:1px solid #eee;'/>" +
                            "<p style='color:#999;font-size:12px;'>This is an automated email. Please do not reply.</p>" +
                            "</div>"
            );

            Mail mail = new Mail(from, subject, to, content);
            SendGrid sg = new SendGrid(sendGridApiKey);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sg.api(request);
            System.out.println("Email sent to: " + toEmail);
        } catch (Exception e) {
            System.out.println("Email failed: " + e.getMessage());
        }
    }
}
