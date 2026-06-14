package com.sanossalvos.pet.factory;

import com.sanossalvos.pet.dto.MascotaDTO;
import com.sanossalvos.pet.model.Mascota;

public interface IMascotaFactory {
    Mascota crearEntidad(MascotaDTO dto);
    MascotaDTO crearDTO(Mascota entidad);
}