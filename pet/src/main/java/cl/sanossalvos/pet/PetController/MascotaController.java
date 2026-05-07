package com.sanosysalvos.pet.controller;

import com.sanosysalvos.pet.dto.MascotaDTO;
import com.sanosysalvos.pet.model.Mascota;
import com.sanosysalvos.pet.service.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @PostMapping("/registrar")
    public ResponseEntity<Map<String, String>> registrarMascota(@RequestBody MascotaDTO mascotaDTO) {
        mascotaService.registrar(mascotaDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensaje", "Mascota registrada exitosamente"));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Mascota>> listarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(mascotaService.obtenerPorEstado(estado));
    }
}