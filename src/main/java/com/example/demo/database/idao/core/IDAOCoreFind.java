package com.example.demo.database.idao.core;

import org.springframework.lang.Nullable;

import java.util.List;

public interface IDAOCoreFind<T> {
    public List<T> findAll();
    public List<T> findAllById(Iterable<Long> ids);
    @Nullable
    public       T findOneById(Long id);
}
