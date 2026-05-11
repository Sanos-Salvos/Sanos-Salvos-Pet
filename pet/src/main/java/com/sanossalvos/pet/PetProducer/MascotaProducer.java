package com.sanossalvos.pet.PetProducer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MascotaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public MascotaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarEvento(String mensaje) {

        kafkaTemplate.send("MascotaRegistrada", mensaje);
    }
}