package nure.knt.database.dao.mysql.entity;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.tools.MySQLCore;
import nure.knt.database.idao.entity.IDAOTravelAgencySQL;
import nure.knt.entity.enums.Role;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.important.TravelAgency;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Component("DA0_TRAVEL_AGENCY_MYSQL")
public class DAOTravelAgencyMySQL extends MySQLCore implements IDAOTravelAgencySQL<TravelAgency> {

    @Override
    public List<TravelAgency> findAllById(Iterable<Long> ids) {
        return null;
    }

    @Override
    public TravelAgency findOneById(Long id) {
        return null;
    }

    @Override
    public long size() {
        return 0;
    }

    @Override
    public int deleteAllById(Iterable<Long> ids) {
        return 0;
    }

    @Override
    public int deleteAllByEntity(Iterable<TravelAgency> entities) {
        return 0;
    }

    @Override
    public int deleteByEntity(TravelAgency entity) {
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    @Override
    public int deleteAll() {
        return 0;
    }

    @Override
    public boolean editing(TravelAgency entity) {
        return false;
    }

    @Override
    public int updateAllById(Iterable<TravelAgency> entities) {
        return 0;
    }

    @Override
    public int updateOneById(TravelAgency entity) {
        return 0;
    }

    @Override
    public List<TravelAgency> findByTravelAgencyIdIn(Iterable<Long> ids) {
        return null;
    }

    @Override
    public List<TravelAgency> findByTravelAgencyObjectIdIn(Iterable<TravelAgency> ids) {
        return null;
    }

    @Override
    public TravelAgency findByTravelAgencyId(Long id) {
        return null;
    }

    @Override
    public List<TravelAgency> findByRatingBetween(float ratingF, float ratingE) {
        return null;
    }

    @Override
    public List<TravelAgency> findByKVEDContaining(String kved) {
        return null;
    }

    @Override
    public TravelAgency findByEGRPOY(Long egrpoy) {
        return null;
    }

    @Override
    public List<TravelAgency> findByEGRPOY(boolean isEGRPOY) {
        return null;
    }

    @Override
    public TravelAgency findByRNEKPN(Long rnekpn) {
        return null;
    }

    @Override
    public List<TravelAgency> findByAddressContaining(String address) {
        return null;
    }

    @Override
    public List<TravelAgency> findByAllNameDirectoContaining(String allNameDirector) {
        return null;
    }

    private static final String LIMIT_IS = " LIMIT ? ;";
    private static final String SORT_BY_RATING = "  ORDER BY rating DESC ";

    @Override
    public List<TravelAgency> findAllAndLimit(int limit) {
        return HandlerSqlDAO.useSelectScript(this.conn, HandlerSqlDAO.concatScriptToEnd(SELECT_TRAVEL_AGENCY,SORT_BY_RATING,LIMIT_IS),
                HandlerDAOTAMYSQL::resultSetToTravelAgency,limit);
    }

    @Override
    public TravelAgency findByNumber(String number) {
        return null;
    }

    @Override
    public TravelAgency findByEmail(String email) {
        return null;
    }

    @Override
    public List<TravelAgency> findByNumberContaining(String number) {
        return null;
    }

    @Override
    public List<TravelAgency> findByUsernameContaining(String username) {
        return null;
    }

    @Override
    public List<TravelAgency> findByEmailContaining(String start) {
        return null;
    }

    @Override
    public List<TravelAgency> findByPassword(String password) {
        return null;
    }

    @Override
    public List<TravelAgency> findByDateRegistrationBetween(LocalDateTime start, LocalDateTime end) {
        return null;
    }

    @Override
    public List<TravelAgency> findByActive(boolean active) {
        return null;
    }

    @Override
    public List<TravelAgency> findByRole(Role role) {
        return null;
    }

    @Override
    public List<TravelAgency> findByTypeState(TypeState typeState) {
        return null;
    }

    @Override
    public List<TravelAgency> findByCountry(String country) {
        return null;
    }

    @Override
    public List<TravelAgency> findByNameContaining(String name) {
        return null;
    }

    private static final String INSERT_TRAVEL_AGENCY =
            " INSERT INTO travel_agency (user_id,rating,kved,egrpoy_or_rnykpn,is_egrpoy,code_confirmed,address,full_name_director,describe_agency,url_photo)" +
                    " VALUES ((select id from user WHERE email = ? AND user.type_state_id =(SELECT id FROM type_state WHERE name = ?)),?,?,?,?,?,?,?,?,?);";
    @Override
    public boolean saveAll(Iterable<TravelAgency> entities) {
        return HandlerUser.useInsertForIterableHeirUser(entities,this.conn,false,
                INSERT_TRAVEL_AGENCY,HandlerDAOTAMYSQL::travelAgencyToMySqlScript);
    }

    @Override
    public boolean save(TravelAgency entity) {
        return this.saveAll(List.of(entity));
    }


    private static final String SELECT_TRAVEL_AGENCY = "select " +
            "user.id AS user_pk, " +
            "travel_agency.id AS travel_agency_pk," +
            "number,email,username,password,user.name," +
            "user.active, date_registration," +
            "country.name AS country," +
            "rating,kved,egrpoy_or_rnykpn,is_egrpoy," +
            "address,full_name_director,describe_agency," +
            "url_photo " +
            "from travel_agency " +
            "left join user on travel_agency.user_id = user.id " +
            "left join country on user.country_id = country.id " +
            "WHERE user.type_state_id = 20 ;";

    private static final String SORT_TO_DATE_REGISTRATION = " ORDER BY date_registration ASC;";

    @Override
    public List<TravelAgency> findAll() {
        return HandlerSqlDAO.useSelectScript(this.conn, HandlerSqlDAO.concatScriptToEnd(SELECT_TRAVEL_AGENCY,SORT_TO_DATE_REGISTRATION),HandlerDAOTAMYSQL::resultSetToTravelAgency);
    }

    private static final String WHERE_USERNAME_IS =" AND user.username = ? ";

    @Nullable
    @Override
    public TravelAgency findByUsername(String username) {
        return HandlerSqlDAO.useSelectScriptAndGetOneObject(this.conn, HandlerSqlDAO.concatScriptToEnd(SELECT_TRAVEL_AGENCY,WHERE_USERNAME_IS,SORT_TO_DATE_REGISTRATION),
                HandlerDAOTAMYSQL::resultSetToTravelAgency,username);
    }


    @Override
    public boolean updateTypeStateById(Long id, TypeState typeState) {
        return false;
    }
}

class HandlerDAOTAMYSQL{

