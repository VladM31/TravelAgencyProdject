package nure.knt.database.dao.mysql.goods;


import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.entity.HandlerUser;
import nure.knt.database.dao.mysql.tools.MySQLCore;
import nure.knt.database.idao.goods.IDAOTourAd;
import nure.knt.database.idao.goods.ScriptTourAdWhere;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.enums.Role;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.goods.TourAd;
import nure.knt.entity.important.TravelAgency;
import nure.knt.tools.WorkWithCountries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static nure.knt.database.dao.HandlerSqlDAO.ERROR_BOOLEAN_ANSWER;
import static nure.knt.database.dao.HandlerSqlDAO.HAVE_NO_ERROR;

public class DAOTourAdMySQL extends MySQLCore implements IDAOTourAd<TourAd> {
    @Override
    public boolean editing(Long id, TourAd entity) {
        return false;
    }


    private static final String INSERT_TOUR_AD =
            " INSERT INTO tour_ad (place,city,date_start,date_end,date_registration,cost_one_customer,cost_service,discount_size_people,discount_percentage,hidden,travel_agency_id,condition_commodity_id,type_state_id,country_id)" +
                    " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";


    @Override
    public boolean saveAll(Iterable<TourAd> entities) {
        try(PreparedStatement preparedStatement = super.conn.getSqlPreparedStatement(INSERT_TOUR_AD)){
            for (TourAd tourAd: entities) {
                HandlerDAOtoMYSQL.tourAdToMySqlScript(preparedStatement, tourAd);
                preparedStatement.addBatch();
            }
            return HandlerSqlDAO.arrayHasOnlyOne(preparedStatement.executeBatch());

        }catch(Exception exc){
            exc.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean save(TourAd entity) {
        return this.saveAll(List.of(entity));
    }



    private static final String UPDATE_TYPE_STATE_BY_ID = "UPDATE tour_ad left join travel_agency on travel_agency_id = travel_agency.id  " +
            "SET type_state_id = ? WHERE user.id = ?;";


    @Override
    public boolean updateTypeStateById(Long id, TypeState typeState) {
        try(PreparedStatement preStatement = super.conn.getSqlPreparedStatement(UPDATE_TYPE_STATE_BY_ID)){
            preStatement.setInt(1,typeState.getId());
            preStatement.setLong(2, id);
            return preStatement.executeUpdate()!=0;

        }catch(Exception exc){
            exc.printStackTrace();
        }
        return ERROR_BOOLEAN_ANSWER;
    }

    @Override
    public ScriptTourAdWhere where() {
        return null;
    }

    @Override
    public List<TourAd> findByCostOneCustomerBetween(int startCostOneCustomer, int endCostOneCustomer, Supplier<String> script) {
        return null;
    }

    @Override
    public List<TourAd> findByCostServiceBetween(int startCostService, int endCostService, Supplier<String> script) {
        return null;
    }

    @Override
    public List<TourAd> findByDiscountSizePeopleBetween(int startDiscountSizePeople, int endDiscountSizePeople, Supplier<String> script) {
        return null;
    }

    @Override
    public List<TourAd> findByOrderQuantityBetween(int startOrderQuantity, int endOrderQuantity, Supplier<String> script) {
        return null;
    }

    @Override
    public List<TourAd> findByDiscountPercentageBetween(float startDiscountPercentage, float endDiscountPercentage, Supplier<String> script) {
        return null;
    }

    @Override
    public List<TourAd> findByRatingAgencyBetween(float startRatingAgency, float endRatingAgency, Supplier<String> script) {
        return null;
    }

    @Override
    public List<TourAd> findByHidden(boolean hidden, Supplier<String> script) {
        return null;
    }

    @Override
    public List<TourAd> findByDateRegistrationBetween(LocalDate startDateRegistration, LocalDate endDateRegistration, Supplier<String> script) {
        return null;
    }

    @Override
    public List<TourAd> findByStartDateTourAdAfter(LocalDate startDateTourAd, Supplier<String> script) {
        return null;
    }

    @Override
    public List<TourAd> findByEndDateTourAdBefore(LocalDate endDateTourAd, Supplier<String> script) {
        return null;
    }

    @Override
    public List<TourAd> findByStartDateTourAdAfterAndEndDateOrderBefore(LocalDate startDateTourAd, LocalDate endDateTourAd, Supplier<String> script) {
        return null;
    }

    @Override
    public List<TourAd> findByPlaceContaining(String place, Supplier<String> script) {
        return null;
    }

    @Override
    public List<TourAd> findByCityContaining(String city, Supplier<String> script) {
        return null;
    }

    @Override
    public List<TourAd> findByCountryContaining(String country, Supplier<String> script) {
        return null;
    }

    @Override
    public List<TourAd> findByNameAgencyContaining(String nameAgency, Supplier<String> script) {
        return null;
    }

    @Override
    public List<TourAd> findByTypeState(Long agencyId, Set<TypeState> typeStates, Supplier<String> script) {
        return null;
    }

    @Override
    public List<TourAd> findByConditionCommodity(Long agencyId, Set<ConditionCommodity> conditionCommodities, Supplier<String> script) {
        return null;
    }

    @Override
    public List<TourAd> findAll(Supplier<String> script) {
        return null;
    }

    @Override
    public List<TourAd> setOrderQuantity(List<TourAd> tourAds) {
        return null;
    }



    private static final String SELECT_TOUR_AD= "select " +
       "tour_ad.id," +
       "place,city," +
       "country.name AS country," +
       "user.name AS travel_agency_name," +
       "date_start," +
       "date_end," +
       "tour_ad.date_registration," +
       "cost_one_customer," +
       "cost_service," +
       "discount_size_people," +
       "discount_percentage," +
       "hidden," +
       "rating," +
       "type_state.name AS type_state," +
       "condition_commodity.name AS condition_commodity," +
       "travel_agency_id" +
       "from tour_ad" +
       "left join travel_agency on tour_ad.travel_agency_id = travel_agency.id" +
       "left join user on travel_agency.user_id = user.id" +
       "left join type_state on tour_ad.type_state_id = type_state.id" +
       "left join condition_commodity on tour_ad.condition_commodity_id = condition_commodity.id" +
       "left join country on tour_ad.country_id = country.id;";



@Component
class HandlerDAOtoMYSQL {

    private static final int PLACE_TOUR_AD_FOR_INSERT = 1;
    private static final int CITY_TOUR_AD_FOR_INSERT = 2;
    private static final int DATE_START_TOUR_AD_FOR_INSERT = 3;
    private static final int DATE_END_TOUR_AD_FOR_INSERT = 4;
    private static final int DATE_REGISTRATION_TOUR_AD_FOR_INSERT = 5;
    private static final int COST_ONE_CUSTOMER_TOUR_AD_FOR_INSERT = 6;
    private static final int COST_SERVICE_TOUR_AD_FOR_INSERT = 7;
    private static final int DISCOUNT_SIZE_PEOPLE_TOUR_AD_FOR_INSERT = 8;
    private static final int DISCOUNT_PERCENTAGE_TOUR_AD_FOR_INSERT = 9;
    private static final int HIDDEN_TOUR_AD_FOR_INSERT = 10;
    private static final int TRAVEL_AGENCY_ID_TOUR_AD_FOR_INSERT = 11;
    private static final int CONDITION_COMMODITY_ID_TOUR_AD_FOR_INSERT = 12;
    private static final int TYPE_STATE_ID_TOUR_AD_FOR_INSERT = 13;
    private static final int COUNTRY_ID_TOUR_AD_FOR_INSERT = 14;

    private static WorkWithCountries countries;

    @Autowired
    public void setCountries(WorkWithCountries countries){
        HandlerDAOtoMYSQL.countries = countries;
    }

    static void tourAdToMySqlScript(PreparedStatement preStat, TourAd tourAd) {
        try {
            preStat.setString(PLACE_TOUR_AD_FOR_INSERT, tourAd.getPlace());
            preStat.setString(CITY_TOUR_AD_FOR_INSERT, tourAd.getCity());
            preStat.setDate(DATE_START_TOUR_AD_FOR_INSERT, Date.valueOf(tourAd.getDateStart()));
            preStat.setDate(DATE_END_TOUR_AD_FOR_INSERT, Date.valueOf(tourAd.getDateEnd()));
            preStat.setTimestamp(DATE_REGISTRATION_TOUR_AD_FOR_INSERT, Timestamp.valueOf(tourAd.getDateRegistration()));
            preStat.setInt(COST_ONE_CUSTOMER_TOUR_AD_FOR_INSERT, tourAd.getCostOneCustomer());
            preStat.setInt(COST_SERVICE_TOUR_AD_FOR_INSERT, tourAd.getCostService());
            preStat.setInt(DISCOUNT_SIZE_PEOPLE_TOUR_AD_FOR_INSERT, tourAd.getDiscountSizePeople());
            preStat.setFloat(DISCOUNT_PERCENTAGE_TOUR_AD_FOR_INSERT, tourAd.getDiscountPercentage());
            preStat.setBoolean(HIDDEN_TOUR_AD_FOR_INSERT, tourAd.isHidden());
            preStat.setLong(TRAVEL_AGENCY_ID_TOUR_AD_FOR_INSERT, tourAd.getTravelAgencyId());
            preStat.setLong(CONDITION_COMMODITY_ID_TOUR_AD_FOR_INSERT, tourAd.getConditionCommodity().getId());
            preStat.setLong(TYPE_STATE_ID_TOUR_AD_FOR_INSERT, tourAd.getTypeState().getId());
            preStat.setLong(COUNTRY_ID_TOUR_AD_FOR_INSERT, countries.getIdByCountry(tourAd.getCountry()));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


        static TourAd resultSetToTourAd(ResultSet resultSet){
            TourAd tourAd = new TourAd();

            try {

                tourAd.setPlace(resultSet.getString(("place")));
                tourAd.setId(resultSet.getLong(("id")));
                tourAd.setCity(resultSet.getString(("city")));
                tourAd.setCountry(resultSet.getString(("country")));
                tourAd.setNameAgency(resultSet.getString(("travel_agency_name")));
                tourAd.setDateStart(resultSet.getDate(("date_start")).toLocalDate());
                tourAd.setDateEnd(resultSet.getDate(("date_end")).toLocalDate());
                tourAd.setDateRegistration(resultSet.getTimestamp(("date_registration")).toLocalDateTime());
                tourAd.setCostOneCustomer(resultSet.getInt(("cost_one_customer")));
                tourAd.setCostService(resultSet.getInt(("cost_service")));
                tourAd.setDiscountPercentage(resultSet.getFloat(("discount_percentage")));
                tourAd.setDiscountSizePeople(resultSet.getInt(("discount_size_people")));
                tourAd.setHidden(resultSet.getBoolean(("hidden")));
                tourAd.setRatingAgency(resultSet.getFloat(("rating")));
                tourAd.setTypeState(TypeState.valueOf(resultSet.getString(("type_state"))));
                tourAd.setConditionCommodity(ConditionCommodity.valueOf(resultSet.getString(("condition_commodity"))));
                tourAd.setTravelAgencyId(resultSet.getLong(("travel_agency_id")));


            } catch (SQLException e) {
                e.printStackTrace();
            }

            return tourAd;
        }
    }



    class ScriptTourAdWhereMySQL implements ScriptTourAdWhere{
    private String script = "";

        @Override
        public ScriptTourAdWhere idIs(Long id) {

            script+=" travel_agency_id = "+id+" ";
            return this;
        }

        @Override
        public ScriptTourAdWhere typeStateIs(Set<TypeState> types) {
            script+=" type_state.name IN (";
            int coun=0;
            for (TypeState typeState: types) {
                coun++;
               script+=" "+typeState+" , ";
                 if( coun == types.size()-1){
                     script+=" "+typeState+");";
                 }
            }

            return this;
        }

        @Override
        public ScriptTourAdWhere conditionCommodityIs(Set<ConditionCommodity> conditions) {
            return null;
        }

        @Override
        public String get() {
            return null;
        }
    }


}
