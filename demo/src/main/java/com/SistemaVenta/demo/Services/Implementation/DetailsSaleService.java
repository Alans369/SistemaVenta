package com.SistemaVenta.demo.Services.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.SistemaVenta.demo.Model.DetailsSale;
import com.SistemaVenta.demo.Repositorios.IDetailsSaleRepository;
import com.SistemaVenta.demo.Services.Interfaces.IDetailsSale;

public class DetailsSaleService implements IDetailsSale {
    @Autowired
    private IDetailsSaleRepository repository;

    @Override
    public List<DetailsSale> buscarporventa(Integer ventaId) {
        
        return repository.findByVentaId(ventaId);
    }
    

}
