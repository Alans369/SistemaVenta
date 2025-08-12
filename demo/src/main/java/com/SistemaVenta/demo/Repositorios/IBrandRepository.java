package com.SistemaVenta.demo.Repositorios;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SistemaVenta.demo.Model.Brand;

public interface IBrandRepository extends JpaRepository<Brand, Integer> {

    Optional<Brand> findByUserId(Integer userId);

}
