package com.example.demo.dao.idao;

import java.util.List;

public interface IDAODefault<T> {

   public int deleteAllById(Iterable<Long> ids);
   public int deleteAllByEntity(Iterable<T> entities);
   public int deleteByEntity(T entity);
   public int deleteById(Long id);
   public int deleteAll();

   public List<T> findAll();
   public List<T> findAllById(Iterable<Long> ids);
   public       T findOneById(Long id);

   public int updateAllById(Iterable<T> entities);
   public int updateOneById(T entity);

   public boolean saveAll(Iterable<T> entities);
   public boolean save(T entity);

   public long size();
}
