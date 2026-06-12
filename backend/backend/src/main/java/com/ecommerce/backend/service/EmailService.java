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
            Email from = new Email(fromEmail);
            Email to = new Email(toEmail);
            String subject = "Order Confirmed 🎉 - Cartify";
            Content content = new Content("text/plain",
                    "Hello " + username + ",\n\n" +
                            "Your order has been placed successfully!\n\n" +
                            "Order ID: #" + orderId + "\n" +
                            "Total Amount: ₹" + amount + "\n\n" +
                            "Thank you for shopping with Cartify! 😊"
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








//package com.ecommerce.backend.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailService {
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @Async
//    public void sendOrderConfirmation( String toEmail, String username, Double amount, Long orderId) {
//
//        SimpleMailMessage message = new SimpleMailMessage();
//
//        message.setTo(toEmail);
//        message.setSubject("Order Confirmed 🎉");
//
//        message.setText(
//                        "Hello " + username + ",\n\n" +
//                        "Your order has been placed successfully.\n\n" +
//                        "Order ID: " + orderId + "\n" +
//                        "Total Amount: ₹" + amount + "\n\n" +
//                        "Thank you for shopping with us! ☺️"
//        );
//
//        mailSender.send(message);
//    }
//}
