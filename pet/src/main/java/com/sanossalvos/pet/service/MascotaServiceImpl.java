package com.sanossalvos.pet.service;

import com.sanossalvos.pet.dto.MascotaDTO;
import com.sanossalvos.pet.factory.IMascotaFactory;
import com.sanossalvos.pet.model.Mascota;
import com.sanossalvos.pet.producer.PetProducer;
import com.sanossalvos.pet.repository.MascotaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MascotaServiceImpl implements IMascotaService {

    private static final Logger log = LoggerFactory.getLogger(MascotaServiceImpl.class);

    private final MascotaRepository repository;
    private final IMascotaFactory factory;
    private final PetProducer producer;

    public MascotaServiceImpl(MascotaRepository repository, IMascotaFactory factory, PetProducer producer) {
        this.repository = repository;
        this.factory = factory;
        this.producer = producer;
    }

    @Override
    public MascotaDTO registrarMascota(MascotaDTO dto) {
        Mascota entidad = factory.crearEntidad(dto);
        Mascota guardada = repository.save(entidad);

        // Bloque seguro contra caídas de Kafka: Permite que el test 'kafkaException_noRompeGuardado' pase con éxito
        try {
            producer.enviarEventoMascota("NUEVA_MASCOTA_REGISTRADA:" + guardada.getId());
        } catch (Exception e) {
            log.error("No se pudo enviar el evento de registro a Kafka para la mascota ID {}: {}", guardada.getId(), e.getMessage());
        }

        return factory.crearDTO(guardada);
    }

    @Override
    public List<MascotaDTO> listarTodas() {
        return repository.findAll().stream()
                .map(factory::crearDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MascotaDTO buscarPorId(Long id) {
        Mascota mascota = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + id));
        return factory.crearDTO(mascota);
    }

    @Override
    public MascotaDTO actualizar(Long id, MascotaDTO dto) {
        Mascota mascotaExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + id));

        String reporteAnterior = mascotaExistente.getTipoReporte();

        mascotaExistente.setNombre(dto.getNombre());
        mascotaExistente.setEspecie(dto.getEspecie());
        mascotaExistente.setRaza(dto.getRaza());
        mascotaExistente.setEstado(dto.getEstado());
        mascotaExistente.setLat(dto.getLat());
        mascotaExistente.setLng(dto.getLng());
        mascotaExistente.setComuna(dto.getComuna());
        mascotaExistente.setContacto(dto.getContacto());
        mascotaExistente.setImagen(dto.getImagen());
        mascotaExistente.setColor(dto.getColor());
        mascotaExistente.setTamano(dto.getTamano());
        mascotaExistente.setSexo(dto.getSexo());
        mascotaExistente.setEdad(dto.getEdad());
        mascotaExistente.setTipoReporte(dto.getTipoReporte());

        if (reporteAnterior != null && !reporteAnterior.equals(dto.getTipoReporte())) {
            // Bloque seguro contra caídas de Kafka en la actualización de reportes
            try {
                producer.enviarEventoMascota("CAMBIO_REPORTE_MASCOTA:" + id + ":" + dto.getTipoReporte());
            } catch (Exception e) {
                log.error("No se pudo enviar el evento de cambio de reporte a Kafka para la mascota ID {}: {}", id, e.getMessage());
            }
        }

        Mascota mascotaActualizada = repository.save(mascotaExistente);
        return factory.crearDTO(mascotaActualizada);
    }

    @Override
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Mascota no encontrada con ID: " + id);
        }
        repository.deleteById(id);
    }
}