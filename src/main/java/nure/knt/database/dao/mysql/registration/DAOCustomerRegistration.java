package nure.knt.database.dao.mysql.registration;

import nure.knt.entity.important.Customer;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
