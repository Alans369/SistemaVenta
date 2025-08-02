package com.SistemaVenta.demo.Model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.*;




@Entity

@Table(name = "usuarios")

@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre no puede estar vacío")
   
    private String username;

    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellido;


    @Email(message = "El correo debe ser válido")
    @NotBlank(message = "El correo no puede estar vacío")
    private String correo;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña debe tener al menos 8 caracteres")
     
    @NotBlank(message = "La contraseña no puede estar vacía")
   
    private String password;

  


}
