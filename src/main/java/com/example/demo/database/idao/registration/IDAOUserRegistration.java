package com.example.demo.database.idao.registration;

import com.example.demo.entity.important.Customer;
import com.example.demo.entity.important.User;

import java.sql.SQLException;

public interface IDAOUserRegistration<U extends User> extends CodeError {

    public boolean userIsBooked(U user);
    public U findUserByEmail(String email);
    public U findUserByCode(long code);
    public long findCodeByUser(U id);
    public boolean saveForRegistration(U user) throws SQLException;
    public boolean saveAsRegistered(U user);
}
