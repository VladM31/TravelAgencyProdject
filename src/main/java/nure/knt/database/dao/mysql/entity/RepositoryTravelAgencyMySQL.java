package nure.knt.database.dao.mysql.entity;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.terms.users.TermTravelAgencyMySQL;
import nure.knt.database.idao.entity.IDAOUserEdit;
import nure.knt.database.idao.factory.IFactoryEntity;
import nure.knt.database.idao.terms.ITermInformation;
import nure.knt.database.idao.terms.fieldenum.IUserField;
import nure.knt.database.idao.terms.fieldenum.TravelAgencyField;
import nure.knt.database.idao.terms.fieldenum.UserField;
import nure.knt.database.idao.terms.users.ITermTravelAgency;
import nure.knt.database.idao.tools.IConnectorGetter;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.important.TravelAgency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

@Repository("Repository_Travel_Agency_MySQL")
@Order(10)
public class RepositoryTravelAgencyMySQL extends MySQLUserCore<TravelAgency> implements IDAOUserEdit<TravelAgency, ITermTravelAgency> {
    @Autowired
    @Qualifier("Get_Object_By_Field_For_Travel_Agency")
    private Map<IUserField, Function<TravelAgency,Object>> travelAgencyFieldFunctionMap;
    @Autowired
    @Qualifier("Get_Name_By_Field_For_Travel_Agency")
    private Map<IUserField,String> travelAgencyFieldStringMap;
    @Autowired
    @Qualifier("Factory_Travel_Agency_MySQL")
    private IFactoryEntity<TravelAgency> factory;
    @Autowired
    @Qualifier("Getter_Travel_Agency_Id")
    private AtomicLong generatorTravelAgencyId;

    @Override
    public boolean saveAll(Iterable<TravelAgency> entities) {
        if(!HandlerUser.saveUsersAndReturnsNewIds(super.conn,entities,super.getterId)) return false;
        return HandlerRepositoryTravelAgencyMySQL.travelAgencyToMySqlScript(super.conn,entities,this.generatorTravelAgencyId);
    }

    @Override
    public boolean save(TravelAgency entity) {
        return this.saveAll(List.of(entity));
    }

    private static final String UPDATE_TRAVEL_AGENCY = "UPDATE travel_agency JOIN user on user.id = travel_agency.user_id SET %s WHERE user.id = ? ;";
    @Override
    public int[] updateAllById(Iterable<TravelAgency> entities, IUserField... fields) {
        if (HandlerUser.areNotNormalFields(fields, UserField.ID, TravelAgencyField.TRAVEL_AGENCY_ID)){
            return HandlerSqlDAO.ERROR_UPDATE;
        }

        return HandlerSqlDAO.updateByFieldAndId(super.conn,
                String.format(UPDATE_TRAVEL_AGENCY,
                        HandlerSqlDAO.getScript(this.travelAgencyFieldStringMap,
                                fields,
                                " = ? ",
                                ", ")),
                entities,
                this.travelAgencyFieldFunctionMap,
                fields, UserField.ID);
    }

    @Override
    public int updateOneById(TravelAgency entity, IUserField... fields) {
        return this.updateAllById(List.of(entity),fields)[0];
    }

    private static final String SELECT_AND_PARAMETERS_FOR_TRAVEL_AGENCY =
            MySQLUserCore.SELECT_AND_PARAMETERS_FOR_USER.concat(",\ntravel_agency.id AS travel_agency_pk,\n" +
                    "travel_agency.rating,\n" +
                    "travel_agency.kved,\n" +
                    "travel_agency.egrpoy_or_rnykpn,\n" +
                    "travel_agency.is_egrpoy,\n" +
                    "travel_agency.address,\n" +
                    "travel_agency.full_name_director,\n" +
                    "travel_agency.describe_agency,\n" +
                    "travel_agency.url_photo\n");

