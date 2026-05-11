package com.sanossalvos.pet.PetService;

import com.sanossalvos.pet.PetDTO.MascotaDTO;
import com.sanossalvos.pet.PetFactory.MascotaFactory;
import com.sanossalvos.pet.PetModel.Mascota;
import com.sanossalvos.pet.PetProducer.MascotaProducer;
import com.sanossalvos.pet.PetRepository.MascotaRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class MascotaService {

    private final MascotaRepository repository;
    private final MascotaProducer producer;

    public MascotaService(MascotaRepository repository, MascotaProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    @CircuitBreaker(name = "petServiceCB", fallbackMethod = "fallbackRegistrar")
    public Mascota registrar(MascotaDTO dto) {
        Mascota mascota = MascotaFactory.crearDesdeDTO(dto);
        Mascota guardada = repository.save(mascota);


        producer.enviarEvento("Nueva mascota ID: " + guardada.getId());

        return guardada;
    }


    public Mascota fallbackRegistrar(MascotaDTO dto, Throwable t) {
        System.err.println("Circuit Breaker activado. Motivo: " + t.getMessage());
        return new Mascota();
    }
}