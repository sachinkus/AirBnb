package com.bnb.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class EmailService {


    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @Value("${email.address}")
    private String emailAdress;

    public void sendEmailWithAttachment(String toEmail, String subject, String body, File attachment) throws MessagingException, IOException, MessagingException {
        // Create a MimeMessage to send email with attachment
        MimeMessage message = javaMailSender.createMimeMessage();

        // Use MimeMessageHelper for easier handling of the message
        MimeMessageHelper helper = new MimeMessageHelper(message, true); // true for multipart

        // Set email fields
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(body);
        helper.setFrom(this.emailAdress);

        // Add the attachment
        if (attachment != null && attachment.exists()) {
            helper.addAttachment(attachment.getName(), attachment);
        }

        // Send the email
        javaMailSender.send(message);
    }
}