    private static final String FROM_AND_JOIN =
            "from travel_agency \n" +
                    "LEFT join user ON travel_agency.user_id = user.id\n" +
                    "LEFT JOIN country ON user.country_id = country.id\n" +
                    "LEFT JOIN role ON user.role_id = role.id  \n" +
                    "LEFT JOIN type_state ON user.type_state_id = type_state.id";
    @Override
    public Optional<TravelAgency> findOneBy(ITermInformation information) {
        return Optional.ofNullable(
                HandlerSqlDAO.useSelectScriptAndGetOneObject(super.conn,
                        HandlerSqlDAO.toScriptDefault(SELECT_AND_PARAMETERS_FOR_TRAVEL_AGENCY,FROM_AND_JOIN,information,super.concatScripts),
                        factory::getEntity,
                        information.getParameters()
                )
        );
    }

    @Override
    public List<TravelAgency> findBy(ITermInformation information) {
        return HandlerSqlDAO.useSelectScript(super.conn,
                        HandlerSqlDAO.toScriptDefault(SELECT_AND_PARAMETERS_FOR_TRAVEL_AGENCY,FROM_AND_JOIN,information,super.concatScripts),
                        factory::getEntity,
                        information.getParameters()
                );

    }

    @Override
    public ITermTravelAgency term() {
        return new TermTravelAgencyMySQL(this.travelAgencyFieldStringMap);
    }

