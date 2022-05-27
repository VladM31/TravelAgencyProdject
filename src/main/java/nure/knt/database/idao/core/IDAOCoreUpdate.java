package nure.knt.database.idao.core;

public interface IDAOCoreUpdate<T> {
    public int[] updateAllById(Iterable<T> entities);
    public int updateOneById(T entity);
}
