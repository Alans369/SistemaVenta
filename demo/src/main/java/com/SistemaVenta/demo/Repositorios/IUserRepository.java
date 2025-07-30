package com.SistemaVenta.demo.Repositorios;
import com.SistemaVenta.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
    
    // Custom query methods can be defined here if needed
    // For example:
    // Optional<User> findByUsername(String username);
    
    // You can also use Spring Data JPA's derived query methods
    // to automatically implement common queries.

}
