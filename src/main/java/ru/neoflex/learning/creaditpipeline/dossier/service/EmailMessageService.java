package ru.neoflex.learning.creaditpipeline.dossier.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.neoflex.learning.creaditpipeline.dossier.model.EmailMessage;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class EmailMessageService {

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender emailSender;

    public void emailSender(EmailMessage emailMessage) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(emailMessage.getAddress());
        message.setSubject(emailMessage.getTheme().getSubject());
        message.setText(format(emailMessage.getTheme().getText(), emailMessage.getApplicationId()));

        emailSender.send(message);
    }
}
