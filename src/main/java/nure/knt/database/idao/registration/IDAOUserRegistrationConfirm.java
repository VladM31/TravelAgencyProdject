package nure.knt.database.idao.registration;

import nure.knt.entity.important.User;

public interface IDAOUserRegistrationConfirm<U extends User>{
    public boolean saveForConfirmation(U user);
}
