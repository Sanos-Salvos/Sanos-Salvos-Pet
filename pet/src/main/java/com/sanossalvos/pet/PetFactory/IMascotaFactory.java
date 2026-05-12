package com.sanossalvos.pet.PetFactory;

import com.sanossalvos.pet.PetDTO.MascotaDTO;
import com.sanossalvos.pet.PetModel.Mascota;

public interface IMascotaFactory {
    Mascota crearEntidad(MascotaDTO dto);
    MascotaDTO crearDTO(Mascota entidad);
}