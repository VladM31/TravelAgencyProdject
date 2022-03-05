package com.example.demo.dao.idao;

import java.util.List;

public interface IDAODefault<T> {

    int deleteAllById(Iterable<Long> ids);
    int deleteAllByEntity(Iterable<T> entities);
    int deleteByEntity(T entity);
    int deleteById(Long id);
    int deleteAll();

    List<T> findAll();
    List<T> findAllById(Iterable<Long> ids);
          T findOneById(Long id);

    boolean addAll(Iterable<T> entities);
    boolean add(T entity);

    int updateAllById(Iterable<T> entities);
    int updateOneById(T entity);

    boolean saveAll(Iterable<T> entities);
    boolean save(T entity);
}
