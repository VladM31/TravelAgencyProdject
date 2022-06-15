package nure.knt.database.dao.mysql.entity;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.tools.MySQLCore;
import nure.knt.database.idao.entity.IDAOTravelAgencySQL;
import nure.knt.entity.enums.Role;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.important.Customer;
import nure.knt.entity.important.TravelAgency;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static nure.knt.database.dao.HandlerSqlDAO.*;
import static nure.knt.database.dao.HandlerSqlDAO.ERROR_BOOLEAN_ANSWER;

@Component("DA0_MySQL_Travel_Agency")
public class DAOTravelAgencyMySQL extends MySQLCore implements IDAOTravelAgencySQL<TravelAgency> {




    private static final String WHERE_ID_IN = " AND user.id IN ( "+ REPLACE_SYMBOL+" )";

    @Override
    public List<TravelAgency> findAllById(Iterable<Long> ids) {
        return HandlerSqlDAO.useSelectScript(super.conn, HandlerSqlDAO.concatScriptToEnd(SELECT_TRAVEL_AGENCY,
                WHERE_ID_IS,HandlerSqlDAO.SORT_TO_DATE_REGISTRATION), HandlerDAOTAMYSQL::resultSetToTravelAgency,ids);

    }



    private static final String WHERE_ID_IS = " AND user.id = ? ";

    @Override
    public TravelAgency findOneById(Long id) {
        return HandlerSqlDAO.useSelectScriptAndGetOneObject(super.conn,
                HandlerSqlDAO.concatScriptToEnd(SELECT_TRAVEL_AGENCY, WHERE_ID_IS, HandlerSqlDAO.SORT_TO_DATE_REGISTRATION),
                HandlerDAOTAMYSQL::resultSetToTravelAgency, id);

    }

    private static final String SELECT_COUNT = "SELECT COUNT(*) AS size_travel_agency FROM travel_agency;";

    @Override
    public long size() {
        try(java.sql.Statement statement = this.conn.getSqlStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_COUNT)){
            resultSet.next();
            return resultSet.getLong("size_travel_agency");
        }catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
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
    public int deleteById(Long id) { return 0;   }

    @Override
    public int deleteAll() {
        return 0;
    }

    @Override
    public boolean editing(Long id,TravelAgency entity) {
        return false;
    }




    private static final String UPDATE_ITERABLE_BY_ID = "UPDATE travel_agency left join user on travel_agency.user_id = user.id  " +
            "SET number = ?, email = ?,username = ?,password = ?,name = ?,active = ?,date_registration = ?,role_id = ?," +
            "country_id = ?,type_state_id = ?,rating = ?,kved = ?," +
            "egrpoy_or_rnykpn = ?,is_egrpoy = ?,address = ?," +
            "full_name_director = ?,describe_agency = ?,url_photo = ? WHERE user.id = ?;";



    @Override
    public int[] updateAllById(Iterable<TravelAgency> entities) {
        return HandlerSqlDAO.updateById(super.conn,UPDATE_ITERABLE_BY_ID,entities,HandlerDAOTAMYSQL::travelAgencyToMySqlUpdateScript);
    }


    @Override
    public int updateOneById(TravelAgency entity) {
        return this.updateAllById(List.of(entity))[0];
    }




    private static final String WHERE_IDS_ARE = " AND travel_agency.id = ? ";