    private static final int EMAIL_TRAVEL_AGENCY_FOR_INSERT = 1;
    private static final int TYPE_STATE_TRAVEL_AGENCY_FOR_INSERT = 2;
    private static final int RATING_TRAVEL_AGENCY_FOR_INSERT = 3;
    private static final int KVED_TRAVEL_AGENCY_FOR_INSERT = 4;
    private static final int EGRPOY_OR_RNYKPN_TRAVEL_AGENCY_FOR_INSERT = 5;
    private static final int IS_EGRPOY_TRAVEL_AGENCY_FOR_INSERT = 6;
    private static final int CODE_CONFIRMED_TRAVEL_AGENCY_FOR_INSERT = 7;
    private static final int ADDRESS_TRAVEL_AGENCY_FOR_INSERT = 8;
    private static final int FULL_NAME_DIRECTOR_TRAVEL_AGENCY_FOR_INSERT = 9;
    private static final int DESCRIBE_AGENCY_TRAVEL_AGENCY_FOR_INSERT = 10;
    private static final int URL_PHOTO_TRAVEL_AGENCY_FOR_INSERT = 11;

    private static final boolean CODE_CONFIRMED =true;//I_HOPE_THIS_IS_TRUE_IF_IS_NOT_WE_HAVE_A_PROBLEM

    static void travelAgencyToMySqlScript(PreparedStatement preStat, TravelAgency travelAgency){
        try {
            preStat.setString(EMAIL_TRAVEL_AGENCY_FOR_INSERT,travelAgency.getEmail());
            preStat.setString(TYPE_STATE_TRAVEL_AGENCY_FOR_INSERT,travelAgency.getRole().toString());
            preStat.setFloat(RATING_TRAVEL_AGENCY_FOR_INSERT,travelAgency.getRating());
            preStat.setString(KVED_TRAVEL_AGENCY_FOR_INSERT,travelAgency.getKved());
            preStat.setLong(EGRPOY_OR_RNYKPN_TRAVEL_AGENCY_FOR_INSERT,travelAgency.getEgrpoyOrRnekpn());
            preStat.setBoolean(IS_EGRPOY_TRAVEL_AGENCY_FOR_INSERT,travelAgency.isEgrpoy());
            preStat.setBoolean(CODE_CONFIRMED_TRAVEL_AGENCY_FOR_INSERT,CODE_CONFIRMED);
            preStat.setString(ADDRESS_TRAVEL_AGENCY_FOR_INSERT,travelAgency.getAddress());
            preStat.setString(FULL_NAME_DIRECTOR_TRAVEL_AGENCY_FOR_INSERT,travelAgency.getFullNameDirector());
            preStat.setString(DESCRIBE_AGENCY_TRAVEL_AGENCY_FOR_INSERT,travelAgency.getDescribeAgency());
            preStat.setString(URL_PHOTO_TRAVEL_AGENCY_FOR_INSERT,travelAgency.getUrlPhoto());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    static TravelAgency resultSetToTravelAgency(ResultSet resultSet){
        TravelAgency travelAgency = new TravelAgency();

        travelAgency.setRole(Role.TRAVEL_AGENCY);
        travelAgency.setTypeState(TypeState.REGISTERED);

        try {
            HandlerUser.resultSetToUserCore(resultSet,travelAgency);

            travelAgency.setTravelId(resultSet.getLong("travel_agency_pk"));

            travelAgency.setRating(resultSet.getFloat("rating"));
            travelAgency.setKved(resultSet.getString("kved"));

            travelAgency.setEgrpoyOrRnekpn(resultSet.getLong("egrpoy_or_rnykpn"));
            travelAgency.setEgrpoy(resultSet.getBoolean("is_egrpoy"));
            travelAgency.setAddress(resultSet.getString("address"));

            travelAgency.setFullNameDirector(resultSet.getString("full_name_director"));
            travelAgency.setDescribeAgency(resultSet.getString("describe_agency"));
            travelAgency.setUrlPhoto(resultSet.getString("url_photo"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return travelAgency;
    }
}
