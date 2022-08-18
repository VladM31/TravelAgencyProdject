package nure.knt.database.service.implement.users;

import nure.knt.database.idao.core.IDAOCoreSave;
import nure.knt.database.idao.core.IDAODeleteById;
import nure.knt.database.idao.core.IDAOUpdateUserField;
import nure.knt.database.idao.core.IEntityIsBooked;
import nure.knt.database.idao.terms.ITermInformation;
import nure.knt.database.idao.terms.users.ITermUser;
import nure.knt.entity.important.User;

import java.util.List;
import java.util.Optional;

public interface IServiceUser<U extends User,IT extends ITermUser> extends IDAOCoreSave<U>, IDAOUpdateUserField<U>, IDAODeleteById, IEntityIsBooked<U> {
    public Optional<U> findOneBy(ITermInformation information);
    public List<U> findBy(ITermInformation information);

    public IT term();
}
