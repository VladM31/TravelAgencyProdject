package com.example.demo.dao.idao;

import com.example.demo.entity.User;

public interface IDAOSecurity {
    public User getUserByUsernameDependingOnRole(String username);
}
