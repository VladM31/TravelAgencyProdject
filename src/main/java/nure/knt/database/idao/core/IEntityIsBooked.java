package nure.knt.database.idao.core;

import nure.knt.entity.important.User;

public interface IEntityIsBooked<U extends User> {
    public boolean isBooked(U user);
}
