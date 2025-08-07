package com.SistemaVenta.demo.Services.Interfaces;

import com.SistemaVenta.demo.Model.User;

public interface IUser {

    User finById(Integer id);
    
    User create(User user);

    User findByUsername(String username);

    

}
