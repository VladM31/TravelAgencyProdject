package nure.knt.database.dao.mysql.goods;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.tools.MySQLCore;
import nure.knt.database.idao.goods.IDAOCourierTask;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.enums.Role;
import nure.knt.entity.goods.CourierTask;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component("DAO_Courier_Task_MySQL")
public class DAOCourierTaskMySQL extends MySQLCore implements IDAOCourierTask<CourierTask> {

    private static final String SELECT_ALL_TASK = "select courier_task.id AS courier_task_id," +
            "courier_task.number_of_flyers," +
            "courier_task.city," +
            "admin.email as admin_email," +
            "admin.name as admin_name," +
            "admin.id as admin_id," +
            "courier.id as courier_id," +
            "active_courier.email as courier_email," +
            "active_courier.name as courier_name," +
            "courier_task.describe_task," +
            "courier_task.date_registration," +
            "condition_commodity.name as condition_name from courier_task " +
            "left join user AS admin on courier_task.user_id = admin.id " +
            "left join courier on courier_task.courier_id = courier.id " +
            "left join user AS active_courier on courier.user_id = active_courier.id " +
            "left join condition_commodity on courier_task.condition_commodity_id = condition_commodity.id;";

    private static final String INSERT_COURIER_TASK = "INSERT INTO courier_task(number_of_flyers, describe_task," +
            "date_registration, city, user_id, courier_id, condition_commodity_id)" +
            "VALUE (?,?,?,?,?,?,?);";

