package com.sanossalvos.pet.PetProducer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PetProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private volatile boolean kafkaAvailable = true;

    public PetProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarEventoMascota(String mensaje) {
        if (!kafkaAvailable) {
            return;
        }
        try {
            this.kafkaTemplate.send("MascotaRegistrada", mensaje).get(2, java.util.concurrent.TimeUnit.SECONDS);
        } catch (Exception e) {
            // Kafka no disponible - marcar como no disponible y continuar
            kafkaAvailable = false;
            System.err.println("Kafka no disponible, deshabilitando eventos: " + e.getMessage());
        }
    }
}
