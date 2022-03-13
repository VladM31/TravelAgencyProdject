package com.example.demo.tempClasses.dao;

import com.example.demo.dao.idao.IDAOCustomer;
import com.example.demo.dao.idao.IDAOSecurity;
import com.example.demo.dao.idao.IDAOTravelAgency;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Role;
import com.example.demo.entity.TravelAgency;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("DAOSecurity")
public class DAOSecurityHashSet implements IDAOSecurity {
    @Autowired
    private IDAOTravelAgency<TravelAgency> dataTravel;
    @Autowired
    private IDAOCustomer<Customer> dataCustomer;

    @Override
    public User getUserByUsernameDependingOnRole(String username) {

        User getUser = dataCustomer.findByUsername(username);
        if (getUser == null) {
            getUser = dataTravel.findByUsername(username);
        }
        dataCustomer.findAll().forEach(System.out::println);
        dataTravel.findAll().forEach(System.out::println);
        return getUser;
    }

    @Override
    public boolean canAddThisUser(User user) {
        if (user.getRole() == Role.TRAVEL_AGENCY) {
            return this.canAddTravelAgency(user);
        } else if (user.getRole() == Role.CUSTOMER) {
            return this.canAddCustomer(user);
        }
        return false;
    }

    private boolean canAddTravelAgency(User user)
    {
        if (!(user instanceof TravelAgency)) {
            return false;
        }

        TravelAgency travel = (TravelAgency)user;

        if (this.dataTravel.findByUsernameOrPasswordOrNumberOrEmail(travel).size() != 0) {
            return false;
        }
        if (this.dataTravel.findByKVED(travel.getKved()) != null)
            return false;
        if (this.dataTravel.findByAddress(travel.getAddress())!= null)
            return false;
        if(this.dataTravel.findByNameTravelAgency(travel.getNameTravelAgency()) != null)
            return false;
        if (travel.getEgrpoy() != null)
            if (this.dataTravel.findByEGRPOY(travel.getEgrpoy()) != null)
                return false;
        else
            if (this.dataTravel.findByRNEKPN(travel.getRnekpn()) != null)
                return false;

        return true;
    }

    private boolean canAddCustomer(User user){
        if (!(user instanceof Customer)) {
            return false;
        }
        return true;
    }
}
