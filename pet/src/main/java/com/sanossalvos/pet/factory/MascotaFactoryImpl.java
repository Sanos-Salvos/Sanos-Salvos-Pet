package com.sanossalvos.pet.factory;

import com.sanossalvos.pet.dto.MascotaDTO;
import com.sanossalvos.pet.model.Mascota;
import org.springframework.stereotype.Component;

@Component
public class MascotaFactoryImpl implements IMascotaFactory {

    @Override
    public Mascota crearEntidad(MascotaDTO dto) {
        Mascota m = new Mascota();
        m.setNombre(dto.getNombre());
        m.setEspecie(dto.getEspecie());
        m.setRaza(dto.getRaza());
        m.setEstado(dto.getEstado());
        m.setLat(dto.getLat());
        m.setLng(dto.getLng());
        m.setComuna(dto.getComuna());
        m.setContacto(dto.getContacto());
        m.setImagen(dto.getImagen());
        m.setColor(dto.getColor());
        m.setTamano(dto.getTamano());
        m.setSexo(dto.getSexo());
        m.setEdad(dto.getEdad());
        m.setTipoReporte(dto.getTipoReporte());
        return m;
    }

    @Override
    public MascotaDTO crearDTO(Mascota m) {
        MascotaDTO dto = new MascotaDTO();
        dto.setId(m.getId());
        dto.setNombre(m.getNombre());
        dto.setEspecie(m.getEspecie());
        dto.setRaza(m.getRaza());
        dto.setEstado(m.getEstado());
        dto.setLat(m.getLat());
        dto.setLng(m.getLng());
        dto.setComuna(m.getComuna());
        dto.setContacto(m.getContacto());
        dto.setImagen(m.getImagen());
        dto.setColor(m.getColor());
        dto.setTamano(m.getTamano());
        dto.setSexo(m.getSexo());
        dto.setEdad(m.getEdad());
        dto.setTipoReporte(m.getTipoReporte());
        return dto;
    }
}
