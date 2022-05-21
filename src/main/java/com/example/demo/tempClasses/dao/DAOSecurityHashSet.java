package com.example.demo.tempClasses.dao;

import com.example.demo.database.idao.entity.IDAOCustomerSQL;
import com.example.demo.database.idao.IDAOSecurity;
import com.example.demo.database.idao.entity.IDAOTravelAgencySQL;
import com.example.demo.entity.important.Customer;
import com.example.demo.entity.important.TravelAgency;
import com.example.demo.entity.important.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("DAOSecurity")
public class DAOSecurityHashSet implements IDAOSecurity {
    @Autowired
    private IDAOTravelAgencySQL<TravelAgency> dataTravel;
    @Autowired
    private IDAOCustomerSQL<Customer> dataCustomer;

    @Override
    public User getUserByUsernameDependingOnRole(String username) {

        User getUser = dataCustomer.findByUsername(username);
        if (getUser == null) {
            getUser = dataTravel.findByUsername(username);
        }
        return getUser;
    }
}
