package nure.knt.database.dao.mysql.registration;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.entity.HandlerUser;
import nure.knt.database.dao.mysql.tools.MySQLCore;
import nure.knt.database.idao.IConnectorGetter;
import nure.knt.database.idao.registration.IDAOUserRegistration;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.important.Customer;
import nure.knt.tools.WorkWithCountries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Component("DAO_Customer_Registration_MySQL")
public class DAOCustomerRegistration extends RegistrationCore<Customer> {

    private static final String INSERT_CUSTOMER =
            "INSERT INTO customer (male,user_id) VALUES (?,?);";

    @Override
    public boolean saveForRegistration(Customer user,String code) throws SQLException {

        long userId = super.saveForRegistrationUser(user,code);

        if(HandlerRegistrationUser.tryStatement(super.conn, (p) -> HandlerCustomerRegistration.saveCustomer(p,user),INSERT_CUSTOMER)== NOT_SAVE){
            return NOT_SAVE;
        }

        return true;
    }
}


class HandlerCustomerRegistration{

    protected static void saveCustomer(PreparedStatement statement,Customer customer) {
        int index_customer = 0;
        try {
            statement.setBoolean(++index_customer,customer.isMale());
            statement.setLong(++index_customer,customer.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
