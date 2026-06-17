package com.travel.user_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendPasswordResetEmail(String to, String token) {
        // Ссылка, которая будет вести на ваш фронтенд
        String resetUrl = "http://localhost:5173/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Сброс пароля EcoTravel");
        message.setText("Для сброса пароля перейдите по ссылке (действительна 1 час):\n" + resetUrl);

        mailSender.send(message);
    }
}