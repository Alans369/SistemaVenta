package com.SistemaVenta.demo.Repositorios;
import com.SistemaVenta.demo.Model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Integer> {

     Optional<User> findByUsername(String username);
    
   
    
}
