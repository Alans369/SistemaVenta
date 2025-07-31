package com.SistemaVenta.demo.Services.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemaVenta.demo.Model.Role;
import com.SistemaVenta.demo.Model.User;
import com.SistemaVenta.demo.Repositorios.IRoleRepository;
import com.SistemaVenta.demo.Repositorios.IUserRepository;
import com.SistemaVenta.demo.Services.Interfaces.IUser;

@Service
public class UserServices implements IUser {

    @Autowired
    private IUserRepository repository;

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public User create(User user) {
        return repository.save(user);
    }

    public User createWithRole(User user, Integer roleId) {

        System.out.println(roleId);
        
        Role role = roleRepository.findById(roleId)

            .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + roleId));
        
         System.out.println(role);
        user.setRole(role);
        return repository.save(user);
    }

    @Override
    public User finById(Integer id) {
        return repository.findById(id).get();
    }
}
