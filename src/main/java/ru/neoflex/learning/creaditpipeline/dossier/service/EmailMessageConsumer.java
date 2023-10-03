package ru.neoflex.learning.creaditpipeline.dossier.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.neoflex.learning.creaditpipeline.dossier.model.EmailMessage;

@Service
@RequiredArgsConstructor
public class EmailMessageConsumer {

    private final EmailMessageService emailMessageService;
    private final ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    @KafkaListener(topics = "#{'${kafka.topic.finish-registration}'}")
    public void finishRegistrationConsume(String message) throws JsonProcessingException {

        EmailMessage emailMessage = objectMapper.readValue(message, EmailMessage.class);
        emailMessageService.emailSender(emailMessage);
    }

    @KafkaListener(topics = "#{'${kafka.topic.create-documents}'}")
    public void createDocumentsConsume(String message) throws JsonProcessingException {

        EmailMessage emailMessage = objectMapper.readValue(message, EmailMessage.class);
        emailMessageService.emailSender(emailMessage);
    }

    @KafkaListener(topics = "#{'${kafka.topic.send-documents}'}")
    public void sendDocumentsConsume(String message) throws JsonProcessingException {

        EmailMessage emailMessage = objectMapper.readValue(message, EmailMessage.class);
        emailMessageService.emailSender(emailMessage);
    }

    @KafkaListener(topics = "#{'${kafka.topic.send-ses}'}")
    public void sendSesConsume(String message) throws JsonProcessingException {

        EmailMessage emailMessage = objectMapper.readValue(message, EmailMessage.class);
        emailMessageService.emailSender(emailMessage);
    }

    @KafkaListener(topics = "#{'${kafka.topic.credit-issued}'}")
    public void creditIssuedConsume(String message) throws JsonProcessingException {

        EmailMessage emailMessage = objectMapper.readValue(message, EmailMessage.class);
        emailMessageService.emailSender(emailMessage);
    }

    @KafkaListener(topics = "#{'${kafka.topic.application-denied}'}")
    public void applicationDeniedConsume(String message) throws JsonProcessingException {

        EmailMessage emailMessage = objectMapper.readValue(message, EmailMessage.class);
        emailMessageService.emailSender(emailMessage);
    }
}
