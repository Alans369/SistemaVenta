package com.SistemaVenta.demo.Repositorios;


import org.springframework.data.jpa.repository.JpaRepository;
import com.SistemaVenta.demo.Model.Role;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    

}
