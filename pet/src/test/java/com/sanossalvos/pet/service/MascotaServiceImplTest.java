package com.sanossalvos.pet.service;

import com.sanossalvos.pet.dto.MascotaDTO;
import com.sanossalvos.pet.factory.IMascotaFactory;
import com.sanossalvos.pet.model.Mascota;
import com.sanossalvos.pet.producer.PetProducer;
import com.sanossalvos.pet.repository.MascotaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MascotaServiceImplTest {

    @Mock
    private MascotaRepository repository;

    @Mock
    private IMascotaFactory factory;

    @Mock
    private PetProducer producer;

    @InjectMocks
    private MascotaServiceImpl service;

    private MascotaDTO sampleDTO;
    private Mascota sampleEntity;

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

        sampleEntity = new Mascota();
        sampleEntity.setId(1L);
        sampleEntity.setNombre("Firulais");
        sampleEntity.setEspecie("Perro");
        sampleEntity.setRaza("Labrador");
        sampleEntity.setEstado("Perdido");
        sampleEntity.setLat(-33.45);
        sampleEntity.setLng(-70.66);
        sampleEntity.setComuna("Santiago");
        sampleEntity.setContacto("12345678");
        sampleEntity.setImagen("img.jpg");
        sampleEntity.setColor("Marrón");
        sampleEntity.setTamano("Grande");
        sampleEntity.setSexo("Macho");
        sampleEntity.setEdad(3);
        sampleEntity.setTipoReporte("PERDIDO");
    }

    // ==================== registrarMascota ====================

    @Test
    void registrarMascota_deberiaGuardarYEnviarEvento() {
        when(factory.crearEntidad(any(MascotaDTO.class))).thenReturn(sampleEntity);
        when(repository.save(any(Mascota.class))).thenReturn(sampleEntity);
        when(factory.crearDTO(any(Mascota.class))).thenReturn(sampleDTO);

        MascotaDTO result = service.registrarMascota(sampleDTO);

        assertNotNull(result);
        assertEquals("Firulais", result.getNombre());
        assertEquals(1L, result.getId());
        verify(repository).save(sampleEntity);
        verify(producer).enviarEventoMascota("NUEVA_MASCOTA_REGISTRADA:1");
    }

    @Test
    void registrarMascota_kafkaException_noRompeGuardado() {
        when(factory.crearEntidad(any(MascotaDTO.class))).thenReturn(sampleEntity);
        when(repository.save(any(Mascota.class))).thenReturn(sampleEntity);
        when(factory.crearDTO(any(Mascota.class))).thenReturn(sampleDTO);
        doThrow(new RuntimeException("Kafka no disponible")).when(producer).enviarEventoMascota(anyString());

        MascotaDTO result = service.registrarMascota(sampleDTO);

        assertNotNull(result);
        assertEquals("Firulais", result.getNombre());
        verify(repository).save(sampleEntity);
    }

    @Test
    void registrarMascota_deberiaRetornarDTOCreado() {
        MascotaDTO expectedDTO = MascotaDTO.builder().id(5L).nombre("Michi").especie("Gato").build();
        Mascota entity = new Mascota();
        entity.setId(5L);
        entity.setNombre("Michi");
        entity.setEspecie("Gato");

        MascotaDTO inputDTO = MascotaDTO.builder().nombre("Michi").especie("Gato").build();

        when(factory.crearEntidad(any(MascotaDTO.class))).thenReturn(entity);
        when(repository.save(any(Mascota.class))).thenReturn(entity);
        when(factory.crearDTO(any(Mascota.class))).thenReturn(expectedDTO);

        MascotaDTO result = service.registrarMascota(inputDTO);

        assertEquals(5L, result.getId());
        assertEquals("Michi", result.getNombre());
    }

    // ==================== listarTodas ====================

    @Test
    void listarTodas_deberiaRetornarListaDeDTOs() {
        Mascota entity2 = new Mascota();
        entity2.setId(2L);
        entity2.setNombre("Michi");

        MascotaDTO dto2 = MascotaDTO.builder().id(2L).nombre("Michi").build();
        when(repository.findAll()).thenReturn(new java.util.ArrayList<>(List.of(sampleEntity, entity2)));
        when(factory.crearDTO(sampleEntity)).thenReturn(sampleDTO);
        when(factory.crearDTO(entity2)).thenReturn(dto2);

        List<MascotaDTO> result = service.listarTodas();

        assertEquals(2, result.size());
        assertEquals("Firulais", result.get(0).getNombre());
        assertEquals("Michi", result.get(1).getNombre());
    }

    @Test
    void listarTodas_deberiaRetornarListaVacia() {
        when(repository.findAll()).thenReturn(List.of());

        List<MascotaDTO> result = service.listarTodas();

        assertTrue(result.isEmpty());
    }

    // ==================== buscarPorId ====================

    @Test
    void buscarPorId_deberiaRetornarMascota() {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleEntity));
        when(factory.crearDTO(sampleEntity)).thenReturn(sampleDTO);

        MascotaDTO result = service.buscarPorId(1L);

        assertNotNull(result);
        assertEquals("Firulais", result.getNombre());
    }

    @Test
    void buscarPorId_deberiaLanzarExcepcionSiNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.buscarPorId(99L));
        assertTrue(ex.getMessage().contains("99"));
    }

    // ==================== actualizar ====================

    @Test
    void actualizar_deberiaActualizarCamposYRetornarDTO() {
        MascotaDTO updateDTO = MascotaDTO.builder()
                .nombre("Firulais II")
                .especie("Perro")
                .raza("Golden")
                .estado("Encontrado")
                .lat(-33.5)
                .lng(-70.7)
                .comuna("Providencia")
                .contacto("87654321")
                .imagen("new.jpg")
                .color("Dorado")
                .tamano("Mediano")
                .sexo("Hembra")
                .edad(5)
                .tipoReporte("ENCONTRADO")
                .build();

        Mascota updatedEntity = new Mascota();
        updatedEntity.setId(1L);
        updatedEntity.setNombre("Firulais II");
        updatedEntity.setTipoReporte("ENCONTRADO");

        MascotaDTO updatedDTO = MascotaDTO.builder().id(1L).nombre("Firulais II").tipoReporte("ENCONTRADO").build();

        when(repository.findById(1L)).thenReturn(Optional.of(sampleEntity));
        when(repository.save(any(Mascota.class))).thenReturn(updatedEntity);
        when(factory.crearDTO(updatedEntity)).thenReturn(updatedDTO);

        MascotaDTO result = service.actualizar(1L, updateDTO);

        assertNotNull(result);
        assertEquals("Firulais II", result.getNombre());
        verify(repository).save(any(Mascota.class));
    }

    @Test
    void actualizar_deberiaLanzarExcepcionSiNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.actualizar(99L, sampleDTO));
    }

    @Test
    void actualizar_deberiaActualizarCuandoTipoReporteNoCambia() {
        // Note: the service sets tipoReporte from DTO before comparing,
        // so the Kafka condition (tipoReporte changed) is never true.
        // This test verifies actualizar works without Kafka being triggered.
        MascotaDTO updateDTO = MascotaDTO.builder()
                .nombre("Nuevo")
                .tipoReporte("PERDIDO")
                .build();

        sampleEntity.setTipoReporte("PERDIDO");

        when(repository.findById(1L)).thenReturn(Optional.of(sampleEntity));
        when(repository.save(any(Mascota.class))).thenReturn(sampleEntity);
        when(factory.crearDTO(any(Mascota.class))).thenReturn(updateDTO);

        MascotaDTO result = service.actualizar(1L, updateDTO);

        assertNotNull(result);
        assertEquals("Nuevo", result.getNombre());
        verify(repository).save(any(Mascota.class));
        verify(producer, never()).enviarEventoMascota(anyString());
    }

    // ==================== eliminar ====================

    @Test
    void eliminar_deberiaEliminarSiExiste() {
        when(repository.existsById(1L)).thenReturn(true);

        service.eliminar(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void eliminar_deberiaLanzarExcepcionSiNoExiste() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> service.eliminar(99L));
        verify(repository, never()).deleteById(anyLong());
    }
}
