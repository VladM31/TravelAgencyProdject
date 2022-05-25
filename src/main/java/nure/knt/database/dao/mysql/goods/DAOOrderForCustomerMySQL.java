package nure.knt.database.dao.mysql.goods;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.tools.MySQLCore;
import nure.knt.database.idao.goods.IDAOOrderFromTourAdCustomer;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.goods.OrderFromTourAdForCustomer;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component("DAOOrderForCustomer")
public class DAOOrderForCustomerMySQL extends MySQLCore implements IDAOOrderFromTourAdCustomer<OrderFromTourAdForCustomer> {

    private static final String SELECT_ALL = "select \n" +
            "user.name as 'travel agency name',\n" +
            "country.name as country,\n" +
            "service.city as 'service city',\n" +
            "place,\n" +
            "cost,\n" +
            "number_of_people,\n" +
            "order_tour.id as order_id,\n" +
            "condition_commodity.name as 'condition commodity',\n" +
            "order_tour.date_start,\n" +
            "order_tour.date_end,\n" +
            "order_tour.date_registration,\n" +
            "service.id as service_id\n" +
            "from order_tour \n" +
            "left join service on order_tour.service_id = service.id \n" +
            "left join travel_agency on service.travel_agency_id = travel_agency.id \n" +
            "left join user on travel_agency.user_id = user.id \n" +
            "left join country on service.country_id = country.id \n" +
            "left join condition_commodity on order_tour.condition_commodity_id = condition_commodity.id;";

    private static final String SORT_TO_ORDER_TOUR_DATE_REGISTRATION = " ORDER BY order_tour.date_registration ASC;";
    @Override
    public List<OrderFromTourAdForCustomer> findAll() {
        return HandlerSqlDAO.useSelectScript(super.conn,
                HandlerSqlDAO.concatScriptToEnd(SELECT_ALL, SORT_TO_ORDER_TOUR_DATE_REGISTRATION),
                HandlerOrderCustomer::scriptToOrderFromTourAdForCustomer);
    }

    @Override
    public List<OrderFromTourAdForCustomer> findAllById(Iterable<Long> ids) {
        return null;
    }

    @Override
    public OrderFromTourAdForCustomer findOneById(Long id) {
        return null;
    }

    @Override
    public boolean saveAll(Iterable<OrderFromTourAdForCustomer> entities) {
        return false;
    }

    @Override
    public boolean save(OrderFromTourAdForCustomer entity) {
        return false;
    }

    @Override
    public boolean updateConditionCommodity(Long id, ConditionCommodity conditionCommodity) {
        return false;
    }

    @Override
    public List<OrderFromTourAdForCustomer> findByCostBetween(Long userOrTuodAdId, int startCost, int endCost) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForCustomer> findByNumberOfPeopleBetween(Long userOrTuodAdId, int startNumberOfPeople, int endNumberOfPeople) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForCustomer> findByCityContaining(Long userOrTuodAdId, String city) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForCustomer> findByCountryContaining(Long userOrTuodAdId, String country) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForCustomer> findByDateRegistrationBetween(Long userOrTuodAdId, LocalDate startDateRegistration, LocalDate endDateRegistration) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForCustomer> findByStartDateOrderAfter(Long userOrTuodAdId, LocalDate startDateOrder) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForCustomer> findByEndDateOrderBefore(Long userOrTuodAdId, LocalDate endDateOrder) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForCustomer> findByStartDateOrderAfterAndEndDateOrderBefore(Long userOrTuodAdId, LocalDate startDateOrder, LocalDate endDateOrder) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForCustomer> findByConditionCommodities(Long userOrTuodAdId, Set<ConditionCommodity> conditionCommodities) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForCustomer> findByNameTravelAgencyContaining(Long userOrTuodAdId, String country) {
        return null;
    }

    @Override
    public List<OrderFromTourAdForCustomer> findByRestingPlaceContaining(Long userOrTuodAdId, String restingPlace) {
        return null;
    }
}

class HandlerOrderCustomer{

    public static OrderFromTourAdForCustomer scriptToOrderFromTourAdForCustomer(java.sql.ResultSet resultSet){
        OrderFromTourAdForCustomer order = new OrderFromTourAdForCustomer();

        try {
            order.setNameTravelAgency(resultSet.getString("travel agency name"));
            order.setCountry(resultSet.getString("country"));
            order.setCity(resultSet.getString("service city"));
            order.setRestingPlace(resultSet.getString("place"));

            order.setCost(resultSet.getInt("cost"));
            order.setNumberOfPeople(resultSet.getInt("number_of_people"));
            order.setId(resultSet.getLong("order_id"));
            order.setConditionCommodity(ConditionCommodity.valueOf(resultSet.getString("condition commodity")));

            order.setDateStart(resultSet.getDate("date_start").toLocalDate());
            order.setDateEnd(resultSet.getDate("date_end").toLocalDate());
            order.setDateRegistration(resultSet.getTimestamp("date_registration").toLocalDateTime());
            order.setIdTourAd(resultSet.getLong("service_id"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

}
