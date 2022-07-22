package nure.knt.database.dao.mysql.factory;

import nure.knt.database.idao.factory.IFactoryEntity;
import nure.knt.entity.enums.Role;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.important.Customer;
import nure.knt.entity.important.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
public class ConfigurationFactoryUsers {

    @Bean("Factory_User_MySQL")
    public IFactoryEntity<User> getUserFactory(){
        return (resultSet -> {
            try{
                User user = new User();
                setInfoFromResultSetToUser(user,resultSet);
                return user;
            }catch (SQLException e){
                e.printStackTrace();
            }
            return null;
        });

    }

    @Bean("Factory_Customer_MySQL")
    public IFactoryEntity<Customer> getCustomerFactory(){
        return (resultSet -> {
           try{
               Customer customer = new Customer();
               setInfoFromResultSetToUser(customer,resultSet);
               customer.setCustomerId(resultSet.getLong("customer_pk"));
               customer.setMale(resultSet.getBoolean("male"));
               return customer;
           }catch (SQLException e){
               e.printStackTrace();
           }
           return null;
        });
    }

    public static void setInfoFromResultSetToUser(User user, ResultSet resultSet) throws SQLException {
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
    }
}
