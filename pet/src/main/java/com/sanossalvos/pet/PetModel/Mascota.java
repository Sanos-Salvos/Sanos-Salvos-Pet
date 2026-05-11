package com.sanossalvos.pet.PetModel;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "mascotas")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private String estado; // "perdido" o "encontrado"
    private String contacto;
}