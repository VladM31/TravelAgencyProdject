package nure.knt.database.idao.core;

public interface IDAOCoreEditing<T> {
    public boolean editing(Long id,T entity);

    public default boolean useEdit(Long id){
        return false;
    }

    public default boolean cancelEdit(Long id){
        return false;
    }

}