    @Override
    public List<TravelAgency> findByTravelAgencyIdIn(Iterable<Long> ids) {
        return HandlerSqlDAO.useSelectScript(super.conn, HandlerSqlDAO.concatScriptToEnd(SELECT_TRAVEL_AGENCY,
                WHERE_IDS_ARE,HandlerSqlDAO.SORT_TO_DATE_REGISTRATION), HandlerDAOTAMYSQL::resultSetToTravelAgency,ids);
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


    private static final String WHERE_NUMBER_IS = " AND user.number = ? ";

    @Override
    public TravelAgency findByNumber(String number) {
        return HandlerSqlDAO.useSelectScriptAndGetOneObject(conn, HandlerSqlDAO.concatScriptToEnd(SELECT_TRAVEL_AGENCY,WHERE_NUMBER_IS,SORT_TO_DATE_REGISTRATION),
                HandlerDAOTAMYSQL::resultSetToTravelAgency,number);
    }


    private static final String WHERE_EMAIL_IS = " AND user.email = ? ";

    @Override
    public TravelAgency findByEmail(String email) {
        return HandlerSqlDAO.useSelectScriptAndGetOneObject(conn, HandlerSqlDAO.concatScriptToEnd(SELECT_TRAVEL_AGENCY,WHERE_EMAIL_IS,SORT_TO_DATE_REGISTRATION),
                HandlerDAOTAMYSQL::resultSetToTravelAgency,email);
    }


    private static final String WHERE_NUMBER_CONTAINING = " AND user.number LIKE ? ";

    @Override
    public List<TravelAgency> findByNumberContaining(String number) {
        return HandlerSqlDAO.useSelectScript(super.conn,HandlerSqlDAO.concatScriptToEnd(SELECT_TRAVEL_AGENCY,
                        WHERE_NUMBER_CONTAINING,SORT_TO_DATE_REGISTRATION),
                HandlerDAOTAMYSQL::resultSetToTravelAgency,
                HandlerSqlDAO.containingString(number));
    }


    private static final String WHERE_USER_NAME_CONTAINING = " AND user.name LIKE ? ";

    @Override
    public List<TravelAgency> findByUsernameContaining(String username) {
        return HandlerSqlDAO.useSelectScript(super.conn,HandlerSqlDAO.concatScriptToEnd(SELECT_TRAVEL_AGENCY,
                        WHERE_USER_NAME_CONTAINING,SORT_TO_DATE_REGISTRATION),
                HandlerDAOTAMYSQL::resultSetToTravelAgency,
                HandlerSqlDAO.containingString(username));
    }


    private static final String WHERE_EMAIL_CONTAINING = " AND user.email LIKE ? ";

    @Override
    public List<TravelAgency> findByEmailContaining(String start) {
        return HandlerSqlDAO.useSelectScript(super.conn,HandlerSqlDAO.concatScriptToEnd(SELECT_TRAVEL_AGENCY,
                        WHERE_EMAIL_CONTAINING,SORT_TO_DATE_REGISTRATION),
                HandlerDAOTAMYSQL::resultSetToTravelAgency,
                HandlerSqlDAO.containingString(start));
    }


    private static final String WHERE_PASSWORD_IS = " AND user.password = ? ";


    @Override
    public List<TravelAgency> findByPassword(String password) {
        return HandlerSqlDAO.useSelectScript(super.conn,HandlerSqlDAO.concatScriptToEnd(SELECT_TRAVEL_AGENCY,
                        WHERE_PASSWORD_IS,SORT_TO_DATE_REGISTRATION),
                HandlerDAOTAMYSQL::resultSetToTravelAgency,password);
    }


    private static final String WHERE_DATE_REGISTRATION_BETWEEN = " AND user.date_registration BETWEEN ? AND ? ";

    @Override
    public List<TravelAgency> findByDateRegistrationBetween(LocalDateTime start, LocalDateTime end) {
        return HandlerSqlDAO.useSelectScript(super.conn,HandlerSqlDAO.concatScriptToEnd(SELECT_TRAVEL_AGENCY,
                        WHERE_DATE_REGISTRATION_BETWEEN,SORT_TO_DATE_REGISTRATION),
                HandlerDAOTAMYSQL::resultSetToTravelAgency,start,end);
    }




    private static final String WHERE_ACTIVE_IS = " AND user.active = ? ";

    @Override
    public List<TravelAgency> findByActive(boolean active) {
        return HandlerSqlDAO.useSelectScript(super.conn,HandlerSqlDAO.concatScriptToEnd(SELECT_TRAVEL_AGENCY,
                        WHERE_ACTIVE_IS,SORT_TO_DATE_REGISTRATION),
                HandlerDAOTAMYSQL::resultSetToTravelAgency,active);
    }



    private static final String WHERE_ROLE_IS = " AND user.role_id = ? ";

    @Override
    public List<TravelAgency> findByRole(Role role) {
        return HandlerSqlDAO.useSelectScript(super.conn,HandlerSqlDAO.concatScriptToEnd(SELECT_TRAVEL_AGENCY,
                        WHERE_ROLE_IS,SORT_TO_DATE_REGISTRATION),
                HandlerDAOTAMYSQL::resultSetToTravelAgency,role.getId());
    }



    @Override
    public List<TravelAgency> findByTypeState(TypeState typeState) {
        return HandlerSqlDAO.useSelectScript(super.conn,HandlerSqlDAO.concatScriptToEnd(SELECT_TRAVEL_AGENCY.replace("20", "?"),SORT_TO_DATE_REGISTRATION),
                HandlerDAOTAMYSQL::resultSetToTravelAgency,typeState.getId());
    }



    private static final String WHERE_COUNTRY_IS = " AND country.name = ? ";

    @Override
    public List<TravelAgency> findByCountry(String country) {
        return HandlerSqlDAO.useSelectScript(super.conn,HandlerSqlDAO.concatScriptToEnd(SELECT_TRAVEL_AGENCY,
                        WHERE_COUNTRY_IS,SORT_TO_DATE_REGISTRATION),
                HandlerDAOTAMYSQL::resultSetToTravelAgency,country);
    }


    private static final String WHERE_NAME_CONTAINING = " AND user.name LIKE ? ";

    @Override
    public List<TravelAgency> findByNameContaining(String name) {
        return HandlerSqlDAO.useSelectScript(super.conn,HandlerSqlDAO.concatScriptToEnd(SELECT_TRAVEL_AGENCY,
                        WHERE_NAME_CONTAINING,SORT_TO_DATE_REGISTRATION),
                HandlerDAOTAMYSQL::resultSetToTravelAgency,
                HandlerSqlDAO.containingString(name));
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
            "WHERE user.type_state_id = 20;";

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





    private static final String UPDATE_TYPE_STATE_BY_ID = "UPDATE travel_agency left join user on travel_agency.user_id = user.id  " +
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
            preStat.setString(TYPE_STATE_TRAVEL_AGENCY_FOR_INSERT,travelAgency.getTypeState().toString());
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



    private static int POSITION_RATING_FOR_UPDATE = 11;
    private static int POSITION_KVED_FOR_UPDATE = 12;
    private static int POSITION_EGRPOY_OR_MYKPN_FOR_UPDATE = 13;
    private static int POSITION_IS_EGRPOY_FOR_UPDATE = 14;
    private static int POSITION_ADDRESS_FOR_UPDATE = 15;
    private static int POSITION_FULL_NAME_DIRECTOR_FOR_UPDATE = 16;
    private static int POSITION_DESCRIBE_AGENCY_FOR_UPDATE = 17;
    private static int POSITION_URL_PHOTO_FOR_UPDATE = 18;
    private static int POSITION_USER_ID_FOR_UPDATE = 19;


    static boolean travelAgencyToMySqlUpdateScript(PreparedStatement preStat, TravelAgency travelAgency){

        if(HandlerUser.userToMySqlScript(preStat,travelAgency) == ERROR_BOOLEAN_ANSWER){
            return ERROR_BOOLEAN_ANSWER;
        }

        try {
            preStat.setFloat(POSITION_RATING_FOR_UPDATE,travelAgency.getRating());
            preStat.setString(POSITION_KVED_FOR_UPDATE,travelAgency.getKved());
            preStat.setLong(POSITION_EGRPOY_OR_MYKPN_FOR_UPDATE, travelAgency.getEgrpoyOrRnekpn());
            preStat.setBoolean(POSITION_IS_EGRPOY_FOR_UPDATE, travelAgency.isEgrpoy());
            preStat.setString(POSITION_ADDRESS_FOR_UPDATE, travelAgency.getAddress());
            preStat.setString(POSITION_FULL_NAME_DIRECTOR_FOR_UPDATE, travelAgency.getFullNameDirector());
            preStat.setString(POSITION_DESCRIBE_AGENCY_FOR_UPDATE, travelAgency.getDescribeAgency());
            preStat.setString(POSITION_URL_PHOTO_FOR_UPDATE, travelAgency.getUrlPhoto());
            preStat.setLong(POSITION_USER_ID_FOR_UPDATE, travelAgency.getId());
            return HAVE_NO_ERROR;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ERROR_BOOLEAN_ANSWER;
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
