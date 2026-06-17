package com.sanossalvos.pet.producer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;

class PetProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private PetProducer petProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void enviarEventoMascota_DeberiaEnviarMensajeCorrectamente() {
        String mensaje = "NUEVA_MASCOTA_REGISTRADA:1";

        // Creamos un CompletableFuture ya completado con éxito para que no devuelva null
        CompletableFuture<SendResult<String, String>> future = CompletableFuture.completedFuture(new SendResult<>(null, null));

        // Le indicamos al mock que cuando se llame a send(), devuelva nuestro futuro simulado
        when(kafkaTemplate.send(anyString(), anyString())).thenReturn(future);

        // Ejecutar el método bajo prueba
        petProducer.enviarEventoMascota(mensaje);

        // Verificar que interactúa correctamente con el template de Kafka
        verify(kafkaTemplate, times(1)).send(anyString(), eq(mensaje));
    }
}