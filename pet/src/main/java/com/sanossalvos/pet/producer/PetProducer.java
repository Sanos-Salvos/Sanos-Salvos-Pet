package com.sanossalvos.pet.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import java.util.concurrent.CompletableFuture;

@Component
public class PetProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public PetProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarEventoMascota(String mensaje) {
        CompletableFuture<SendResult<String, String>> future =
                this.kafkaTemplate.send("mascota-registrada", mensaje);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Evento enviado a Kafka con éxito: [Offset "
                        + result.getRecordMetadata().offset() + "]");
            } else {
                System.err.println("No se pudo enviar el evento a Kafka de fondo: " + ex.getMessage());

            }
        });
    }
}