    @Override
    public boolean saveAll(Iterable<CourierTask> entities) {
        try (PreparedStatement statement = super.conn.getSqlPreparedStatement(INSERT_COURIER_TASK)) {
            for(CourierTask task : entities) {
                HandlerCourierTask.courierToMySqlScript(statement, task);
                statement.addBatch();
            }
            return HandlerSqlDAO.arrayHasOnlyOne(statement.executeBatch());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean save(CourierTask entity) {
        return this.saveAll(List.of(entity));
    }

    public static final String CONDITION_COMMODITY_UPDATE = "update courier_task set condition_commodity_id = ? where id = ?;";
    @Override
    public boolean updateConditionCommodity(Long id, ConditionCommodity conditionCommodity) {
        try (PreparedStatement statement = super.conn.getSqlPreparedStatement(CONDITION_COMMODITY_UPDATE)) {
            statement.setInt(1,conditionCommodity.getId());
            statement.setLong(2, id);
            return statement.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static final String ADMIN_ROLE_AND_ID_USER_AND_CITY_CONTAINING = " WHERE admin.id = ? AND courier_task.city LIKE ?";
    private static final String COURIER_ROLE_AND_ID_USER_AND_CITY_CONTAINING = " WHERE courier.id = ? AND courier_task.city LIKE ?";


    private static final String ADMIN_ROLE_AND_ID_USER = " WHERE admin.id = ?";
    private static final String COURIER_ROLE_AND_ID_USER = " WHERE courier.id = ?";
    @Override
    public List<CourierTask> findByRoleAndIdUser(Role role, Long id) {
        if(role.equals(Role.COURIER)) {
            return wrapperForUseSelectList(COURIER_ROLE_AND_ID_USER, id);
        }
        return wrapperForUseSelectList(ADMIN_ROLE_AND_ID_USER, id);
    }


    private static final String ADMIN_ROLE_AND_TASK_ID = " WHERE admin.id = ? AND courier_task.id = ? ";
    private static final String COURIER_ROLE_AND_TASK_ID = " WHERE courier.id = ? AND courier_task.id = ? ";
    @Override
    public CourierTask findByRoleAndIdUserAndTaskId(Role role, Long id, Long taskId) {
        if(role.equals(Role.COURIER)) {
            return HandlerSqlDAO.useSelectScriptAndGetOneObject(super.conn,
                    HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_TASK,COURIER_ROLE_AND_TASK_ID,HandlerSqlDAO.SORT_TO_DATE_REGISTRATION),
                    HandlerCourierTask::resultSetToCourierTask, id, taskId);
        }
        return HandlerSqlDAO.useSelectScriptAndGetOneObject(super.conn,
                HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_TASK,ADMIN_ROLE_AND_TASK_ID,HandlerSqlDAO.SORT_TO_DATE_REGISTRATION),
                HandlerCourierTask::resultSetToCourierTask, id, taskId);
    }

    @Override
    public List<CourierTask> findByRoleAndIdUserAndCityContaining(Role role, Long id, String city) {
        if(role.equals(Role.COURIER)) {
            return wrapperForUseSelectList(COURIER_ROLE_AND_ID_USER_AND_CITY_CONTAINING, id, HandlerSqlDAO.containingString(city));
        }
        return wrapperForUseSelectList(ADMIN_ROLE_AND_ID_USER_AND_CITY_CONTAINING, id, HandlerSqlDAO.containingString(city));
    }

    private static final String ADMIN_ROLE_AND_ID_USER_AND_EMAIL_CONTAINING = " WHERE admin.id = ? AND active_courier.email LIKE ?";
    private static final String COURIER_ROLE_AND_ID_USER_AND_EMAIL_CONTAINING = " WHERE courier.id = ? AND admin.email LIKE ?";

    @Override
    public List<CourierTask> findByRoleAndIdUserAndEmailContaining(Role role, Long id, String email) {
        if(role.equals(Role.COURIER)) {
            return wrapperForUseSelectList(COURIER_ROLE_AND_ID_USER_AND_EMAIL_CONTAINING, id, HandlerSqlDAO.containingString(email));
        }
        return wrapperForUseSelectList(ADMIN_ROLE_AND_ID_USER_AND_EMAIL_CONTAINING, id, HandlerSqlDAO.containingString(email));
    }

    private static final String ADMIN_ROLE_AND_ID_USER_AND_ADMIN_NAME_CONTAINING = " WHERE admin.id = ? AND active_courier.name LIKE ?";
    private static final String COURIER_ROLE_AND_ID_USER_AND_ADMIN_NAME_CONTAINING = " WHERE courier.id = ? AND active_courier.name LIKE ?";

    @Override
    public List<CourierTask> findByRoleAndIdUserAndNameCourierContaining(Role role, Long id, String name) {
        if(role.equals(Role.ADMINISTRATOR)) {
            return wrapperForUseSelectList(ADMIN_ROLE_AND_ID_USER_AND_ADMIN_NAME_CONTAINING, id, HandlerSqlDAO.containingString(name));
        }
        return wrapperForUseSelectList(COURIER_ROLE_AND_ID_USER_AND_ADMIN_NAME_CONTAINING, id, HandlerSqlDAO.containingString(name));
    }

    private static final String ADMIN_ROLE_AND_ID_USER_AND_COURIER_NAME_CONTAINING = " WHERE admin.id = ? AND admin.name LIKE ?";
    private static final String COURIER_ROLE_AND_ID_USER_AND_NAME_CONTAINING = " WHERE courier.id = ? AND admin.name LIKE ?";

    @Override
    public List<CourierTask> findByRoleAndIdUserAndNameAdminContaining(Role role, Long id, String name) {
        if(role.equals(Role.COURIER)) {
            return wrapperForUseSelectList(COURIER_ROLE_AND_ID_USER_AND_NAME_CONTAINING, id, HandlerSqlDAO.containingString(name));
        }
        return wrapperForUseSelectList(ADMIN_ROLE_AND_ID_USER_AND_COURIER_NAME_CONTAINING, id, HandlerSqlDAO.containingString(name));
    }

    private static final String ADMIN_ROLE_AND_ID_USER_AND_DESCRIBE_CONTAINING = " WHERE admin.id = ? AND describe_task LIKE ?";
    private static final String COURIER_ROLE_AND_ID_USER_AND_DESCRIBE_CONTAINING = " WHERE courier.id = ? AND describe_task LIKE ?";

    @Override
    public List<CourierTask> findByRoleAndIdUserAndDescribeContaining(Role role, Long id, String describe) {
        if(role.equals(Role.COURIER)) {
            return wrapperForUseSelectList(COURIER_ROLE_AND_ID_USER_AND_DESCRIBE_CONTAINING, id, HandlerSqlDAO.containingString(describe));
        }
        return wrapperForUseSelectList(ADMIN_ROLE_AND_ID_USER_AND_DESCRIBE_CONTAINING, id, HandlerSqlDAO.containingString(describe));
    }

    private static final String ADMIN_ROLE_AND_ID_USER_AND_DATE_REGISTRATION = " WHERE admin.id = ? AND courier_task.date_registration BETWEEN ? AND ? ";
    private static final String COURIER_ROLE_AND_ID_USER_AND_DATE_REGISTRATION = " WHERE courier.id = ? AND courier_task.date_registration BETWEEN ? AND ? ";

    @Override
    public List<CourierTask> findByRoleAndIdUserAndDateRegistrationBetween(Role role, Long id, LocalDateTime startDate, LocalDateTime endDate) {
        if(role.equals(Role.COURIER)) {
            return wrapperForUseSelectList(COURIER_ROLE_AND_ID_USER_AND_DATE_REGISTRATION, id, startDate, endDate);
        }
        return wrapperForUseSelectList(ADMIN_ROLE_AND_ID_USER_AND_DATE_REGISTRATION, id, startDate, endDate);
    }

    //+
    private static final String ADMIN_ROLE_AND_ID_USER_AND_NUMBER_OF_FLYERS = " WHERE admin.id = ? AND number_of_flyers BETWEEN ? AND ? ";
    private static final String COURIER_ROLE_AND_ID_USER_AND_NUMBER_OF_FLYERS = " WHERE courier.id = ? AND number_of_flyers BETWEEN ? AND ? ";

    @Override
    public List<CourierTask> findByRoleAndIdUserAndNumberOfFlyersBetween(Role role, Long id, int start, int end) {
        if(role.equals(Role.COURIER)) {
            return wrapperForUseSelectList(COURIER_ROLE_AND_ID_USER_AND_NUMBER_OF_FLYERS, id, start, end);
        }
        return wrapperForUseSelectList(ADMIN_ROLE_AND_ID_USER_AND_NUMBER_OF_FLYERS, id, start, end);
    }

    //+
    private static final String ADMIN_ROLE_AND_ID_USER_AND_CONDITION_COMMODITY = " WHERE admin.id = ? AND condition_commodity.name IN ( "+HandlerSqlDAO.REPLACE_SYMBOL+" )";
    private static final String COURIER_ROLE_AND_ID_USER_AND_CONDITION_COMMODITY = " WHERE courier.id = ? AND condition_commodity.name IN ( "+HandlerSqlDAO.REPLACE_SYMBOL+" )";

    @Override
    public List<CourierTask> findByRoleAndIdUserAndConditionCommodityIn(Role role, Long id, Set<ConditionCommodity> conditionCommodities) {
        if(role.equals(Role.COURIER)) {
            return wrapperForUseSelectList(HandlerSqlDAO.setInInsideScript(COURIER_ROLE_AND_ID_USER_AND_CONDITION_COMMODITY, conditionCommodities), id, conditionCommodities);
        }
        return wrapperForUseSelectList(HandlerSqlDAO.setInInsideScript(ADMIN_ROLE_AND_ID_USER_AND_CONDITION_COMMODITY, conditionCommodities), id, conditionCommodities);
    }

    private List<CourierTask> wrapperForUseSelectList(String part, @NonNull Object ...arrayField){
        return HandlerSqlDAO.useSelectScript(conn, HandlerSqlDAO.concatScriptToEnd(SELECT_ALL_TASK,part," ORDER BY courier_task.date_registration ASC;"),
                HandlerCourierTask::resultSetToCourierTask,arrayField);
    }
}

class HandlerCourierTask {

    private static final int NUMBER_OF_FLYERS_POSITION_FOR_INSERT = 1;
    private static final int DESCRIBE_TASK_POSITION_FOR_INSERT = 2;
    private static final int DATE_REGISTRATION_POSITION_FOR_INSERT = 3;
    private static final int CITY_POSITION_FOR_INSERT = 4;
    private static final int ADMIN_ID_POSITION_FOR_INSERT = 5;
    private static final int COURIER_ID_POSITION_FOR_INSERT = 6;
    private static final int CONDITION_COMMODITY_ID_POSITION_FOR_INSERT = 7;

    static void courierToMySqlScript(PreparedStatement preStat, CourierTask courierTask){
        try {
            preStat.setInt(NUMBER_OF_FLYERS_POSITION_FOR_INSERT,courierTask.getNumberOfFlyers());
            preStat.setString(DESCRIBE_TASK_POSITION_FOR_INSERT,courierTask.getDescribeTask());
            preStat.setTimestamp(DATE_REGISTRATION_POSITION_FOR_INSERT, Timestamp.valueOf(courierTask.getDateRegistration()));
            preStat.setString(CITY_POSITION_FOR_INSERT, courierTask.getCity());
            preStat.setLong(ADMIN_ID_POSITION_FOR_INSERT,courierTask.getIdAdmin());
            preStat.setLong(COURIER_ID_POSITION_FOR_INSERT, courierTask.getIdCourier());
            preStat.setInt(CONDITION_COMMODITY_ID_POSITION_FOR_INSERT, courierTask.getConditionCommodity().getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static CourierTask resultSetToCourierTask(ResultSet resultSet){
        CourierTask courierTask = new CourierTask();
        try {
            courierTask.setId(resultSet.getLong("courier_task_id"));
            courierTask.setNumberOfFlyers(resultSet.getInt("number_of_flyers"));
            courierTask.setCity(resultSet.getString("city"));
            courierTask.setEmailAdmin(resultSet.getString("admin_email"));
            courierTask.setIdAdmin(resultSet.getLong("admin_id"));
            courierTask.setNameAdmin(resultSet.getString("admin_name"));
            courierTask.setIdCourier(resultSet.getLong("courier_id"));
            courierTask.setEmailCourier(resultSet.getString("courier_email"));
            courierTask.setNameCourier(resultSet.getString("courier_name"));
            courierTask.setDescribeTask(resultSet.getString("describe_task"));
            courierTask.setDateRegistration(resultSet.getTimestamp("date_registration").toLocalDateTime());
            courierTask.setConditionCommodity(ConditionCommodity.valueOf(resultSet.getString("condition_name")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courierTask;
    }
}
