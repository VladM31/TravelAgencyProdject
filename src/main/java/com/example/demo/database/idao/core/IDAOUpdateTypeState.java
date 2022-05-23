package com.example.demo.database.idao.core;

import com.example.demo.entity.enums.TypeState;

public interface IDAOUpdateTypeState {
    public boolean updateTypeStateById(Long id, TypeState typeState);
}
