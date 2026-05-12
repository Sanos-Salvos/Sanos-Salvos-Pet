package com.sanossalvos.pet.PetProducer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PetProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public PetProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarEventoMascota(String mensaje) {

        this.kafkaTemplate.send("MascotaRegistrada", mensaje);
    }
}