package com.example.demo.dao.idao.form;

import com.example.demo.forms.CustomerForm;

public interface IDAOCustomerForm extends IDAOForm {

    public CustomerForm getForm(String email,long code);
    public boolean saveForm(CustomerForm form);
}
