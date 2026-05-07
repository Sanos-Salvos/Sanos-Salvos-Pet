package com.sanosysalvos.pet.service;

import com.sanosysalvos.pet.dto.MascotaDTO;
import com.sanosysalvos.pet.model.Mascota;
import com.sanosysalvos.pet.factory.MascotaFactory;
import com.sanosysalvos.pet.repository.MascotaRepository;
import com.sanosysalvos.pet.producer.MascotaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private MascotaProducer mascotaProducer; // Usamos el nuevo Producer

    public Mascota registrar(MascotaDTO dto) {
        Mascota nuevaMascota = MascotaFactory.crearReporte(dto);
        Mascota guardada = mascotaRepository.save(nuevaMascota);

        // Llamamos al producer independiente
        mascotaProducer.enviarEventoMascotaRegistrada(guardada);

        return guardada;
    }

    public List<Mascota> obtenerPorEstado(String estado) {
        return mascotaRepository.findByEstado(estado);
    }
}