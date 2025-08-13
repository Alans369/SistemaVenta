package com.SistemaVenta.demo.Model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "productos")
@Getter @Setter @ToString

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Category category;
    
    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Brand marca;   
    
    @NotBlank(message = "el nombre no puede estar vacio")
    private String nombre;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imagen;
    private double precioCompra;
    private double precioVenta;
    @NotBlank(message = "El stock no puede estar vacío")
    private String stock;
    private Boolean estado;
}
