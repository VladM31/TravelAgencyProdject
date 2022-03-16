package com.example.demo.tempClasses.dao.form;

import com.example.demo.dao.idao.form.IDAOForm;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class DAOForm implements IDAOForm {
    static Random ran = new Random();
    static HashMap<String,Long> mapData = new HashMap<>();
    static long minimumRangeNumber = 10000000;
    static long maximumRangeNumber = 99999999;

    @Override
    public Long getCode(String email) {
        return mapData.get(email);
    }

    @Override
    public String getEmail(long code) {
        for(Map.Entry<String,Long> entity : mapData.entrySet()) {
            if (entity.getValue() == code) {
                return entity.getKey();
            }
        }
        return null;
    }
}
