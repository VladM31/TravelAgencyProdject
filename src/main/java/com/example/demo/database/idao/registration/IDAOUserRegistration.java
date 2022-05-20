package com.example.demo.database.idao.registration;

import com.example.demo.entity.important.Customer;
import com.example.demo.entity.important.User;
import org.springframework.lang.Nullable;

import java.sql.SQLException;

public interface IDAOUserRegistration<U extends User> extends CodeError {

    public boolean userIsBooked(U user);
    @Nullable
    public U findUserByEmail(String email);
    @Nullable
    public U findUserByCode(long code);
    public long findCodeByUser(U id);
    public boolean saveForRegistration(U user) throws SQLException;
    public boolean saveAsRegistered(U user);
}
