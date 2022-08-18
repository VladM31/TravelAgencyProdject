package nure.knt.database.dao.mysql.registration;

import nure.knt.database.idao.IDAOSecurity;
import nure.knt.database.idao.terms.users.ITermUser;
import nure.knt.database.service.implement.users.IServiceUser;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.important.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("DA0_MySQL_Security")
public class DAOSecurityMySQL implements IDAOSecurity {

    private List<IServiceUser<? extends User,? extends ITermUser>> serviceUsers;
    @Autowired
    public void setParameters( List<IServiceUser<? extends User,? extends ITermUser>>  list){
        this.serviceUsers = list;
    }

    private static final User USER_NOT_FOUND = null;

    @Nullable
    @Override
    public User getUserByUsernameDependingOnRole(String username) {
        Optional<? extends User> userOptional = null;
        for (IServiceUser<? extends User,? extends ITermUser> service: serviceUsers) {
            userOptional = service
                    .findOneBy(service.term().usernameIs(username)
                    .typeStateIn(TypeState.REGISTERED).end());
            if(userOptional.isPresent()){
                return userOptional.get();
            }
        }
        return USER_NOT_FOUND;
    }


}
