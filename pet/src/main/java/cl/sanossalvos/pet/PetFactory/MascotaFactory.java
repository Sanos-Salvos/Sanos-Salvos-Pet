package com.sanosysalvos.pet.factory;

import com.sanosysalvos.pet.dto.MascotaDTO;
import com.sanosysalvos.pet.model.Mascota;
import java.time.LocalDateTime;

public class MascotaFactory {
    public static Mascota crearReporte(MascotaDTO dto) {
        Mascota mascota = new Mascota();
        mascota.setNombre(dto.getNombre());
        mascota.setRaza(dto.getRaza());
        mascota.setColor(dto.getColor());
        mascota.setTamano(dto.getTamano());
        mascota.setSexo(dto.getSexo());
        mascota.setEdad(dto.getEdad());
        mascota.setUsuarioId(dto.getUsuarioId());
        mascota.setFechaRegistro(LocalDateTime.now());

        if ("PERDIDO".equalsIgnoreCase(dto.getTipoReporte())) {
            mascota.setEstado("PERDIDO");
        } else if ("ENCONTRADO".equalsIgnoreCase(dto.getTipoReporte())) {
            mascota.setEstado("ENCONTRADO");
        } else {
            throw new IllegalArgumentException("Tipo de reporte no válido");
        }
        return mascota;
    }
}