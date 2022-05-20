package com.example.demo.database.idao.core;

public interface IDAOCoreDelete<T> {
    public int deleteAllById(Iterable<Long> ids);
    public int deleteAllByEntity(Iterable<T> entities);
    public int deleteByEntity(T entity);
    public int deleteById(Long id);
    public int deleteAll();
}
