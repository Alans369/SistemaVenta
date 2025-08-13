package com.SistemaVenta.demo.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "detalles_venta")
@Getter @Setter @ToString
public class DetailsSale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Sale venta;

    
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Product producto;
    
    private double precio;

    private double cantidad;

    private double subtotal;
}
