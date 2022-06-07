package nure.knt.database.dao.mysql.goods;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.tools.MySQLCore;
import nure.knt.database.idao.goods.IDAOOrderFromTourAdCustomer;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.goods.OrderFromTourAdForCustomer;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository("DAO_MySQL_Order_For_Customer")
public class DAOOrderForCustomerMySQL extends MySQLCore implements IDAOOrderFromTourAdCustomer<OrderFromTourAdForCustomer> {

    private static final String SELECT_ALL = "select \n" +
            "user.name as 'travel agency name',\n" +
            "country.name as country,\n" +
            "tour_ad.city as 'tour ad city',\n" +
            "place,\n" +
            "cost,\n" +
            "number_of_people,\n" +
            "order_tour.id as order_id,\n" +
            "condition_commodity.name as 'condition commodity',\n" +
            "order_tour.date_start,\n" +
            "order_tour.date_end,\n" +
            "order_tour.date_registration,\n" +
            "tour_ad.id as tour_ad_id," +
            "customer_id\n" +
            "from order_tour \n" +
            "left join tour_ad on order_tour.tour_ad_id = tour_ad.id \n" +
            "left join travel_agency on tour_ad.travel_agency_id = travel_agency.id \n" +
            "left join user on travel_agency.user_id = user.id \n" +
            "left join country on tour_ad.country_id = country.id \n" +
            "left join condition_commodity on order_tour.condition_commodity_id = condition_commodity.id;";

    private static final String SORT_TO_ORDER_TOUR_DATE_REGISTRATION = " ORDER BY order_tour.date_registration DESC;";

    private static final String insert_order = "insert into order_tour(number_of_people,cost,date_start,date_end," +
            "date_registration,city,customer_id,condition_commodity_id,tour_ad_id)\n" +
            " VALUE(?,?,?,?,?,?,?,(SELECT id FROM condition_commodity WHERE name = ?),?);";

