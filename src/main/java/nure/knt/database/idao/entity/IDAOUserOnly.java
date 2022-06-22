package nure.knt.database.idao.entity;

import nure.knt.entity.enums.Role;
import nure.knt.entity.important.User;

import java.util.List;
import java.util.Set;

public interface IDAOUserOnly extends IDAOUserSQL<User> {

    public List<User> findByRoles(Set<Role> roles);
    public boolean updateStateUser(Long id, Boolean active);
}
