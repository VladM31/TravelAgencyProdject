package com.example.demo.dao.idao.form;

import com.example.demo.forms.TravelAgencyForm;

public interface IDAOTravelAgencyForm extends IDAOForm{
    public TravelAgencyForm getForm(String email, long code);
    public boolean saveForm(TravelAgencyForm form);
}
