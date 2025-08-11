package com.SistemaVenta.demo.Services.Interfaces;

import com.SistemaVenta.demo.Model.Product;

public interface IProduct {

    Product CreateOrEdit(Product producto);

    Product seleatAll(Product producto);

     boolean  delet(Integer id);

}
