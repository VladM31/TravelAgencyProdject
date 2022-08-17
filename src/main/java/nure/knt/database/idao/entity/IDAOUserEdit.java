package nure.knt.database.idao.entity;

import nure.knt.database.idao.core.IDAOCoreEditing;
import nure.knt.database.idao.terms.users.ITermUser;
import nure.knt.entity.important.User;

public interface IDAOUserEdit<U extends User,IT extends ITermUser> extends  IDAOUserWithTerms<U,IT>, IDAOCoreEditing<U> {
}
