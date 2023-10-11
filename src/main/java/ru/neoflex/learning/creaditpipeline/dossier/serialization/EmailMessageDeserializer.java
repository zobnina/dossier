package ru.neoflex.learning.creaditpipeline.dossier.serialization;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import ru.neoflex.learning.creaditpipeline.dossier.model.EmailMessage;

import java.io.IOException;

@Slf4j
public class EmailMessageDeserializer implements Deserializer<EmailMessage> {

    private final JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    @Override
    public EmailMessage deserialize(String topic, byte[] data) {

        try {
            return jsonMapper.readValue(data, EmailMessage.class);
        } catch (IOException e) {
            log.error("deserialize() - error: ", e);
            return null;
        }
    }
}
