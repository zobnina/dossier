package ru.neoflex.learning.creaditpipeline.dossier;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.javafaker.Faker;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import ru.neoflex.learning.creaditpipeline.dossier.model.EmailMessage;
import ru.neoflex.learning.creaditpipeline.dossier.model.Theme;
import ru.neoflex.learning.creaditpipeline.dossier.service.EmailMessageConsumer;
import ru.neoflex.learning.creaditpipeline.dossier.service.EmailMessageService;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.after;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@SpringBootTest
class DossierApplicationTests {

    static final int DELAY = 5000;

    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    static {
        kafkaContainer.start();
    }

    static KafkaProducer<String, String> producer;

    @BeforeAll
    static void init() {
        producer = new KafkaProducer<>(getProducerProperties());
        assertAll(
            () -> assertTrue(kafkaContainer.isCreated()),
            () -> assertTrue(kafkaContainer.isRunning())
        );
    }

    static Map<String, Object> getProducerProperties() {

        return Map.of(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers(),
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    }

    static final Faker FAKER = new Faker();
    static final JsonMapper JSON_MAPPER = JsonMapper.builder().findAndAddModules().build();

    @SpyBean
    EmailMessageConsumer emailMessageConsumer;

    @SpyBean
    EmailMessageService emailMessageService;

    @MockBean
    JavaMailSender javaMailSender;

    @BeforeEach
    void setUp() {
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void finishRegistrationConsumeTest() throws JsonProcessingException {
        final EmailMessage emailMessage = getEmailMessage(Theme.FINISH_REGISTRATION);
        final String message = JSON_MAPPER.writeValueAsString(emailMessage);
        producer.send(new ProducerRecord<>("finish-registration", message));
        verify(emailMessageConsumer, after(DELAY)).finishRegistrationConsume(message);
        verify(emailMessageService).emailSender(emailMessage);
        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void createDocumentsConsumeTest() throws JsonProcessingException {
        final EmailMessage emailMessage = getEmailMessage(Theme.CREATE_DOCUMENTS);
        final String message = JSON_MAPPER.writeValueAsString(emailMessage);
        producer.send(new ProducerRecord<>("create-documents", message));
        verify(emailMessageConsumer, after(DELAY)).createDocumentsConsume(message);
        verify(emailMessageService).emailSender(emailMessage);
        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendDocumentsConsumeTest() throws JsonProcessingException {
        final EmailMessage emailMessage = getEmailMessage(Theme.SEND_DOCUMENTS);
        final String message = JSON_MAPPER.writeValueAsString(emailMessage);
        producer.send(new ProducerRecord<>("send-documents", message));
        verify(emailMessageConsumer, after(DELAY)).sendDocumentsConsume(message);
        verify(emailMessageService).emailSender(emailMessage);
        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendSesConsumeTest() throws JsonProcessingException {
        final EmailMessage emailMessage = getEmailMessage(Theme.SEND_SES);
        final String message = JSON_MAPPER.writeValueAsString(emailMessage);
        producer.send(new ProducerRecord<>("send-ses", message));
        verify(emailMessageConsumer, after(DELAY)).sendSesConsume(message);
        verify(emailMessageService).emailSender(emailMessage);
        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void creditIssuedConsumeTest() throws JsonProcessingException {
        final EmailMessage emailMessage = getEmailMessage(Theme.CREDIT_ISSUED);
        final String message = JSON_MAPPER.writeValueAsString(emailMessage);
        producer.send(new ProducerRecord<>("credit-issued", message));
        verify(emailMessageConsumer, after(DELAY)).creditIssuedConsume(message);
        verify(emailMessageService).emailSender(emailMessage);
        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void applicationDeniedConsumeTest() throws JsonProcessingException {
        final EmailMessage emailMessage = getEmailMessage(Theme.APPLICATION_DENIED);
        final String message = JSON_MAPPER.writeValueAsString(emailMessage);
        producer.send(new ProducerRecord<>("application-denied", message));
        verify(emailMessageConsumer, after(DELAY)).applicationDeniedConsume(message);
        verify(emailMessageService).emailSender(emailMessage);
        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }

    private EmailMessage getEmailMessage(Theme finishRegistration) {
        return EmailMessage.builder()
            .applicationId(UUID.randomUUID())
            .address(FAKER.internet().emailAddress())
            .theme(finishRegistration)
            .build();
    }
}
