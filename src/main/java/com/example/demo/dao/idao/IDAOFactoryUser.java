package com.example.demo.dao.idao;

import com.example.demo.entity.User;

public interface IDAOFactoryUser {
    public User getUserByUsernameDependingOnRole(String username);
}
