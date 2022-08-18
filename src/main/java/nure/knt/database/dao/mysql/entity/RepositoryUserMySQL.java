package nure.knt.database.dao.mysql.entity;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.terms.users.TermUserMySQL;
import nure.knt.database.idao.entity.IDAOUserWithTerms;
import nure.knt.database.idao.factory.IFactoryEntity;
import nure.knt.database.idao.terms.ITermInformation;
import nure.knt.database.idao.terms.fieldenum.IUserField;
import nure.knt.database.idao.terms.fieldenum.UserField;
import nure.knt.database.idao.terms.users.ITermUser;
import nure.knt.entity.important.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Function;

@Repository("Repository_User_MySQL")
@PropertySource("classpath:property/users/UserProperty.properties")
public class RepositoryUserMySQL extends MySQLUserCore<User> implements IDAOUserWithTerms<User,ITermUser> {
    @Autowired
    @Qualifier("Factory_User_MySQL")
    private IFactoryEntity<User> factory;
    final private Map<IUserField,String> ENUM_TO_SCRIPT_MYSQL;
    final private Map<IUserField, Function<User,Object>> mapUserGettersValueByField;

    @Autowired
    public RepositoryUserMySQL(@Qualifier("Get_Object_By_Field_For_User") Map<IUserField, Function<User,Object>> mapUserGettersValueByField,
                               @Qualifier("Get_Name_By_Field_For_User") Map<IUserField,String> nameUserFieldInDataBase) {
        this.ENUM_TO_SCRIPT_MYSQL = nameUserFieldInDataBase;
        this.mapUserGettersValueByField = mapUserGettersValueByField;
    }

    @Override
    public boolean saveAll(Iterable<User> entities) {
        return HandlerUser.saveUsersAndReturnsNewIds(super.conn,entities,super.getterId);
    }

    @Override
    public boolean save(User entity) {
        return this.saveAll(List.of(entity));
    }

    private static final String UPDATE_USER = "UPDATE user SET %s WHERE id = ? ;";
    @Override
    public int[] updateAllById(Iterable<User> entities, IUserField... fields) {
        if (HandlerUser.areNotNormalFields(fields, UserField.ID)){
            return HandlerSqlDAO.ERROR_UPDATE;
        }

        return HandlerSqlDAO.updateByFieldAndId(super.conn,String.format(UPDATE_USER,
                        HandlerSqlDAO.getScript(ENUM_TO_SCRIPT_MYSQL,fields," = ? ",", ")),
                entities,mapUserGettersValueByField,fields,UserField.ID);
    }

    @Override
    public int updateOneById(User entity, IUserField... fields) {
        return updateAllById(List.of(entity),fields)[0];
    }

    @Override
    public Optional<User> findOneBy(ITermInformation information) {
        return Optional.ofNullable(
                HandlerSqlDAO.useSelectScriptAndGetOneObject(super.conn,
                        HandlerSqlDAO.toScriptDefault(SELECT_AND_PARAMETERS_FOR_USER,FROM_AND_JOIN,information,super.concatScripts),
                        factory::getEntity,
                        information.getParameters()
                )
        );
    }

    @Override
    public List<User> findBy(ITermInformation information) {
        return HandlerSqlDAO.useSelectScript(super.conn,
                        HandlerSqlDAO.toScriptDefault(SELECT_AND_PARAMETERS_FOR_USER,FROM_AND_JOIN,information,super.concatScripts),
                        factory::getEntity,
                        information.getParameters()
                );
    }

    @Override
    public ITermUser<ITermUser> term() {
        return new TermUserMySQL<>(this.ENUM_TO_SCRIPT_MYSQL);
    }

    private static final String FROM_AND_JOIN =
            "FROM user " +
                    "\nLEFT JOIN country ON user.country_id = country.id  " +
                    "\nLEFT JOIN role ON user.role_id = role.id  " +
                    "\nLEFT JOIN type_state ON user.type_state_id = type_state.id \n";
}
