package nure.knt.database.dao.mysql.registration;

import nure.knt.database.idao.IDAOSecurity;
import nure.knt.database.idao.entity.*;
import nure.knt.database.idao.terms.ITermInformation;
import nure.knt.database.idao.terms.users.ITermUser;
import nure.knt.entity.enums.TypeState;
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
import java.util.Optional;
import java.util.function.Function;

@Component("DA0_MySQL_Security")
public class DAOSecurityMySQL implements IDAOSecurity {

    private List<IDAOUserWithTerms<? extends User,? extends ITermUser>> daoUserList;
    @Autowired
    public void setParameters( List<IDAOUserWithTerms<? extends User,? extends ITermUser>>  list){
        this.daoUserList = list;
    }

    private static final User USER_NOT_FOUND = null;

    @Nullable
    @Override
    public User getUserByUsernameDependingOnRole(String username) {
        Optional<? extends User> userOptional = null;
        for (IDAOUserWithTerms<? extends User,? extends ITermUser> dao:daoUserList) {
            userOptional = dao
                    .findOneBy(dao.term().usernameIs(username)
                    .typeStateIn(TypeState.REGISTERED).end());
            if(userOptional.isPresent()){
                return userOptional.get();
            }
        }
        return USER_NOT_FOUND;
    }


}
