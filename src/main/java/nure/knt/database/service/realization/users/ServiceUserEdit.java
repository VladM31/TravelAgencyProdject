package nure.knt.database.service.realization.users;

import nure.knt.database.idao.core.IDAOCoreEditing;
import nure.knt.database.idao.entity.IDAOUserEdit;
import nure.knt.database.idao.entity.IDAOUserWithTerms;
import nure.knt.database.idao.terms.users.ITermUser;
import nure.knt.database.service.implement.users.IServiceUserEdit;
import nure.knt.entity.important.User;

public class ServiceUserEdit<U extends User, IT extends ITermUser> extends ServiceUser<U,IT> implements IServiceUserEdit<U,IT> {
    protected IDAOCoreEditing<U> edit;

    public ServiceUserEdit(IDAOUserEdit<U, IT> dao) {
        super(dao);
        edit = dao;
    }

    @Override
    public boolean editing(Long id, U entity) {
        return edit.editing(id,entity);
    }

    @Override
    public boolean useEdit(Long id) {
        return edit.useEdit(id);
    }

    @Override
    public boolean cancelEdit(Long id) {
        return edit.cancelEdit(id);
    }
}
