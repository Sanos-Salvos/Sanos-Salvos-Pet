package com.sanossalvos.pet.PetService;

import com.sanossalvos.pet.PetDTO.MascotaDTO;
import java.util.List;

public interface IMascotaService {
    MascotaDTO registrarMascota(MascotaDTO dto);
    List<MascotaDTO> listarTodas();
    MascotaDTO buscarPorId(Long id);
    MascotaDTO actualizar(Long id, MascotaDTO dto);
    void eliminar(Long id);
}