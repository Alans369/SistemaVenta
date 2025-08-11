package com.SistemaVenta.demo.Services.Interfaces;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.SistemaVenta.demo.Model.Product;

public interface IProduct {

    Product CreateOrEdit(Product producto);



     boolean  delet(Integer id);

    Page<Product> selectAll(Integer marcaId, Pageable pageable);

}
