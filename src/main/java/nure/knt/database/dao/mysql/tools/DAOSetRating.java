package nure.knt.database.dao.mysql.tools;

import nure.knt.database.idao.temporary.IDAOSetRating;
import org.springframework.stereotype.Component;

import java.security.PrivilegedAction;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class DAOSetRating extends MySQLCore implements IDAOSetRating {

    private static final String SELECT = "SELECT value FROM user_rating WHERE customer_id = ? AND  " +
            "travel_agency_id = (SELECT travel_agency_id FROM tour_ad WHERE id = ?)  ;";

    @Override
    public boolean hasRating(Long idCustomer, Long idTourAd) {
        try(java.sql.PreparedStatement statement = super.conn.getSqlPreparedStatement(SELECT)){
            statement.setLong(1,idCustomer);
            statement.setLong(2,idTourAd);

            try(java.sql.ResultSet resultSet = statement.executeQuery()){
                return  resultSet.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static final String UPDATE = "UPDATE user_rating SET value = ?, date_registratio = ? WHERE travel_agency_id = (SELECT travel_agency_id FROM tour_ad WHERE id = ?) AND customer_id = ? ;";

    @Override
    public boolean updateRating(Long idCustomer, Long idTourAd,int value) {
        return this.useExecute(idCustomer,idTourAd,value,UPDATE);
    }

    private static final String INSERT = "INSERT INTO user_rating(value,date_registratio,travel_agency_id,customer_id) VALUE(?,?, (SELECT travel_agency_id FROM tour_ad WHERE id = ?),?);";

    @Override
    public boolean insertRating(Long idCustomer, Long idTourAd,int value) {
        return this.useExecute(idCustomer,idTourAd,value,INSERT);
    }

    private boolean useExecute(Long idCustomer, Long idTourAd,int value,String script){
        try(java.sql.PreparedStatement statement = super.conn.getSqlPreparedStatement(script)){
            int position = 0;
            statement.setInt(++position,value);
            statement.setTimestamp(++position, Timestamp.valueOf(LocalDateTime.now()));
            statement.setLong(++position,idTourAd);
            statement.setLong(++position,idCustomer);

            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
