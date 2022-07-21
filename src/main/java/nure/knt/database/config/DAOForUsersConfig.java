package nure.knt.database.config;

import nure.knt.database.idao.terms.fieldenum.CustomerField;
import nure.knt.database.idao.terms.fieldenum.IUserField;
import nure.knt.database.idao.terms.fieldenum.UserField;
import nure.knt.database.idao.tools.IConnectorGetter;
import nure.knt.entity.important.Customer;
import nure.knt.entity.important.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

@Configuration
@PropertySource("classpath:property/users/UserProperty.properties")
public class DAOForUsersConfig {

    @Bean("Getter_User_Id")
    @Autowired
    public AtomicLong getterAtomicUserId(IConnectorGetter connector,
                                         @Value("${dao.for.users.id.script}") String script,
                                         @Value("${dao.for.users.id.script.name.id}") String nameId){
        AtomicLong atomicLong = new AtomicLong();

        try(java.sql.PreparedStatement statement = connector.getSqlPreparedStatement(script);
            java.sql.ResultSet resultSet = statement.executeQuery()){

            if(resultSet.next()){
                atomicLong.set(resultSet.getLong(nameId));
            }else{
                throw new NullPointerException("not found max id for User");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return atomicLong;
    }

    @Bean("Get_Object_By_Field_For_User")
    public Map<IUserField, Function<User,Object>> userFieldToObject(){
        Map<IUserField, Function<User,Object>> map = new HashMap<>();

        HandlerDAOForUsersConfig.setUserFieldToObject(map);

        return map;
    }

    @Bean("Get_Object_By_Field_For_Customer")
    public Map<IUserField, Function<Customer,Object>> customerFieldToObject(){
        Map<IUserField, Function<Customer,Object>> map = new HashMap<>();

        map.put(CustomerField.CUSTOMER_ID,User::getId);
        map.put(CustomerField.MALE,User::getId);

        HandlerDAOForUsersConfig.setUserFieldToObject(map);

        return map;
    }
}

class HandlerDAOForUsersConfig{
    public static <U extends User> void setUserFieldToObject(Map<IUserField, Function<U,Object>> map){
        map.put(UserField.ID,User::getId);

        map.put(UserField.NUMBER,User::getNumber);
        map.put(UserField.EMAIL,User::getEmail);
        map.put(UserField.USERNAME,User::getUsername);

        map.put(UserField.COUNTRY,User::getCountry);
        map.put(UserField.NAME,User::getName);
        map.put(UserField.ACTIVE,User::isActive);

        map.put(UserField.DATE_REGISTRATION,User::getDateRegistration);
        map.put(UserField.ROLE,User::getRole);
        map.put(UserField.TYPE_STATE,User::getTypeState);

    }
}
