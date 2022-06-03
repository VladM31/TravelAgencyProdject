package nure.knt.database.dao.mysql.registration;

import nure.knt.database.idao.IDAOSecurity;
import nure.knt.database.idao.entity.IDAOCourierSQL;
import nure.knt.database.idao.entity.IDAOCustomerSQL;
import nure.knt.database.idao.entity.IDAOTravelAgencySQL;
import nure.knt.database.idao.entity.IDAOUserSQL;
import nure.knt.entity.important.Courier;
import nure.knt.entity.important.Customer;
import nure.knt.entity.important.TravelAgency;
import nure.knt.entity.important.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component("DA0_SECURITY_MYSQL")
public class DAOSecurityMySQL implements IDAOSecurity {

    private List<IDAOUserSQL> daoUsers;

    @Autowired
    public void setDaoUsers(List<IDAOUserSQL> daoUsers) {
        this.daoUsers = daoUsers;
    }
    private static final User USER_NOT_FOUND = null;

    @Nullable
    @Override
    public User getUserByUsernameDependingOnRole(String username) {
        User user = USER_NOT_FOUND;
        for (IDAOUserSQL dao:daoUsers) {
            user = dao.findByUsername(username);
            if(user != USER_NOT_FOUND){
                return user;
            }
        }
        return USER_NOT_FOUND;
    }


}
