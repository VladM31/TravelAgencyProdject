package nure.knt.database.idao.core;

public interface IDAOCoreEditing<T> {
    public boolean editing(Long id,T entity);
}
