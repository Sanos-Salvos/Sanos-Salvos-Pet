package com.sanossalvos.pet.controller;

import com.sanossalvos.pet.PetController.PetController;
import com.sanossalvos.pet.PetDTO.MascotaDTO;
import com.sanossalvos.pet.PetService.IMascotaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    @Mock
    private IMascotaService service;

    @InjectMocks
    private PetController controller;

    private MascotaDTO sampleDTO;

    @BeforeEach
    void setUp() {
        sampleDTO = MascotaDTO.builder()
                .id(1L)
                .nombre("Firulais")
                .especie("Perro")
                .raza("Labrador")
                .estado("Perdido")
                .lat(-33.45)
                .lng(-70.66)
                .comuna("Santiago")
                .contacto("12345678")
                .imagen("img.jpg")
                .color("Marrón")
                .tamano("Grande")
                .sexo("Macho")
                .edad(3)
                .tipoReporte("PERDIDO")
                .build();
    }

    // ==================== POST /registrar ====================

    @Test
    void registrar_deberiaRetornar200ConDTO() {
        when(service.registrarMascota(any(MascotaDTO.class))).thenReturn(sampleDTO);

        ResponseEntity<MascotaDTO> response = controller.registrar(sampleDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Firulais", response.getBody().getNombre());
        assertEquals(1L, response.getBody().getId());
        verify(service).registrarMascota(sampleDTO);
    }

    // ==================== GET /listar ====================

    @Test
    void listar_deberiaRetornar200ConLista() {
        MascotaDTO dto2 = MascotaDTO.builder().id(2L).nombre("Michi").build();
        when(service.listarTodas()).thenReturn(List.of(sampleDTO, dto2));

        ResponseEntity<List<MascotaDTO>> response = controller.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Firulais", response.getBody().get(0).getNombre());
        assertEquals("Michi", response.getBody().get(1).getNombre());
    }

    @Test
    void listar_deberiaRetornarListaVacia() {
        when(service.listarTodas()).thenReturn(List.of());

        ResponseEntity<List<MascotaDTO>> response = controller.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    // ==================== GET /{id} ====================

    @Test
    void obtenerPorId_deberiaRetornar200ConMascota() {
        when(service.buscarPorId(1L)).thenReturn(sampleDTO);

        ResponseEntity<MascotaDTO> response = controller.obtenerPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Firulais", response.getBody().getNombre());
    }

    @Test
    void obtenerPorId_deberiaLanzarExcepcionSiNoExiste() {
        when(service.buscarPorId(99L)).thenThrow(new RuntimeException("Mascota no encontrada"));

        assertThrows(RuntimeException.class, () -> controller.obtenerPorId(99L));
    }

    // ==================== PUT /actualizar/{id} ====================

    @Test
    void actualizar_deberiaRetornar200ConDTOModificado() {
        MascotaDTO updatedDTO = MascotaDTO.builder().id(1L).nombre("Firulais II").build();
        when(service.actualizar(eq(1L), any(MascotaDTO.class))).thenReturn(updatedDTO);

        ResponseEntity<MascotaDTO> response = controller.actualizar(1L, updatedDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Firulais II", response.getBody().getNombre());
    }

    @Test
    void actualizar_deberiaLanzarExcepcionSiNoExiste() {
        when(service.actualizar(eq(99L), any(MascotaDTO.class)))
                .thenThrow(new RuntimeException("Mascota no encontrada"));

        assertThrows(RuntimeException.class, () -> controller.actualizar(99L, sampleDTO));
    }

    // ==================== DELETE /eliminar/{id} ====================

    @Test
    void eliminar_deberiaRetornar200ConMensaje() {
        doNothing().when(service).eliminar(1L);

        ResponseEntity<String> response = controller.eliminar(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Mascota eliminada correctamente", response.getBody());
        verify(service).eliminar(1L);
    }

    @Test
    void eliminar_deberiaLanzarExcepcionSiNoExiste() {
        doThrow(new RuntimeException("Mascota no encontrada")).when(service).eliminar(99L);

        assertThrows(RuntimeException.class, () -> controller.eliminar(99L));
    }
}
