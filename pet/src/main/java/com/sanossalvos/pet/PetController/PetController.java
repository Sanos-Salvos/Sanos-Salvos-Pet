package com.sanossalvos.pet.PetController;

import com.sanossalvos.pet.PetDTO.MascotaDTO;
import com.sanossalvos.pet.PetService.IMascotaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pet")
public class PetController {

    private final IMascotaService service;

    public PetController(IMascotaService service) {
        this.service = service;
    }

    @PostMapping("/registrar")
    public ResponseEntity<MascotaDTO> registrar(@RequestBody MascotaDTO dto) {
        return ResponseEntity.ok(service.registrarMascota(dto));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<MascotaDTO>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<MascotaDTO> actualizar(@PathVariable Long id, @RequestBody MascotaDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Mascota eliminada correctamente");
    }
}