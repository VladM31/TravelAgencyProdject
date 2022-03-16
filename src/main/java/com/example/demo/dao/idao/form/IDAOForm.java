package com.example.demo.dao.idao.form;

public interface IDAOForm {
    public Long getCode(String email);

    public String getEmail(long code);

    public String getName(String email);
}