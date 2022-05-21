package com.example.demo.database.dao.registration;

import com.example.demo.database.idao.IDAOSecurity;
import com.example.demo.database.idao.entity.IDAOCourierSQL;
import com.example.demo.database.idao.entity.IDAOCustomerSQL;
import com.example.demo.database.idao.entity.IDAOTravelAgencySQL;
import com.example.demo.database.idao.entity.IDAOUserSQL;
import com.example.demo.entity.important.Courier;
import com.example.demo.entity.important.Customer;
import com.example.demo.entity.important.TravelAgency;
import com.example.demo.entity.important.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.security.PrivilegedAction;
import java.util.function.Function;

@Component("DA0_SECURITY_MYSQL")
public class DAOSecurityMySQL implements IDAOSecurity {

    private IDAOCourierSQL<Courier> daoCourier;
    private IDAOTravelAgencySQL<TravelAgency> daoTravelAgency;
    private IDAOCustomerSQL<Customer> daoCustomer;
    private IDAOUserSQL<User> daoUser;

    @Autowired
    @Qualifier("DAO_COURIER_MYSQL")
    public void setDaoCourier(IDAOCourierSQL<Courier> daoCourier) {
        this.daoCourier = daoCourier;
    }

    @Autowired
    @Qualifier("DA0_TRAVEL_AGENCY_MYSQL")
    public void setDaoTravelAgency(IDAOTravelAgencySQL<TravelAgency> daoTravelAgency) {
        this.daoTravelAgency = daoTravelAgency;
    }

    @Autowired
    @Qualifier("DAO_CUSTOMER_MYSQL")
    public void setDaoCustomer(IDAOCustomerSQL<Customer> daoCustomer) {
        this.daoCustomer = daoCustomer;
    }

    @Autowired
    @Qualifier("DAO_USER_MYSQL")
    public void setDaoUser(IDAOUserSQL<User> daoUser) {
        this.daoUser = daoUser;
    }

    private static final User USER_NOT_FOUND = null;

    @Nullable
    @Override
    public User getUserByUsernameDependingOnRole(String username) {
        CheckToNull checkToNull = new CheckToNull(
                daoCourier::findByUsername,
                daoTravelAgency::findByUsername,
                daoCustomer::findByUsername,
                daoUser::findByUsername);

        return checkToNull.find(username);
    }

    private static class CheckToNull{
        private Function<String,User>[] functions;

        CheckToNull(@NonNull Function<String,User> ...functions){
            this.functions = functions;
        }

        public User find(String username){
            User user = USER_NOT_FOUND;
            for (Function<String,User> function : functions){
                user = function.apply(username);
                if (user != USER_NOT_FOUND){
                    return user;
                }
            }
            return USER_NOT_FOUND;
        }
    }
}
