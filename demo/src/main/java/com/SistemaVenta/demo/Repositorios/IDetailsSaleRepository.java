package com.SistemaVenta.demo.Repositorios;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SistemaVenta.demo.Model.DetailsSale;

public interface IDetailsSaleRepository extends JpaRepository <DetailsSale,Integer>{

 List<DetailsSale> findByVentaId(Integer ventaId);
  

}
