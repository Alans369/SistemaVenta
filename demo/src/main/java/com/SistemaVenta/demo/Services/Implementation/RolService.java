package com.SistemaVenta.demo.Services.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemaVenta.demo.Model.Role;
import com.SistemaVenta.demo.Repositorios.IRoleRepository;
import com.SistemaVenta.demo.Services.Interfaces.IRole;

@Service
public class RolService implements IRole {

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public Role findById(Integer id) {
        return roleRepository.findById(id).get();
    }
    

}
