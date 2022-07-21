package nure.knt.database.idao.core;

import nure.knt.database.idao.terms.fieldenum.IUserField;

public interface IDAOUpdateUserField<T> {
    public int[] updateAllById(Iterable<T> entities, IUserField ...fields);
    public int updateOneById(T entity,IUserField ...fields);
}
