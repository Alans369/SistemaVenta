package com.SistemaVenta.demo.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    private Brand marca;    private String nombre;
    
    @NotBlank(message = "La descripción no puede estar vacía")
    private String imagen;
    
    @NotBlank(message = "El precio de compra no puede estar vacío")
    private double precioCompra;
    
    @NotBlank(message = "El precio de venta no puede estar vacío")
    private double precioVenta;
    
    @NotBlank(message = "El stock no puede estar vacío")
    private String stock;
    
    @NotBlank(message = "El estado no puede estar vacío")
    private Boolean estado;
}
