package com.sanossalvos.pet.service;

import com.sanossalvos.pet.dto.MascotaDTO;
import java.util.List;

public interface IMascotaService {
    MascotaDTO registrarMascota(MascotaDTO dto);
    List<MascotaDTO> listarTodas();
    MascotaDTO buscarPorId(Long id);
    MascotaDTO actualizar(Long id, MascotaDTO dto);
    void eliminar(Long id);
}