package nure.knt.database.dao.mysql.factory;

import nure.knt.database.idao.factory.IFactoryEntity;
import nure.knt.entity.enums.Role;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.important.Courier;
import nure.knt.entity.important.Customer;
import nure.knt.entity.important.TravelAgency;
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

    @Bean("Factory_Courier_MySQL")
    public IFactoryEntity<Courier> getCourierFactory(){
        return (resultSet -> {
            try{
                Courier courier = new Courier();
                setInfoFromResultSetToUser(courier,resultSet);
                courier.setIdCourier(resultSet.getLong("courier_pk"));

                courier.setCity(resultSet.getString("city"));
                courier.setAddress(resultSet.getString("address"));
                courier.setDateBirth(resultSet.getDate("date_birth").toLocalDate());
                courier.setDoesHeWant(resultSet.getBoolean("does_he_want"));
                return courier;
            }catch (SQLException e){
                e.printStackTrace();
            }
            return null;
        });
    }

    @Bean("Factory_Travel_Agency_MySQL")
    public IFactoryEntity<TravelAgency> getTravelAgencyFactory(){
        return resultSet -> {
            try{
                TravelAgency travelAgency = new TravelAgency();
                setInfoFromResultSetToUser(travelAgency,resultSet);
                travelAgency.setTravelId(resultSet.getLong("travel_agency_pk"));

                travelAgency.setRating(resultSet.getFloat("rating"));
                travelAgency.setKved(resultSet.getString("kved"));

                travelAgency.setEgrpoyOrRnekpn(resultSet.getLong("egrpoy_or_rnykpn"));
                travelAgency.setEgrpoy(resultSet.getBoolean("is_egrpoy"));
                travelAgency.setAddress(resultSet.getString("address"));

                travelAgency.setFullNameDirector(resultSet.getString("full_name_director"));
                travelAgency.setDescribeAgency(resultSet.getString("describe_agency"));
                travelAgency.setUrlPhoto(resultSet.getString("url_photo"));
                return travelAgency;
            }catch (SQLException e){
                e.printStackTrace();
            }
            return null;
        };
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
