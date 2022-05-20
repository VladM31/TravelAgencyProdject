package com.example.demo.database.idao.core;

public interface IDAOCoreSave<T> {
    public boolean saveAll(Iterable<T> entities);
    public boolean save(T entity);
}
