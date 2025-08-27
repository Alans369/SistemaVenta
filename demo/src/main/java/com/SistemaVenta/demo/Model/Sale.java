package com.SistemaVenta.demo.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "ventas")
@Getter @Setter @ToString
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;
    
    private LocalDateTime fecha;

    @OneToMany(mappedBy = "venta", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference // Evita la serialización circular
    private List<DetailsSale> detallesVenta = new ArrayList<>();
    
    @Column(name = "metodo_pago")
    private String metodoPago;
    
    private double total;
    
    // Método helper para agregar detalles
    public void addDetalle(DetailsSale detalle) {
        if (detallesVenta == null) {
            detallesVenta = new ArrayList<>();
        }
        detallesVenta.add(detalle);
        detalle.setVenta(this);
    }
}
