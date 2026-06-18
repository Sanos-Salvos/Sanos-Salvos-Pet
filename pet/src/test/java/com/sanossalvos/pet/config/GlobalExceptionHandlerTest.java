package com.sanossalvos.pet.config;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void cuandoSeLanzaResourceNotFound_deberiaDevolver404() {
        GlobalExceptionHandler.ResourceNotFoundException ex =
                new GlobalExceptionHandler.ResourceNotFoundException("Mascota no encontrada");

        ResponseEntity<Map<String, Object>> respuesta = handler.handleResourceNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
        assertEquals("Recurso no encontrado", respuesta.getBody().get("error"));
        assertEquals("Mascota no encontrada", respuesta.getBody().get("message"));
        assertNotNull(respuesta.getBody().get("timestamp"));
    }

    @Test
    void cuandoSeLanzaRuntimeException_deberiaDevolver400() {
        RuntimeException ex = new RuntimeException("Error fatal de lógica");

        ResponseEntity<Map<String, Object>> respuesta = handler.handleRuntimeException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        assertEquals("Error en la solicitud", respuesta.getBody().get("error"));
        assertTrue(respuesta.getBody().get("message").toString().contains("Error fatal de lógica"));
    }

    @Test
    void cuandoSeLanzaExceptionGenerica_deberiaDevolver500() {
        Exception ex = new Exception("Error interno de sistema");

        ResponseEntity<Map<String, Object>> respuesta = handler.handleAllExceptions(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, respuesta.getStatusCode());
        assertEquals("Error Interno del Servidor", respuesta.getBody().get("error"));
    }
}