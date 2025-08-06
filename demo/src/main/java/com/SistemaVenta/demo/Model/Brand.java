package com.SistemaVenta.demo.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Entity
@Table(name = "marcas")
@Getter @Setter @ToString
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank(message = "El nombre de la marca no puede estar vacío")
    private String nombre;
    
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imagen;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;
}
