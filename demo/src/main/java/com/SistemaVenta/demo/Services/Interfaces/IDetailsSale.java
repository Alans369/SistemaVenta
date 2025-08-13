package com.SistemaVenta.demo.Services.Interfaces;

import java.util.List;

import com.SistemaVenta.demo.Model.DetailsSale;

public interface IDetailsSale {

    List<DetailsSale> buscarporventa(Integer ventaId);

}
