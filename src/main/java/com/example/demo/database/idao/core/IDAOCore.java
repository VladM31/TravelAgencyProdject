package com.example.demo.database.idao.core;

import java.util.List;

public interface IDAOCore<T>  extends IDAOCoreSave<T>,IDAOCoreUpdate<T>,IDAOCoreDelete<T>{

   public List<T> findAll();
   public List<T> findAllById(Iterable<Long> ids);
   public       T findOneById(Long id);

   public long size();
}
