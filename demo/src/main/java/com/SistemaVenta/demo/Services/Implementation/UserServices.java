package com.SistemaVenta.demo.Services.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemaVenta.demo.Model.User;

import com.SistemaVenta.demo.Repositorios.IUserRepository;
import com.SistemaVenta.demo.Services.Interfaces.IUser;

@Service
public class UserServices implements IUser {

    @Autowired
    private IUserRepository repository;

  
    @Override
    public User create(User user) {
        return repository.save(user);
    }

  

    @Override
    public User finById(Integer id) {
        return repository.findById(id).get();
    }



    
              

}
