package com.SistemaVenta.demo.Services.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.SistemaVenta.demo.Model.Product;
import com.SistemaVenta.demo.Repositorios.IProductRepository;
import com.SistemaVenta.demo.Services.Interfaces.IProduct;

@Service
public class ProductService implements IProduct {

    @Autowired
    private IProductRepository repository;

    @Override
    public Product createOrEdit(Product producto){
        return repository.save(producto);
    }

    @Override
    public  boolean  delet(Integer id) {
        int updatedRows = repository.softDeleteById(id);
        return updatedRows > 0;
    }

    @Override
    public Page<Product> searchBynameOrCategory(String nombre, Integer categoryId, Integer marcaId, Pageable page) {
        return repository.findWithFilters(nombre,categoryId,marcaId,page);
    }

    @Override
    public Product selectById(Integer id) {
        
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Product> findAll() {
       return repository.findAll();
    }


}
