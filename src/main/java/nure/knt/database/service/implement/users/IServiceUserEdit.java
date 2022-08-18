package nure.knt.database.service.implement.users;

import nure.knt.database.idao.core.IDAOCoreEditing;
import nure.knt.database.idao.terms.users.ITermUser;
import nure.knt.entity.important.User;

public interface IServiceUserEdit<U extends User,IT extends ITermUser> extends IServiceUser<U,IT>, IDAOCoreEditing<U> {
}
