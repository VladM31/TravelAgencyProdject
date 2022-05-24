package nure.knt.database.idao;

import nure.knt.entity.important.User;

public interface IDAOSecurity {
    public User getUserByUsernameDependingOnRole(String username);
}
