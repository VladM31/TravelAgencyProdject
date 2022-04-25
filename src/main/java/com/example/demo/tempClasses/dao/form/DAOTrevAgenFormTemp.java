package com.example.demo.tempClasses.dao.form;

import com.example.demo.dao.idao.form.IDAOTravelAgencyForm;
import com.example.demo.forms.signup.TravelAgencyForm;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class DAOTrevAgenFormTemp extends DAOForm implements IDAOTravelAgencyForm {

    static HashMap<String, TravelAgencyForm> mapDataForm = new HashMap<>();

    @Override
    public String getName(String email) {
        if (mapDataForm.get(email) == null) {
            return null;
        }
        return mapDataForm.get(email).getNameTravelAgency();
    }

    @Override
    public TravelAgencyForm getForm(String email, long code) {
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
    public boolean saveForm(TravelAgencyForm form) {
        synchronized (mapData){
            mapData.put(form.getEmail(),ran.nextLong(minimumRangeNumber,maximumRangeNumber));
        }
        synchronized(mapDataForm){
            mapDataForm.put(form.getEmail(),form);
        }
        return true;
    }
}