    private static final String SAVE_EDIT = "INSERT INTO edit_agency(confirmed,need_delete,whom_need_change_id,what_to_change) VALUE(false,false,?,?)";
    @Override
    public boolean editing(Long id, TravelAgency entity) {
        entity.setTypeState(TypeState.EDITING);

        if(!this.save(entity)) return false;

        try(PreparedStatement statement = super.conn.getSqlPreparedStatement(super.concatScripts.concatScripts(SAVE_EDIT))){
            statement.setLong(1,id);
            statement.setLong(2,entity.getTravelId());
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static final String UPDATE_EDIT =
            "update  user AS main_u\n" +
            "join travel_agency AS main_ta on main_ta.user_id = main_u.id\n" +
            "left join edit_agency ed on ed.whom_need_change_id = main_ta.id\n" +
            "join travel_agency as changes_ta on changes_ta.id = ed.what_to_change\n" +
            "join user as changes_u on changes_u.id = changes_ta.user_id\n" +
            "SET main_u.number = changes_u.number,\n" +
            "main_u.email = changes_u.email,\n" +
            "main_u.username = changes_u.username,\n" +
            "main_u.password = changes_u.password,\n" +
            "main_u.name = changes_u.name,\n" +
            "main_u.country_id = changes_u.country_id,\n" +
            "main_ta.kved = changes_ta.kved,\n" +
            "main_ta.egrpoy_or_rnykpn = changes_ta.egrpoy_or_rnykpn,\n" +
            "main_ta.is_egrpoy = changes_ta.is_egrpoy,\n" +
            "main_ta.address = changes_ta.address,\n" +
            "main_ta.describe_agency = changes_ta.describe_agency,\n" +
            "main_ta.url_photo = changes_ta.url_photo\n" +
            "WHERE ed.what_to_change = 668;";

    private static final String UPDATE_EDITE_TRAVEL_AGENCY_AS_USED= "UPDATE edit_agency SET confirmed = true,need_delete = true WHERE what_to_change = ?;";
    private static final String UPDATE_EDITE_TRAVEL_AGENCY_AS_CANCELED= "UPDATE edit_agency SET need_delete = true WHERE what_to_change = ?;";

    @Override
    public boolean useEdit(Long id) {
        if(HandlerSqlDAO.updateByParameters(super.conn,super.concatScripts.concatScripts(UPDATE_EDIT),id) == 0)
            return false;
        return HandlerSqlDAO.updateByParameters(super.conn,super.concatScripts.concatScripts(UPDATE_EDITE_TRAVEL_AGENCY_AS_USED),id) != 0;
    }

    @Override
    public boolean cancelEdit(Long id) {
        return HandlerSqlDAO.updateByParameters(super.conn,super.concatScripts.concatScripts(UPDATE_EDITE_TRAVEL_AGENCY_AS_CANCELED),id) != 0;
    }

    private static final String SELECT_WHERE_EMAIL_OR_NUMBER_OR_USERNAME_OR_NAME_EGRPOY_OR_RNYKPN_IS =
            "SELECT u.id FROM user u " +
                    "left join type_state ON u.type_state_id = type_state.id\n" +
                    "left join travel_agency ta ON u.id = ta.user_id \n" +
                    "WHERE " +
                    "(u.email = ? OR u.number = ? OR u.username = ? OR u.name = ? " +
                    "OR (ta.is_egrpoy = ? AND ta.egrpoy_or_rnykpn = ?))\n" +
                    "AND (type_state.name = ? OR type_state.name = ? AND u.date_registration > ?) ;";

    @Override
    public boolean isBooked(TravelAgency user) {
        return HandlerSqlDAO.useSelectScriptAndGetOneObject(super.conn,super.concatScripts.concatScripts(SELECT_WHERE_EMAIL_OR_NUMBER_OR_USERNAME_OR_NAME_EGRPOY_OR_RNYKPN_IS), r -> true,
                user.getEmail(),user.getNumber(),user.getUsername(),user.getName(),user.isEgrpoy(),user.getEgrpoyOrRnekpn(),
               TypeState.REGISTERED.name(),TypeState.REGISTRATION.name(), LocalDateTime.now().minusMinutes(15l)) != null;
    }
}

class HandlerRepositoryTravelAgencyMySQL{

    private static final String INSERT_TRAVEL_AGENCY =
            " INSERT INTO travel_agency (id,rating,kved,egrpoy_or_rnykpn,is_egrpoy,code_confirmed,address,full_name_director,describe_agency,url_photo,user_id)" +
                    " VALUES (?,?,?,?,?,?,?,?,?,?,?);";

    enum Parameter{
        TRAVEL_AGENCY_ID(1),RATING(2),KVED(3),EGRPOY_OR_RNYKPN(4),
        IS_EGRPOY(5),CODE_CONFIRMED(6),ADDRESS(7),FULL_NAME_DIRECTOR(8),
        DESCRIBE_AGENCY(9),URL_PHOTO(10),USER_ID(11);
        public final int position;
        Parameter(int position){
            this.position = position;
        }
    }

    private static final boolean CODE_CONFIRMED_DEFAULT_VALUE = true;//I_HOPE_THIS_IS_TRUE_IF_IS_NOT_WE_HAVE_A_PROBLEM

    static boolean travelAgencyToMySqlScript(IConnectorGetter connector, Iterable<TravelAgency> travelAgencies, AtomicLong generatorCustomerIds){
        try(PreparedStatement statement = connector.getSqlPreparedStatement(INSERT_TRAVEL_AGENCY)) {

            for (TravelAgency travelAgency:travelAgencies) {
                travelAgency.setTravelId(generatorCustomerIds.incrementAndGet());
                statement.setLong(Parameter.TRAVEL_AGENCY_ID.position,travelAgency.getTravelId());
                statement.setFloat(Parameter.RATING.position,travelAgency.getRating());
                statement.setString(Parameter.KVED.position,travelAgency.getKved());

                statement.setLong(Parameter.EGRPOY_OR_RNYKPN.position,travelAgency.getEgrpoyOrRnekpn());
                statement.setBoolean(Parameter.IS_EGRPOY.position,travelAgency.isEgrpoy());
                statement.setBoolean(Parameter.CODE_CONFIRMED.position,CODE_CONFIRMED_DEFAULT_VALUE);
                statement.setString(Parameter.ADDRESS.position,travelAgency.getAddress());

                statement.setString(Parameter.FULL_NAME_DIRECTOR.position,travelAgency.getFullNameDirector());
                statement.setString(Parameter.DESCRIBE_AGENCY.position,travelAgency.getDescribeAgency());
                statement.setString(Parameter.URL_PHOTO.position,travelAgency.getUrlPhoto());
                statement.setLong(Parameter.USER_ID.position,travelAgency.getId());

                statement.addBatch();
            }

            return HandlerSqlDAO.arrayHasOnlyOne(statement.executeBatch());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
