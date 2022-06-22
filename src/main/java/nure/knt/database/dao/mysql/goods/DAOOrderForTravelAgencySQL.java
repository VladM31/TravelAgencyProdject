package nure.knt.database.dao.mysql.goods;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.tools.MySQLCore;
import nure.knt.database.idao.goods.IDAOOrderFromTourAdTravelAgency;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.goods.OrderFromTourAdForTravelAgency;
import nure.knt.entity.goods.TourAd;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class DAOOrderForTravelAgencySQL extends MySQLCore implements IDAOOrderFromTourAdTravelAgency<OrderFromTourAdForTravelAgency> {
    @Override
    public boolean updateConditionCommodity(Long id, ConditionCommodity conditionCommodity) {
        return false;
    }

    private static final String SELECT = "select \n" +
            "order_tour.id,  " +
            "user.name, " +
            "customer.male, " +
            "order_tour.date_start, " +
            "user.number,\n" +
            "user.email,\n" +
            "country.name AS country,\n" +
            "order_tour.date_end,\n" +
            "order_tour.cost,\n" +
            "order_tour.number_of_people,\n" +
            "order_tour.city,\n" +
            "order_tour.date_registration,\n" +
            "condition_commodity.name AS conditionCommodity\n" +
            "from order_tour\n" +
            "left join customer on order_tour.customer_id = customer.id\n" +
            "left join user on customer.user_id =user.id\n" +
            "left join country on user.country_id =country.id\n" +
            "left join condition_commodity on order_tour.condition_commodity_id = condition_commodity.id;";

    private List<OrderFromTourAdForTravelAgency> wrapperForUseSelectList(String part,Object ...array){
        return HandlerSqlDAO.useSelectScript(super.conn,
                HandlerSqlDAO.concatScriptToEnd(SELECT,part,HandlerSqlDAO.SORT_TO_DATE_REGISTRATION),
                HandlerDAOOrderForTravelAgencySQL::resultSetToOrderFromTourAdForTravelAgency,array);
    }

    @Override
    public List<OrderFromTourAdForTravelAgency> findAllById(Long userOrTuodAdId) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForTravelAgency> findByCostBetween(Long userOrTuodAdId, int startCost, int endCost) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForTravelAgency> findByNumberOfPeopleBetween(Long userOrTuodAdId, int startNumberOfPeople, int endNumberOfPeople) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForTravelAgency> findByCityContaining(Long userOrTuodAdId, String city) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForTravelAgency> findByCountryContaining(Long userOrTuodAdId, String country) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForTravelAgency> findByDateRegistrationBetween(Long userOrTuodAdId, LocalDateTime startDateRegistration, LocalDateTime endDateRegistration) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForTravelAgency> findByStartDateOrderAfter(Long userOrTuodAdId, LocalDate startDateOrder) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForTravelAgency> findByEndDateOrderBefore(Long userOrTuodAdId, LocalDate endDateOrder) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForTravelAgency> findByStartDateOrderAfterAndEndDateOrderBefore(Long userOrTuodAdId, LocalDate startDateOrder, LocalDate endDateOrder) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForTravelAgency> findByConditionCommodities(Long userOrTuodAdId, Set<ConditionCommodity> conditionCommodities) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForTravelAgency> findByFirstnameContaining(Long userOrTuodAdId, String firstname) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForTravelAgency> findByLastnameContaining(Long userOrTuodAdId, String lastname) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForTravelAgency> findByEmailContaining(Long userOrTuodAdId, String email) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForTravelAgency> findByNumberContaining(Long userOrTuodAdId, String number) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForTravelAgency> findByMaleIs(Long userOrTuodAdId, Boolean male) {
        return null;
    }
}


class HandlerDAOOrderForTravelAgencySQL{

    static OrderFromTourAdForTravelAgency resultSetToOrderFromTourAdForTravelAgency(java.sql.ResultSet resultSet){
        OrderFromTourAdForTravelAgency order = new OrderFromTourAdForTravelAgency();

        try{
            order.setId(resultSet.getLong("id"));
            order.setCountry(resultSet.getString("country"));
            order.setCity(resultSet.getString("city"));
            order.setCost(resultSet.getInt("cost"));

            order.setNumberOfPeople(resultSet.getInt("number_of_people"));
            order.setDateStart(resultSet.getDate("date_end").toLocalDate());
            order.setDateEnd(resultSet.getDate("date_start").toLocalDate());
            order.setDateRegistration(resultSet.getTimestamp("date_registration").toLocalDateTime());

            order.setConditionCommodity(ConditionCommodity.valueOf(resultSet.getString("conditionCommodity")));
            order.setEmailCustomer(resultSet.getString("email"));
            order.setNumberCustomer(resultSet.getString("number"));
            order.setMale(resultSet.getBoolean("male"));

            String name = resultSet.getString("name");

            order.setFirstnameCustomer(name.substring(0,name.indexOf("/")));
            order.setLastnameCustomer(name.substring(name.indexOf("/")+1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }
}
