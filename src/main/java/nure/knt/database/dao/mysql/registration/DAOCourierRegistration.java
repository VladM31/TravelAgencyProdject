package nure.knt.database.dao.mysql.registration;

import nure.knt.database.idao.registration.IDAOUserRegistration;
import nure.knt.entity.important.Courier;
import nure.knt.entity.important.Customer;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component("DAO_Courier_Registration_MySQL")
public class DAOCourierRegistration extends RegistrationCore<Courier> {

    @Override
    public boolean saveForRegistration(Courier courier,String code) throws SQLException {

        long userId = super.saveForRegistrationUser(courier,code);

        if(HandlerRegistrationUser.tryStatement(super.conn, (p) -> HandlerCourierRegistration.saveCourier(p,courier),HandlerCourierRegistration.INSERT_COURIER)== NOT_SAVE){
            return NOT_SAVE;
        }

        return true;
    }
}

class HandlerCourierRegistration{
    public static final String INSERT_COURIER = "INSERT INTO courier(city,address,date_birth,does_he_want,user_id) VALUE(?,?,?,?,?);";

    protected static void saveCourier(PreparedStatement statement, Courier courier) {
        int position = 0;
        try {
            statement.setString(++position,courier.getCity());
            statement.setString(++position,courier.getAddress());
            statement.setDate(++position, Date.valueOf(courier.getDateBirth()));
            statement.setBoolean(++position,courier.isDoesHeWant());
            statement.setLong(++position,courier.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
