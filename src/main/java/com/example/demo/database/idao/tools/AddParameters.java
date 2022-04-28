package com.example.demo.database.idao.tools;

import com.example.demo.entity.important.User;

import java.sql.PreparedStatement;

@FunctionalInterface
public interface AddParameters {
    public void extraOptions(PreparedStatement preStat);
}
