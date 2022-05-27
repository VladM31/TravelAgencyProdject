package nure.knt.database.idao.registration;

import nure.knt.entity.important.User;
import org.springframework.lang.Nullable;

import java.sql.SQLException;

public interface IDAOUserRegistration<U extends User>  {

    public static final Long userIdNotFound = null;

    public boolean userIsBooked(U user);

    public String generateCode();

    @Nullable
    public Long findUserIdByEmailAndCode(String email,String code);

    public boolean saveForRegistration(U user,String code) throws SQLException;
    public boolean saveAsRegistered(Long id);
}
