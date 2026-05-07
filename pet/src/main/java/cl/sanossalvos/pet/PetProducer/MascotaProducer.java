package com.sanosysalvos.pet.producer;

import com.sanosysalvos.pet.model.Mascota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MascotaProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "mascotas_topic";

    public void enviarEventoMascotaRegistrada(Mascota mascota) {
        kafkaTemplate.send(TOPIC, "MascotaRegistrada", mascota);
        System.out.println("Evento enviado a Kafka: MascotaRegistrada -> " + mascota.getNombre());
    }
}