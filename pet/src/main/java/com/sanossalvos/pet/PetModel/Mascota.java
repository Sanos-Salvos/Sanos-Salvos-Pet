package com.sanossalvos.pet.PetModel;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "mascotas")
@Data
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String raza;
    private String color;
    private String tamano;
    private String sexo;
    private Integer edad;
    private String tipoReporte;
    private String contacto;
}