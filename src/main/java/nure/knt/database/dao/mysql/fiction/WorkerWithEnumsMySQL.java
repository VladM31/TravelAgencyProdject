package nure.knt.database.dao.mysql.fiction;

import nure.knt.database.dao.mysql.tools.MySQLCore;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.enums.IEnumId;
import nure.knt.entity.enums.Role;
import nure.knt.entity.enums.TypeState;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.SQLException;

@Component
public class WorkerWithEnumsMySQL extends MySQLCore {

    private static final String SELECT_ALL_ROLES = "SELECT id,name FROM role;";

    private static final String SELECT_ALL_CONDITION_COMMODITY = "SELECT id,name FROM condition_commodity;";

    private static final String SELECT_ALL_TYPE_STATE = "SELECT id,name FROM type_state;";

    @PostConstruct
    private void setIdForEnums(){
        try(java.sql.Statement statement = conn.getSqlStatement()){

            try(java.sql.ResultSet resultSet = statement.executeQuery(SELECT_ALL_ROLES)){
                Role role = null;
                while (resultSet.next()){
                    role = Role.valueOf(resultSet.getString("name"));
                    role.setId(resultSet.getInt("id"));
                }
            }

            try(java.sql.ResultSet resultSet = statement.executeQuery(SELECT_ALL_CONDITION_COMMODITY)){
                ConditionCommodity commodity = null;
                while (resultSet.next()){
                    commodity = ConditionCommodity.valueOf(resultSet.getString("name"));
                    commodity.setId(resultSet.getInt("id"));
                }
            }

            try(java.sql.ResultSet resultSet = statement.executeQuery(SELECT_ALL_TYPE_STATE)){
                TypeState typeState = null;
                while (resultSet.next()){
                    typeState = TypeState.valueOf(resultSet.getString("name"));
                    typeState.setId(resultSet.getInt("id"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
