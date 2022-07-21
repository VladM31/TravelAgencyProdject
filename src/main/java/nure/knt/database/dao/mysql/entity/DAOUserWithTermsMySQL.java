package nure.knt.database.dao.mysql.entity;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.terms.users.TermUserMySQL;
import nure.knt.database.dao.mysql.tools.MySQLCore;
import nure.knt.database.idao.entity.IDAOUserWithTerms;
import nure.knt.database.idao.terms.ITermInformation;
import nure.knt.database.idao.terms.ITermTourAd;
import nure.knt.database.idao.terms.fieldenum.IUserField;
import nure.knt.database.idao.terms.fieldenum.UserField;
import nure.knt.database.idao.terms.users.ITermUser;
import nure.knt.entity.important.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("Repository_User_MySQL")
@PropertySource("classpath:property/users/UserProperty.properties")
public class DAOUserWithTermsMySQL<U extends User> extends MySQLCore implements IDAOUserWithTerms<U> {

    final protected Map<IUserField,String> ENUM_TO_SCRIPT_MYSQL;

    public DAOUserWithTermsMySQL(Map<IUserField, String> ENUM_TO_SCRIPT_MYSQL) {
        this.ENUM_TO_SCRIPT_MYSQL = ENUM_TO_SCRIPT_MYSQL;
    }

    @Autowired
    public DAOUserWithTermsMySQL(@Value("${dao.users.order.by.enums.properties}") String fileName,
                                 @Value("${dao.terms.users.what.add}") String propertyStart) {
        Map<UserField,String> tempMapWithFiled = HandlerSqlDAO.setNameScriptForEnumsTourAdOrderByValue(fileName,propertyStart, UserField.values());

        Map<IUserField,String> tempMap = new HashMap<>();

        tempMapWithFiled.forEach((k,v) -> tempMap.put(k,v));

        tempMapWithFiled.clear();

        this.ENUM_TO_SCRIPT_MYSQL = Collections.unmodifiableMap(tempMap);
    }

    @Override
    public boolean canUpdate(U origin, U update) {
        return false;
    }

    @Override
    public boolean saveAll(Iterable<U> entities) {
        return false;
    }

    @Override
    public boolean save(U entity) {
        return false;
    }

    @Override
    public int deleteAllById(Iterable<Long> ids) {
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    @Override
    public int[] updateAllById(Iterable<U> entities, IUserField... fields) {
        return new int[0];
    }

    @Override
    public int updateOneById(U entity, IUserField... fields) {
        return 0;
    }

    @Override
    public Optional<U> findOneBy(ITermInformation information) {
        return Optional.empty();
    }

    @Override
    public List<U> findBy(ITermInformation information) {
        return null;
    }

    @Override
    public ITermUser<ITermUser> term() {
        return new TermUserMySQL<>(this.ENUM_TO_SCRIPT_MYSQL);
    }

    @Override
    public boolean userIsBooked(U user) {
        return false;
    }
}
