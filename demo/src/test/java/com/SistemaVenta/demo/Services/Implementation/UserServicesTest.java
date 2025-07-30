package com.SistemaVenta.demo.Services.Implementation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.SistemaVenta.demo.Model.User;
import com.SistemaVenta.demo.Repositorios.IUserRepository;

class UserServicesTest {

    @Mock
    private IUserRepository repository;

    @InjectMocks
    private UserServices userServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateWithAny() {
        // Arrange
        User user = new User();
        user.setId(1);
        user.setNombre("Test User");
        user.setCorreo("test@example.com");
        user.setContraseña("password123");

        User otroUser = new User();
        otroUser.setId(1);
        otroUser.setNombre("Test User");
        otroUser.setCorreo("test@example.com");
        otroUser.setContraseña("password123");

        
           // Usando any() - funcionará con cualquier objeto User
        when(repository.save(any(User.class))).thenReturn(user);

        // Act
        User result = userServices.create(otroUser);


        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getNombre(), result.getNombre());
        assertEquals(user.getCorreo(), result.getCorreo());
        verify(repository).save(otroUser);
    }

   
    

    @Test
    void testFindById() {
        // Arrange
        User user = new User();
        user.setId(1);
        user.setNombre("Test User");
        user.setCorreo("test@example.com");
        user.setContraseña("password123");
        
        when(repository.findById(1)).thenReturn(Optional.of(user));

        // Act
        User result = userServices.finById(1);

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getNombre(), result.getNombre());
        assertEquals(user.getCorreo(), result.getCorreo());
        verify(repository).findById(1);
    }
}
