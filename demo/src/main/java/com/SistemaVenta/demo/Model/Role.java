package com.SistemaVenta.demo.Model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.Entity;

@Entity
@Table(name = "roles")
@Getter @Setter @ToString
public class Role {

   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
}