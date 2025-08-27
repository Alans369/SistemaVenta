package com.SistemaVenta.demo.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Getter @Setter
@ToString(exclude = "venta")
public class DetailsSale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id")
    @JsonBackReference 
    private Sale venta;
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Product producto;
    
    private double precio;

    private Integer cantidad;

    private double subtotal;
}
