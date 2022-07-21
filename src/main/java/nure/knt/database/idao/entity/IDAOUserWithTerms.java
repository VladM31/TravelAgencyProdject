package nure.knt.database.idao.entity;

import nure.knt.database.idao.core.IDAOCanUpdate;
import nure.knt.database.idao.core.IDAOCoreSave;
import nure.knt.database.idao.core.IDAODeleteById;
import nure.knt.database.idao.core.IDAOUpdateUserField;
import nure.knt.database.idao.terms.ITermInformation;
import nure.knt.database.idao.terms.users.ITermUser;
import nure.knt.entity.important.User;

import java.util.List;
import java.util.Optional;

public interface IDAOUserWithTerms<U extends User> extends IDAOCoreSave<U>, IDAOUpdateUserField<U>, IDAOCanUpdate<U>, IDAODeleteById {

    public Optional<U> findOneBy(ITermInformation information);
    public List<U> findBy(ITermInformation information);

    public ITermUser<ITermUser> term();

    public static final Long userIdNotFound = null;

    public boolean userIsBooked(U user);
}
