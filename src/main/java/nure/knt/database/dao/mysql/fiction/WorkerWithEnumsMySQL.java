package nure.knt.database.dao.mysql.fiction;

import nure.knt.database.dao.mysql.tools.MySQLCore;
import nure.knt.database.idao.terms.ITermTourAd;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.enums.IEnumId;
import nure.knt.entity.enums.Role;
import nure.knt.entity.enums.TypeState;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.function.Function;

@Component
public class WorkerWithEnumsMySQL extends MySQLCore {

    private static final String SELECT_ALL_ROLES = "SELECT id,name FROM role;";

    private static final String SELECT_ALL_CONDITION_COMMODITY = "SELECT id,name FROM condition_commodity;";

    private static final String SELECT_ALL_TYPE_STATE = "SELECT id,name FROM type_state;";

    @PostConstruct
    private void setIdForEnums(){
        try(java.sql.Statement statement = conn.getSqlStatement()){

            HandlerWorkerWithEnumsMySQL.setId(statement,SELECT_ALL_ROLES,Role::valueOf);

            HandlerWorkerWithEnumsMySQL.setId(statement,SELECT_ALL_CONDITION_COMMODITY,ConditionCommodity::valueOf);

            HandlerWorkerWithEnumsMySQL.setId(statement,SELECT_ALL_TYPE_STATE,TypeState::valueOf);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


class HandlerWorkerWithEnumsMySQL{

    protected static <E extends Enum & IEnumId> void setId(java.sql.Statement statement, final String SCRIPT, Function<String,E> valueOf) throws SQLException {
        try(java.sql.ResultSet resultSet = statement.executeQuery(SCRIPT)){
            E enumEntity = null;
            while (resultSet.next()){
                enumEntity = valueOf.apply(resultSet.getString("name"));

                enumEntity.setId(resultSet.getInt("id"));
            }
        }
    }
}