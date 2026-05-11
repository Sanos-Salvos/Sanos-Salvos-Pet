package com.sanossalvos.pet.PetDTO;

import lombok.Data;

@Data
public class MascotaDTO {
    private String nombre;
    private String raza;
    private String color;
    private String tamano;
    private String sexo;
    private Integer edad;
    private String tipoReporte; // "perdido" o "encontrado"
    private String contacto;
}