    @Override
    public boolean saveAll(Iterable<OrderFromTourAdForCustomer> entities) {
        try(PreparedStatement preparedStatement = super.conn.getSqlPreparedStatement(insert_order)) {

            for (var entity : entities){
                HandlerOrderCustomer.courierToMySqlScript(preparedStatement,entity);
                preparedStatement.addBatch();
            }

            return HandlerSqlDAO.arrayHasOnlyOne(preparedStatement.executeBatch());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean save(OrderFromTourAdForCustomer entity) {
        return this.saveAll(List.of(entity));
    }

    private static final String UPDATE_CONDITION_BY_ID = "UPDATE order_tour SET condition_commodity_id = ? WHERE id = ? ;";
    private static final int NAME_CONDITION_POSITION_FOR_UPDATE = 1;
    private static final int ORDER_ID_POSITION_FOR_UPDATE = 2;

    @Override
    public boolean updateConditionCommodity(Long id, ConditionCommodity conditionCommodity) {
        try(PreparedStatement preparedStatement = super.conn.getSqlPreparedStatement(UPDATE_CONDITION_BY_ID)){

            preparedStatement.setInt(NAME_CONDITION_POSITION_FOR_UPDATE,conditionCommodity.getId());
            preparedStatement.setLong(ORDER_ID_POSITION_FOR_UPDATE,id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static final String WHERE_CUSTOMER_ID_IS = " WHERE customer_id = ? ";
    private static final String AND = " AND ";
    private static final String COST_BETWEEN = " order_tour.cost BETWEEN ? AND ? ";

    @Override
    public List<OrderFromTourAdForCustomer> findAllById(Long customerId) {
        return HandlerSqlDAO.useSelectScript(super.conn,
                HandlerSqlDAO.concatScriptToEnd(SELECT_ALL,WHERE_CUSTOMER_ID_IS,SORT_TO_ORDER_TOUR_DATE_REGISTRATION),
                HandlerOrderCustomer::scriptToOrderFromTourAdForCustomer,customerId);
    }

    @Override
    public List<OrderFromTourAdForCustomer> findByCostBetween(Long customerId, int startCost, int endCost) {
        return HandlerSqlDAO.useSelectScript(super.conn,
                HandlerSqlDAO.concatScriptToEnd(SELECT_ALL,WHERE_CUSTOMER_ID_IS,AND,COST_BETWEEN,SORT_TO_ORDER_TOUR_DATE_REGISTRATION),
                HandlerOrderCustomer::scriptToOrderFromTourAdForCustomer,
                customerId,startCost,endCost);
    }

    private static final String NUMBER_OF_PEOPLE_BETWEEN = " order_tour.number_of_people BETWEEN ? AND ? ";

    @Override
    public List<OrderFromTourAdForCustomer> findByNumberOfPeopleBetween(Long customerId, int startNumberOfPeople, int endNumberOfPeople) {
        return HandlerSqlDAO.useSelectScript(super.conn,
                HandlerSqlDAO.concatScriptToEnd(SELECT_ALL,WHERE_CUSTOMER_ID_IS,AND,NUMBER_OF_PEOPLE_BETWEEN,SORT_TO_ORDER_TOUR_DATE_REGISTRATION),
                HandlerOrderCustomer::scriptToOrderFromTourAdForCustomer,
                customerId,startNumberOfPeople,endNumberOfPeople);
    }

    private static final String NAME_CITY_CONTAINING = " tour_ad.city LIKE ? ";
    @Override
    public List<OrderFromTourAdForCustomer> findByCityContaining(Long customerId, String city) {
        return HandlerSqlDAO.useSelectScript(super.conn,
                HandlerSqlDAO.concatScriptToEnd(SELECT_ALL,WHERE_CUSTOMER_ID_IS,AND,NAME_CITY_CONTAINING,SORT_TO_ORDER_TOUR_DATE_REGISTRATION),
                HandlerOrderCustomer::scriptToOrderFromTourAdForCustomer,
                customerId,HandlerSqlDAO.containingString(city));
    }

    @Override
    public List<OrderFromTourAdForCustomer> findByCountryContaining(Long customerId, String country) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForCustomer> findByDateRegistrationBetween(Long customerId, LocalDate startDateRegistration, LocalDate endDateRegistration) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForCustomer> findByStartDateOrderAfter(Long customerId, LocalDate startDateOrder) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForCustomer> findByEndDateOrderBefore(Long customerId, LocalDate endDateOrder) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForCustomer> findByStartDateOrderAfterAndEndDateOrderBefore(Long customerId, LocalDate startDateOrder, LocalDate endDateOrder) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForCustomer> findByConditionCommodities(Long customerId, Set<ConditionCommodity> conditionCommodities) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForCustomer> findByNameTravelAgencyContaining(Long customerId, String country) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForCustomer> findByRestingPlaceContaining(Long customerId, String restingPlace) {
        return null;
    }
}

class HandlerOrderCustomer{

    public static OrderFromTourAdForCustomer scriptToOrderFromTourAdForCustomer(java.sql.ResultSet resultSet){
        OrderFromTourAdForCustomer order = new OrderFromTourAdForCustomer();

        try {
            order.setNameTravelAgency(resultSet.getString("travel agency name"));
            order.setCountry(resultSet.getString("country"));
            order.setCity(resultSet.getString("tour ad city"));
            order.setRestingPlace(resultSet.getString("place"));

            order.setCost(resultSet.getInt("cost"));
            order.setNumberOfPeople(resultSet.getInt("number_of_people"));
            order.setId(resultSet.getLong("order_id"));
            order.setConditionCommodity(ConditionCommodity.valueOf(resultSet.getString("condition commodity")));

            order.setDateStart(resultSet.getDate("date_start").toLocalDate());
            order.setDateEnd(resultSet.getDate("date_end").toLocalDate());
            order.setDateRegistration(resultSet.getTimestamp("date_registration").toLocalDateTime());
            order.setIdTourAd(resultSet.getLong("tour_ad_id"));

            order.setIdCustomer(resultSet.getLong("customer_id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }



    private static final int NUMBER_OF_PEOPLE_POSITION_FOR_INSERT = 1;
    private static final int COST_POSITION_FOR_INSERT = 2;
    private static final int DATE_START_POSITION_FOR_INSERT = 3;
    private static final int DATE_END_POSITION_FOR_INSERT = 4;
    private static final int DATE_REGISTRATION_POSITION_FOR_INSERT = 5;
    private static final int CITY_POSITION_FOR_INSERT = 6;
    private static final int CUSTOMER_ID_POSITION_FOR_INSERT = 7;
    private static final int CONDITION_COMMODITY_ID_POSITION_FOR_INSERT = 8;
    private static final int TOUR_AD_ID_POSITION_FOR_INSERT = 9;

    static void courierToMySqlScript(PreparedStatement preStat, OrderFromTourAdForCustomer order) throws SQLException {

        preStat.setInt(NUMBER_OF_PEOPLE_POSITION_FOR_INSERT, order.getNumberOfPeople());
        preStat.setInt(COST_POSITION_FOR_INSERT, order.getCost());

        preStat.setDate(DATE_START_POSITION_FOR_INSERT, Date.valueOf(order.getDateStart()));
        preStat.setDate(DATE_END_POSITION_FOR_INSERT, Date.valueOf(order.getDateEnd()));
        preStat.setTimestamp(DATE_REGISTRATION_POSITION_FOR_INSERT, Timestamp.valueOf(order.getDateRegistration()));

        preStat.setString(CITY_POSITION_FOR_INSERT, order.getCity());
        preStat.setLong(CUSTOMER_ID_POSITION_FOR_INSERT, order.getIdCustomer());
        preStat.setString(CONDITION_COMMODITY_ID_POSITION_FOR_INSERT, order.getConditionCommodity().toString());
        preStat.setLong(TOUR_AD_ID_POSITION_FOR_INSERT, order.getIdTourAd());

    }


}
