package com.SistemaVenta.demo.Services.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemaVenta.demo.Model.Product;
import com.SistemaVenta.demo.Repositorios.IProductRepository;
import com.SistemaVenta.demo.Services.Interfaces.IProduct;

@Service
public class ProductService implements IProduct {

    @Autowired
    private IProductRepository repository;

    @Override
    public Product CreateOrEdit(Product producto){
        return repository.save(producto);
    }

    @Override
    public Product delet(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Product seleatAll(Product producto) {
        // TODO Auto-generated method stub
        return null;
    }





}
