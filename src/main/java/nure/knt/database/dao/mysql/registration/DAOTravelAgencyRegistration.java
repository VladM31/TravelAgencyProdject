package nure.knt.database.dao.mysql.registration;

import nure.knt.database.idao.IConnectorGetter;
import nure.knt.database.idao.registration.IDAOUserRegistrationConfirm;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.important.TravelAgency;
import nure.knt.entity.important.User;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
public class DAOTravelAgencyRegistration extends RegistrationCore<TravelAgency> implements IDAOUserRegistrationConfirm<TravelAgency> {
    @Override
    public boolean saveForRegistration(TravelAgency user, String code) throws SQLException {
        if( HandlerRegistrationUser.tryStatement(super.conn,
                (p) -> HandlerRegistrationUser.saveUserAsRegistration(p,user,countries.getIdByCountry(user.getCountry())),
                HandlerRegistrationUser.INSERT_USER) == NOT_SAVE){
            return NOT_SAVE;
        }

        long userId = HandlerRegistrationUser.getUserIdByNumberAndEmailAndUsernameAndTypeStateIsRegistrationAdnDateRegistrationIsNew(super.conn,user);

        user.setId(userId);

        if(HandlerRegistrationUser.tryStatement(super.conn, (p) -> HandlerTravelAgencyRegistration.saveTravelAgency(user,p),HandlerTravelAgencyRegistration.INSERT_TRAVEL_AGENCY)== NOT_SAVE){
            return NOT_SAVE;
        }

        if(HandlerRegistrationUser.tryStatement(super.conn, (p) -> HandlerRegistrationUser.saveCode(p,userId,code),HandlerRegistrationUser.INSERT_CODE_VALUE)== NOT_SAVE){
            return NOT_SAVE;
        }
        return true;
    }

    private static final String UPDATE_CODE_CONFIRMED = "UPDATE travel_agency SET code_confirmed = true WHERE user_id = ?;";
    @Override
    public boolean saveForConfirmation(TravelAgency user) {
        try(java.sql.PreparedStatement statement = super.conn.getSqlPreparedStatement(UPDATE_CODE_CONFIRMED)){
            statement.setLong(1,user.getId());
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean userIsBooked(TravelAgency travelAgency) {
        return HandlerTravelAgencyRegistration.checkForTheExistenceOfTheUser(travelAgency,super.conn);
    }

}

class HandlerTravelAgencyRegistration{

    // =========================== CHECK USER ===============================
    private static final String SELECT_WHERE_EMAIL_OR_NUMBER_OR_USERNAME_IS =
            "SELECT user.id FROM travel_agency  " +
                    "left join user ON travel_agency.user_id = user.id " +
                    "left join type_state ON user.type_state_id = type_state.id " +
                    " WHERE (email = ? OR number = ? OR username = ? OR travel_agency.egrpoy_or_rnykpn = ? AND travel_agency.is_egrpoy=?) AND " +
                    " (type_state.name != ? OR type_state.name = ? AND user.date_registration > ?) ;";
    private static final int EMAIL_POSITION_FOR_CHECK_TRAVEL_AGENCY = 1;
    private static final int NUMBER_POSITION_FOR_CHECK_TRAVEL_AGENCY = 2;
    private static final int USERNAME_POSITION_FOR_CHECK_TRAVEL_AGENCY = 3;
    private static final int VALUE_EGRPOY_OR_RNYKPN_FOR_CHECK_TRAVEL_AGENCY = 4;
    private static final int IS_EGRPOY_POSITION_FOR_CHECK_TRAVEL_AGENCY = 5;

    private static final int TYPE_STATE_IS_NOT_REGISTRATION_POSITION_FOR_CHECK_TRAVEL_AGENCY = 6;
    private static final int TYPE_STATE_REGISTRATION_POSITION_FOR_CHECK_TRAVEL_AGENCY = 7;
    private static final int DATE_REGISTRATION_POSITION_FOR_CHECK_TRAVEL_AGENCY = 8;

    protected static boolean checkForTheExistenceOfTheUser(TravelAgency travelAgency, IConnectorGetter conn){
        try(java.sql.PreparedStatement statement = conn.getSqlPreparedStatement(SELECT_WHERE_EMAIL_OR_NUMBER_OR_USERNAME_IS)){

            statement.setString(EMAIL_POSITION_FOR_CHECK_TRAVEL_AGENCY,travelAgency.getEmail());
            statement.setString(NUMBER_POSITION_FOR_CHECK_TRAVEL_AGENCY,travelAgency.getNumber());
            statement.setString(USERNAME_POSITION_FOR_CHECK_TRAVEL_AGENCY,travelAgency.getUsername());
            statement.setLong(VALUE_EGRPOY_OR_RNYKPN_FOR_CHECK_TRAVEL_AGENCY,travelAgency.getEgrpoyOrRnekpn());
            statement.setBoolean(IS_EGRPOY_POSITION_FOR_CHECK_TRAVEL_AGENCY,travelAgency.isEgrpoy());

            statement.setString(TYPE_STATE_IS_NOT_REGISTRATION_POSITION_FOR_CHECK_TRAVEL_AGENCY, TypeState.REGISTRATION.toString());
            statement.setString(TYPE_STATE_REGISTRATION_POSITION_FOR_CHECK_TRAVEL_AGENCY,TypeState.REGISTRATION.toString());
            statement.setTimestamp(DATE_REGISTRATION_POSITION_FOR_CHECK_TRAVEL_AGENCY, Timestamp.valueOf(LocalDateTime.now().minusMinutes(15l)));

            try(java.sql.ResultSet resultSet = statement.executeQuery()){
                return resultSet.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    protected static final String INSERT_TRAVEL_AGENCY =
            " INSERT INTO travel_agency (user_id,rating,kved,egrpoy_or_rnykpn,is_egrpoy,code_confirmed,address,full_name_director,describe_agency,url_photo)" +
                    " VALUES (?,?,?,?,?,?,?,?,?,?);";

    protected static final Float DEFAULT_RATING = 0F;
    protected static final Boolean DEFAULT_CODE_CONFIRMED = Boolean.FALSE;
    protected static final String DEFAULT_DESCRIBE_AGENCY = "";
    protected static final String DEFAULT_URL_PHOTO = "";

    protected static boolean saveTravelAgency(TravelAgency travelAgency, PreparedStatement statement) {
        int position = 0;
        try{
            statement.setLong(++position,travelAgency.getId());
            statement.setFloat(++position,DEFAULT_RATING);

            statement.setString(++position,travelAgency.getKved());
            statement.setLong(++position,travelAgency.getEgrpoyOrRnekpn());
            statement.setBoolean(++position,travelAgency.isEgrpoy());

            statement.setBoolean(++position,DEFAULT_CODE_CONFIRMED);
            statement.setString(++position,travelAgency.getAddress());
            statement.setString(++position,travelAgency.getFullNameDirector());

            statement.setString(++position,DEFAULT_DESCRIBE_AGENCY);
            statement.setString(++position,DEFAULT_URL_PHOTO);
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
