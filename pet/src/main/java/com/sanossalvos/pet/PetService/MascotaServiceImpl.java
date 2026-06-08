package com.sanossalvos.pet.PetService;

import com.sanossalvos.pet.PetDTO.MascotaDTO;
import com.sanossalvos.pet.PetFactory.IMascotaFactory;
import com.sanossalvos.pet.PetModel.Mascota;
import com.sanossalvos.pet.PetProducer.PetProducer;
import com.sanossalvos.pet.PetRepository.MascotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MascotaServiceImpl implements IMascotaService {

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
        try {
            producer.enviarEventoMascota("NUEVA_MASCOTA_REGISTRADA:" + guardada.getId());
        } catch (Exception e) {
            System.err.println("Kafka no disponible, evento no enviado: " + e.getMessage());
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

        try {
            if (mascotaExistente.getTipoReporte() != null && !mascotaExistente.getTipoReporte().equals(dto.getTipoReporte())) {
                producer.enviarEventoMascota("CAMBIO_REPORTE_MASCOTA:" + id + ":" + dto.getTipoReporte());
            }
        } catch (Exception e) {
            System.err.println("Kafka no disponible, evento no enviado: " + e.getMessage());
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
