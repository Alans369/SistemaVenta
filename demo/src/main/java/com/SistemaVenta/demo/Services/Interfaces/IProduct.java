package com.SistemaVenta.demo.Services.Interfaces;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.SistemaVenta.demo.Model.Product;

public interface IProduct {

    Product createOrEdit(Product producto);

    boolean  delet(Integer id);

    Page<Product> selectAll(Integer marcaId, Pageable pageable);

    Page<Product> searchBynameOrCategory(String nombre,Integer categoryId, Integer marcaId,Pageable page);
    
     Product selectById(Integer id);
}
