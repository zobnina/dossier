package ru.neoflex.learning.creaditpipeline.dossier.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.neoflex.learning.creaditpipeline.dossier.model.EmailMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailMessageConsumer {

    private final EmailMessageService emailMessageService;

    @KafkaListener(topics = "#{'${kafka.topic.finish-registration}'}")
    public void finishRegistrationConsume(EmailMessage emailMessage) {

        log.info("finishRegistrationConsume() - start: message = {}", emailMessage);
        emailMessageService.emailSender(emailMessage);
    }

    @KafkaListener(topics = "#{'${kafka.topic.create-documents}'}")
    public void createDocumentsConsume(EmailMessage emailMessage) {

        log.info("createDocumentsConsume() - start: message = {}", emailMessage);
        emailMessageService.emailSender(emailMessage);
    }

    @KafkaListener(topics = "#{'${kafka.topic.send-documents}'}")
    public void sendDocumentsConsume(EmailMessage emailMessage) {

        log.info("sendDocumentsConsume() - start: message = {}", emailMessage);
        emailMessageService.emailSender(emailMessage);
    }

    @KafkaListener(topics = "#{'${kafka.topic.send-ses}'}")
    public void sendSesConsume(EmailMessage emailMessage) {

        log.info("sendSesConsume() - start: message = {}", emailMessage);
        emailMessageService.emailSender(emailMessage);
    }

    @KafkaListener(topics = "#{'${kafka.topic.credit-issued}'}")
    public void creditIssuedConsume(EmailMessage emailMessage) {

        log.info("creditIssuedConsume() - start: message = {}", emailMessage);
        emailMessageService.emailSender(emailMessage);
    }

    @KafkaListener(topics = "#{'${kafka.topic.application-denied}'}")
    public void applicationDeniedConsume(EmailMessage emailMessage) {

        log.info("applicationDeniedConsume() - start: message = {}", emailMessage);
        emailMessageService.emailSender(emailMessage);
    }
}
