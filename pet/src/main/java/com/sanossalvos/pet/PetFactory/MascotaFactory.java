package com.sanossalvos.pet.PetFactory;

import com.sanossalvos.pet.PetDTO.MascotaDTO;
import com.sanossalvos.pet.PetModel.Mascota;

public class MascotaFactory {
    public static Mascota crearDesdeDTO(MascotaDTO dto) {
        Mascota mascota = new Mascota();
        mascota.setNombre(dto.getNombre());
        mascota.setRaza(dto.getRaza());
        mascota.setColor(dto.getColor());
        mascota.setTamano(dto.getTamano());
        mascota.setSexo(dto.getSexo());
        mascota.setEdad(dto.getEdad());
        mascota.setContacto(dto.getContacto());
        mascota.setEstado(dto.getTipoReporte().toLowerCase());
        return mascota;
    }
}