package com.SistemaVenta.demo.Repositorios;

import com.SistemaVenta.demo.Model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISaleRepository extends JpaRepository <Sale,Long> {

}
