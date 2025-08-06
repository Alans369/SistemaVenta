package com.SistemaVenta.demo.Services.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemaVenta.demo.Model.Brand;
import com.SistemaVenta.demo.Repositorios.IBrandRepository;
import com.SistemaVenta.demo.Services.Interfaces.IBrand;

@Service
public class BrandService implements IBrand {

    @Autowired
    private IBrandRepository brandRepository;

    @Override
    public Brand create(Brand brand) {
        // TODO Auto-generated method stub
        return brandRepository.save(brand);
    }



}
