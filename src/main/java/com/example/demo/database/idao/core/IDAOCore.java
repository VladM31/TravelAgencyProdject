package com.example.demo.database.idao.core;

import org.springframework.lang.Nullable;

import java.util.List;

public interface IDAOCore<T>  extends IDAOCoreSave<T>,IDAOCoreUpdate<T>,IDAOCoreDelete<T>,IDAOCoreFind<T>{



   public long size();
}
