package nure.knt.database.dao.mysql.entity;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.terms.users.TermTravelAgencyMySQL;
import nure.knt.database.idao.entity.IDAOUserWithTerms;
import nure.knt.database.idao.factory.IFactoryEntity;
import nure.knt.database.idao.terms.ITermInformation;
import nure.knt.database.idao.terms.fieldenum.CustomerField;
import nure.knt.database.idao.terms.fieldenum.IUserField;
import nure.knt.database.idao.terms.fieldenum.TravelAgencyField;
import nure.knt.database.idao.terms.fieldenum.UserField;
import nure.knt.database.idao.terms.users.ITermTravelAgency;
import nure.knt.database.idao.tools.IConnectorGetter;
import nure.knt.entity.important.Customer;
import nure.knt.entity.important.TravelAgency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

@Repository("Repository_Travel_Agency_MySQL")
@Order(10)
public class RepositoryTravelAgencyMySQL extends MySQLUserCore<TravelAgency> implements IDAOUserWithTerms<TravelAgency, ITermTravelAgency> {
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
        HandlerUser.saveUsersAndReturnsNewIds(super.conn,entities,super.getterId);
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
}

class HandlerRepositoryTravelAgencyMySQL{

    private static final String INSERT_TRAVEL_AGENCY =
            " INSERT INTO travel_agency (id,rating,kved,egrpoy_or_rnykpn,is_egrpoy,code_confirmed,address,full_name_director,describe_agency,url_photo,user_id)" +
                    " VALUES (?,?,?,?,?,?,?,?,?,?,?);";

    private static final int TRAVEL_AGENCY_ID = 1;
    private static final int RATING = 2;
    private static final int KVED = 3;
    private static final int EGRPOY_OR_RNYKPN = 4;
    private static final int IS_EGRPOY = 5;
    private static final int CODE_CONFIRMED = 6;
    private static final int ADDRESS = 7;
    private static final int FULL_NAME_DIRECTOR = 8;
    private static final int DESCRIBE_AGENCY = 9;
    private static final int URL_PHOTO = 10;
    private static final int USER_ID = 11;

    private static final boolean CODE_CONFIRMED_DEFAULT_VALUE = true;//I_HOPE_THIS_IS_TRUE_IF_IS_NOT_WE_HAVE_A_PROBLEM

    static boolean travelAgencyToMySqlScript(IConnectorGetter connector, Iterable<TravelAgency> travelAgencies, AtomicLong generatorCustomerIds){
        try(PreparedStatement statement = connector.getSqlPreparedStatement(INSERT_TRAVEL_AGENCY)) {

            for (TravelAgency travelAgency:travelAgencies) {
                travelAgency.setTravelId(generatorCustomerIds.incrementAndGet());
                statement.setLong(TRAVEL_AGENCY_ID,travelAgency.getTravelId());
                statement.setFloat(RATING,travelAgency.getRating());
                statement.setString(KVED,travelAgency.getKved());

                statement.setLong(EGRPOY_OR_RNYKPN,travelAgency.getEgrpoyOrRnekpn());
                statement.setBoolean(IS_EGRPOY,travelAgency.isEgrpoy());
                statement.setBoolean(CODE_CONFIRMED,CODE_CONFIRMED_DEFAULT_VALUE);
                statement.setString(ADDRESS,travelAgency.getAddress());

                statement.setString(FULL_NAME_DIRECTOR,travelAgency.getFullNameDirector());
                statement.setString(DESCRIBE_AGENCY,travelAgency.getDescribeAgency());
                statement.setString(URL_PHOTO,travelAgency.getUrlPhoto());
                statement.setLong(USER_ID,travelAgency.getId());

                return HandlerSqlDAO.arrayHasOnlyOne(statement.executeBatch());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
