package com.SistemaVenta.demo.Utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter @ToString
public class DtoProduct {
    private Integer id;
     
    private String nombre;

    private double precioCompra;

    private double precioVenta;

    private Integer stock;

}
