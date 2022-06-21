package nure.knt.database.dao.mysql.goods;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.tools.MySQLCore;
import nure.knt.database.idao.goods.IDAOOrderFromTourAdTravelAgency;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.goods.OrderFromTourAdForTravelAgency;
import nure.knt.entity.goods.TourAd;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public class DAOOrderForTravelAgencySQL extends MySQLCore implements IDAOOrderFromTourAdTravelAgency<OrderFromTourAdForTravelAgency> {
    @Override
    public boolean updateConditionCommodity(Long id, ConditionCommodity conditionCommodity) {
        return false;
    }

    private static final String SELECT = "select \n" +
            "order_tour.id, \n" +
            "user.name,\n" +
            "customer.male,\n" +
            "order_tour.date_start,\n" +
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
            "left join condition_commodity on order_tour.condition_commodity_id = condition_commodity.id ;";

    private List<OrderFromTourAdForTravelAgency> wrapperForUseSelectList(String part,Object ...array){
        return HandlerSqlDAO.useSelectScript(super.conn,
                HandlerSqlDAO.concatScriptToEnd(SELECT,part,HandlerSqlDAO.SORT_TO_DATE_REGISTRATION),
                HandlerDAOOrderForTravelAgencySQL::resultSetToOrderFromTourAdForTravelAgency,array);
    }

    private static final String WHERE_TOUR_AD_ID_IS = " WHERE order_tour.tour_ad_id = ? ";

    @Override
    public List<OrderFromTourAdForTravelAgency> findAllById(Long userOrTuodAdId) {
        return this.wrapperForUseSelectList(WHERE_TOUR_AD_ID_IS,userOrTuodAdId);
    }

    private static final String WHERE_TOUR_AD_ID_IS_AND_COST_BETWEEN = WHERE_TOUR_AD_ID_IS + " AND order_tour.cost BETWEEN ? AND ? ";

    @Override
    public List<OrderFromTourAdForTravelAgency> findByCostBetween(Long userOrTuodAdId, int startCost, int endCost) {
        return this.wrapperForUseSelectList(WHERE_TOUR_AD_ID_IS_AND_COST_BETWEEN,userOrTuodAdId,startCost,endCost);
    }

    private static final String WHERE_TOUR_AD_ID_IS_AND_NUMBER_OF_PEOPLE_BETWEEN = WHERE_TOUR_AD_ID_IS + " AND order_tour.number_of_people BETWEEN ? AND ? ";

    @Override
    public List<OrderFromTourAdForTravelAgency> findByNumberOfPeopleBetween(Long userOrTuodAdId, int startNumberOfPeople, int endNumberOfPeople) {
        return this.wrapperForUseSelectList(WHERE_TOUR_AD_ID_IS_AND_NUMBER_OF_PEOPLE_BETWEEN,userOrTuodAdId,startNumberOfPeople,endNumberOfPeople);
    }

    private static final String WHERE_TOUR_AD_ID_IS_AND_CITY_CONTAINING = WHERE_TOUR_AD_ID_IS + " AND order_tour.city LIKE ? ";

    @Override
    public List<OrderFromTourAdForTravelAgency> findByCityContaining(Long userOrTuodAdId, String city) {
        return this.wrapperForUseSelectList(WHERE_TOUR_AD_ID_IS_AND_CITY_CONTAINING,userOrTuodAdId,HandlerSqlDAO.containingString(city));
    }

    private static final String WHERE_TOUR_AD_ID_IS_AND_COUNTRY_CONTAINING = WHERE_TOUR_AD_ID_IS + " AND country.name LIKE ? ";

    @Override
    public List<OrderFromTourAdForTravelAgency> findByCountryContaining(Long userOrTuodAdId, String country) {
        return this.wrapperForUseSelectList(WHERE_TOUR_AD_ID_IS_AND_COUNTRY_CONTAINING,userOrTuodAdId,HandlerSqlDAO.containingString(country));
    }

    private static final String WHERE_TOUR_AD_ID_IS_AND_DATE_REGISTRATION_BETWEEN = WHERE_TOUR_AD_ID_IS + " AND order_tour.date_registration BETWEEN ? AND ? ";

    @Override
    public List<OrderFromTourAdForTravelAgency> findByDateRegistrationBetween(Long userOrTuodAdId, LocalDateTime startDateRegistration, LocalDateTime endDateRegistration) {
        return this.wrapperForUseSelectList(WHERE_TOUR_AD_ID_IS_AND_DATE_REGISTRATION_BETWEEN,userOrTuodAdId,startDateRegistration,endDateRegistration);
    }

    private static final String WHERE_TOUR_AD_ID_IS_AND_START_DATE_ORDER_AFTER = WHERE_TOUR_AD_ID_IS + " AND order_tour.date_start >= ? ";

    @Override
    public List<OrderFromTourAdForTravelAgency> findByStartDateOrderAfter(Long userOrTuodAdId, LocalDate startDateOrder) {
        return this.wrapperForUseSelectList(WHERE_TOUR_AD_ID_IS_AND_START_DATE_ORDER_AFTER,userOrTuodAdId,startDateOrder);
    }

    private static final String WHERE_TOUR_AD_ID_IS_AND_END_DATE_ORDER_BEFORE = WHERE_TOUR_AD_ID_IS + " AND order_tour.date_end <= ? ";

    @Override
    public List<OrderFromTourAdForTravelAgency> findByEndDateOrderBefore(Long userOrTuodAdId, LocalDate endDateOrder) {
        return this.wrapperForUseSelectList(WHERE_TOUR_AD_ID_IS_AND_END_DATE_ORDER_BEFORE,userOrTuodAdId,endDateOrder);
    }

    private static final String WHERE_TOUR_AD_ID_IS_AND_START_DATE_ORDER_AFTER_AND_END_DATE_ORDER_BEFORE =
            WHERE_TOUR_AD_ID_IS + " AND order_tour.date_start >= ? AND order_tour.date_end <= ? ";

    @Override
    public List<OrderFromTourAdForTravelAgency> findByStartDateOrderAfterAndEndDateOrderBefore(Long userOrTuodAdId, LocalDate startDateOrder, LocalDate endDateOrder) {
        return this.wrapperForUseSelectList(WHERE_TOUR_AD_ID_IS_AND_START_DATE_ORDER_AFTER_AND_END_DATE_ORDER_BEFORE,userOrTuodAdId,startDateOrder,endDateOrder);
    }

    private static final String WHERE_TOUR_AD_ID_IS_AND_STATE_IN = WHERE_TOUR_AD_ID_IS + " AND  condition_commodity.name IN( "+ HandlerSqlDAO.REPLACE_SYMBOL + " )";

    @Override
    public List<OrderFromTourAdForTravelAgency> findByConditionCommodities(Long userOrTuodAdId, Set<ConditionCommodity> conditionCommodities) {
        return this.wrapperForUseSelectList(HandlerSqlDAO.setInInsideScript(WHERE_TOUR_AD_ID_IS_AND_STATE_IN,conditionCommodities),userOrTuodAdId,conditionCommodities);
    }

    private static final String WHERE_TOUR_AD_ID_IS_AND_FIRSTNAME_CONTAINING = WHERE_TOUR_AD_ID_IS + " AND user.name LIKE ? ";

    @Override
    public List<OrderFromTourAdForTravelAgency> findByFirstnameContaining(Long userOrTuodAdId, String firstname) {
        return this.wrapperForUseSelectList(WHERE_TOUR_AD_ID_IS_AND_FIRSTNAME_CONTAINING,userOrTuodAdId,"%".concat(firstname).concat("%/%"));
    }

    private static final String WHERE_TOUR_AD_ID_IS_AND_LASTNAME_CONTAINING = WHERE_TOUR_AD_ID_IS + " AND user.name LIKE ? ";

    @Override
    public List<OrderFromTourAdForTravelAgency> findByLastnameContaining(Long userOrTuodAdId, String lastname) {
        return this.wrapperForUseSelectList(WHERE_TOUR_AD_ID_IS_AND_LASTNAME_CONTAINING,userOrTuodAdId,"%/%".concat(lastname).concat("%"));
    }

    private static final String WHERE_TOUR_AD_ID_IS_AND_EMAIL_CONTAINING = WHERE_TOUR_AD_ID_IS + " AND user.email LIKE ? ";

    @Override
    public List<OrderFromTourAdForTravelAgency> findByEmailContaining(Long userOrTuodAdId, String email) {
        return this.wrapperForUseSelectList(WHERE_TOUR_AD_ID_IS_AND_EMAIL_CONTAINING,userOrTuodAdId,HandlerSqlDAO.containingString(email));
    }

    private static final String WHERE_TOUR_AD_ID_IS_AND_NUMBER_CONTAINING = WHERE_TOUR_AD_ID_IS + " AND user.number LIKE ? ";

    @Override
    public List<OrderFromTourAdForTravelAgency> findByNumberContaining(Long userOrTuodAdId, String number) {
        return this.wrapperForUseSelectList(WHERE_TOUR_AD_ID_IS_AND_NUMBER_CONTAINING,userOrTuodAdId,HandlerSqlDAO.containingString(number));
    }

    private static final String WHERE_TOUR_AD_ID_IS_AND_MALE_IS = WHERE_TOUR_AD_ID_IS + " AND customer.male = ? ";

    @Override
    public List<OrderFromTourAdForTravelAgency> findByMaleIs(Long userOrTuodAdId, Boolean male) {
        return this.wrapperForUseSelectList(WHERE_TOUR_AD_ID_IS_AND_MALE_IS,userOrTuodAdId,male);
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
            order.setDateStart(resultSet.getDate("date_start").toLocalDate());
            order.setDateEnd(resultSet.getDate("date_end").toLocalDate());
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
