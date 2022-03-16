package com.example.demo.tempClasses.dao.form;

import com.example.demo.dao.idao.form.IDAOCustomerForm;
import com.example.demo.forms.CustomerForm;
import com.example.demo.tempClasses.dao.form.DAOForm;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class DAOCustomerForm extends DAOForm implements IDAOCustomerForm {

    static HashMap<String,CustomerForm> mapDataForm = new HashMap<>();

    @Override
    public CustomerForm getForm(String email, long code) {
        synchronized (mapData){
            if(mapData.get(email) != code) {
                return null;
            }
        }
        synchronized(mapDataForm){
            return mapDataForm.get(email);
        }
    }

    @Override
    public boolean saveForm(CustomerForm form) {
        synchronized (mapData){
            mapData.put(form.getEmail(),ran.nextLong(minimumRangeNumber,maximumRangeNumber));
        }
        synchronized(mapDataForm){
            mapDataForm.put(form.getEmail(),form);
        }
        return true;
    }

    @Override
    public String getName(String email) {
        if (mapDataForm.get(email) == null) {
            return null;
        }
        return mapDataForm.get(email).getName();
    }
}
