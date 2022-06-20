package nure.knt.database.idao.core;

public interface IDAOCoreEditing<T> {
    public boolean editing(Long id,T entity);

    public default boolean saveEdit(Long id){
        return false;
    }

    public default boolean removeEdit(Long id){
        return false;
    }

}
