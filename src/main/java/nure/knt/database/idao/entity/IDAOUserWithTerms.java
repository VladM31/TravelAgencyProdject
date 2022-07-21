package nure.knt.database.idao.entity;

import nure.knt.database.idao.core.*;
import nure.knt.database.idao.terms.ITermInformation;
import nure.knt.database.idao.terms.users.ITermUser;
import nure.knt.entity.important.User;

import java.util.List;
import java.util.Optional;

public interface IDAOUserWithTerms<U extends User,IT extends ITermUser> extends IDAOCoreSave<U>, IDAOUpdateUserField<U>, IDAODeleteById, IEntityIsBooked<U> {

    public Optional<U> findOneBy(ITermInformation information);
    public List<U> findBy(ITermInformation information);

    public IT term();

    public static final Long userIdNotFound = null;


}
