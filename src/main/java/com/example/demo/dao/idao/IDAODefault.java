package com.example.demo.dao.idao;

import java.util.List;

public interface IDAODefault<T> {

    void deleteAllById(Iterable<Long> ids);
    void deleteAllByEntity(Iterable<T> entities);
    void deleteByEntity(T entity);
    void deleteById(Long id);
    void deleteAll();


    List<T> findAll();
    List<T> findAllById(Iterable<Long> ids);
          T findOneById(Long id);

    boolean addAll(Iterable<T> entities);
    boolean add(T entity);

    boolean saveAll(Iterable<T> entities);
    boolean save(T entity);
}
