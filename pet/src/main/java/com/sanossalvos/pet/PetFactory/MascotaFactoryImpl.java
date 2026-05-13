package com.sanossalvos.pet.PetFactory;

import com.sanossalvos.pet.PetDTO.MascotaDTO;
import com.sanossalvos.pet.PetModel.Mascota;
import org.springframework.stereotype.Component;

@Component
public class MascotaFactoryImpl implements IMascotaFactory {

    @Override
    public Mascota crearEntidad(MascotaDTO dto) {
        Mascota m = new Mascota();
        m.setNombre(dto.getNombre());
        m.setRaza(dto.getRaza());
        m.setColor(dto.getColor());
        m.setTamano(dto.getTamano());
        m.setSexo(dto.getSexo());
        m.setEdad(dto.getEdad());
        m.setTipoReporte(dto.getTipoReporte());
        m.setContacto(dto.getContacto());
        return m;
    }

    @Override
    public MascotaDTO crearDTO(Mascota m) {
        MascotaDTO dto = new MascotaDTO();
        dto.setId(m.getId());
        dto.setNombre(m.getNombre());
        dto.setRaza(m.getRaza());
        dto.setColor(m.getColor());
        dto.setTamano(m.getTamano());
        dto.setSexo(m.getSexo());
        dto.setEdad(m.getEdad());
        dto.setTipoReporte(m.getTipoReporte());
        dto.setContacto(m.getContacto());
        return dto;
    }
}