package nure.knt.database.idao.core;

public interface IDAOCanUpdate<U> {
    public boolean canUpdate(U origin,U update);
}
