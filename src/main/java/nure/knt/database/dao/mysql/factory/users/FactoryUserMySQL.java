package nure.knt.database.dao.mysql.factory.users;

import nure.knt.database.idao.factory.users.IFactoryUser;
import nure.knt.entity.enums.Role;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.important.User;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component("Factory_User_MySQL")
public class FactoryUserMySQL implements IFactoryUser {
    @Override
    public User getUser(ResultSet resultSet) {
        User user = new User();
        try{
            user.setId(resultSet.getLong("user_pk"));
            user.setNumber(resultSet.getString("number"));
            user.setEmail(resultSet.getString("email"));

            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));

            user.setName(resultSet.getString("name"));
            user.setCountry(resultSet.getString("country"));
            user.setActive(resultSet.getBoolean("active"));

            user.setDateRegistration(resultSet.getTimestamp("date_registration").toLocalDateTime());
            user.setRole(Role.valueOf(resultSet.getString("role")));
            user.setTypeState(TypeState.valueOf(resultSet.getString("type_state")));

            return user;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
