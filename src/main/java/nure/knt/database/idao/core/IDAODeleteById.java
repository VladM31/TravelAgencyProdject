package nure.knt.database.idao.core;

public interface IDAODeleteById {
    public int deleteAllById(Iterable<Long> ids);
    public int deleteById(Long id);
}
