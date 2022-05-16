package com.example.demo.database.idao.registration;

import com.example.demo.entity.important.TravelAgency;
import com.example.demo.entity.important.User;

public interface IDAOUserRegistrationConfirm<U extends User> extends IDAOUserRegistration<U>{
    public boolean saveForConfirmation(U user);
}
