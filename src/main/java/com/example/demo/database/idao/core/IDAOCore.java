package com.example.demo.database.idao.core;

import org.springframework.lang.Nullable;

import java.util.List;

public interface IDAOCore<T>  extends IDAOCoreSave<T>,IDAOCoreUpdate<T>,IDAOCoreDelete<T>{

   public List<T> findAll();
   public List<T> findAllById(Iterable<Long> ids);
   @Nullable
   public       T findOneById(Long id);

   public long size();
}
