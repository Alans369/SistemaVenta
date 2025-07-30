package com.SistemaVenta.demo.Repositorios;
import org.springframework.data.jpa.repository.JpaRepository;
import com.SistemaVenta.demo.Model.Brand;

public interface IBrandRepository extends JpaRepository<Brand, Long> {
    // Additional query methods can be defined here if needed

}
