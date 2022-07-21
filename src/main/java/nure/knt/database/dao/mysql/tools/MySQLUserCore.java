package nure.knt.database.dao.mysql.tools;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.idao.core.IDAODeleteById;
import nure.knt.database.idao.core.IEntityIsBooked;
import nure.knt.database.idao.tools.IConcatScripts;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.important.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public abstract class MySQLUserCore<U extends User> extends MySQLCore implements IDAODeleteById, IEntityIsBooked<U> {

    @Autowired
    protected IConcatScripts concatScripts;
    @Autowired
    @Qualifier("Getter_User_Id")
    protected AtomicLong getterId;

    private static final String DELETE_FROM_USER_WHERE_USER_ID_IN =  "DELETE FROM user WHERE user.id IN( "+ HandlerSqlDAO.REPLACE_SYMBOL + " )";
    @Override
    public int deleteAllById(Iterable<Long> ids) {
        return HandlerSqlDAO.deleteByIdIn(super.conn,DELETE_FROM_USER_WHERE_USER_ID_IN,ids);
    }

    @Override
    public int deleteById(Long id) {
        return this.deleteAllById(List.of(id));
    }

    private static final String SELECT_WHERE_EMAIL_OR_NUMBER_OR_USERNAME_IS =
            "SELECT user.id FROM user left join type_state ON user.type_state_id = type_state.id WHERE (email = ? OR number = ? OR username = ?) " +
                    "AND (type_state.name = ? OR type_state.name = ? AND user.date_registration > ?) ;";

    @Override
    public boolean isBooked(U user) {
        try(java.sql.PreparedStatement statement = super.conn.getSqlPreparedStatement(SELECT_WHERE_EMAIL_OR_NUMBER_OR_USERNAME_IS)){
            int position = 0;
            statement.setString(++position,user.getEmail());
            statement.setString(++position,user.getNumber());
            statement.setString(++position,user.getUsername());

            statement.setString(++position, TypeState.REGISTERED.toString());
            statement.setString(++position,TypeState.REGISTRATION.toString());
            statement.setTimestamp(++position, Timestamp.valueOf(LocalDateTime.now().minusMinutes(15l)));

            try(java.sql.ResultSet resultSet = statement.executeQuery()){
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected Optional<Long> saveUserAndReturnId(){


        return Optional.empty();
    }

}
