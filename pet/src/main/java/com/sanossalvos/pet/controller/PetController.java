package com.sanossalvos.pet.controller;

import com.sanossalvos.pet.dto.MascotaDTO;
import com.sanossalvos.pet.service.IMascotaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pet")
@CrossOrigin(origins = "*")
public class PetController {

    private final IMascotaService service;

    public PetController(IMascotaService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<MascotaDTO> registrar(@RequestBody MascotaDTO dto) {
        return ResponseEntity.ok(service.registrarMascota(dto));
    }

    @GetMapping
    public ResponseEntity<List<MascotaDTO>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MascotaDTO> actualizar(@PathVariable Long id, @RequestBody MascotaDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Mascota eliminada correctamente");
    }
}