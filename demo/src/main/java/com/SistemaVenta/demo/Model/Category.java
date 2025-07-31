package com.SistemaVenta.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "categorias")
@Getter @Setter @ToString
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  id;

    @NotBlank (message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank (message = "La descripción no puede estar vacía")
    private String descripcion;
}
