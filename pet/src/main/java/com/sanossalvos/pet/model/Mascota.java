package com.sanossalvos.pet.model;

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
    private String especie;
    private String raza;
    private String estado;
    private Double lat;
    private Double lng;
    private String comuna;
    private String contacto;
    @Column(columnDefinition = "TEXT")
    private String imagen;
    private String color;
    private String tamano;
    private String sexo;
    private Integer edad;
    private String tipoReporte;
}
