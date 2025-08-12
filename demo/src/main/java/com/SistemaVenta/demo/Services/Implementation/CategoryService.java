package com.SistemaVenta.demo.Services.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemaVenta.demo.Model.Category;
import com.SistemaVenta.demo.Repositorios.ICategoryRepository;
import com.SistemaVenta.demo.Services.Interfaces.ICategory;

@Service
public class CategoryService implements ICategory {

    @Autowired
    private ICategoryRepository repository;

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

}
