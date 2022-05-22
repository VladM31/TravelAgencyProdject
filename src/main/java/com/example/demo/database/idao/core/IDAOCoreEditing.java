package com.example.demo.database.idao.core;

public interface IDAOCoreEditing<T> {
    public boolean editing(T entity);
    public boolean saveForEditing(T entity);
}
