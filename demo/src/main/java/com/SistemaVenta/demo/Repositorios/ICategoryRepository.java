package com.SistemaVenta.demo.Repositorios;
import com.SistemaVenta.demo.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
    // Aquí puedes definir métodos personalizados si es necesario   

}
