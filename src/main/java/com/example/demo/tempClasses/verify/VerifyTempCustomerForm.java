package com.example.demo.tempClasses.verify;

import com.example.demo.database.idao.entity.IDAOCustomerSQL;
import com.example.demo.entity.important.Customer;
import com.example.demo.forms.signup.CustomerForm;
import com.example.demo.tempClasses.verify.verify.inter.IVerifyCustomerForm;
import com.example.demo.tempClasses.verify.verify.inter.IVerifySyntaxErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerifyTempCustomerForm implements IVerifyCustomerForm {

    private IDAOCustomerSQL<Customer> dataCustomer;
    private IVerifySyntaxErrors syntaxErrors;

    @Autowired
    public void setDataCustomer(IDAOCustomerSQL<Customer> dataCustomer) {
        this.dataCustomer = dataCustomer;
    }
    @Autowired
    public void setSyntaxErrors(IVerifySyntaxErrors syntaxErrors) {
        this.syntaxErrors = syntaxErrors;
    }

    @Override
    public String checkOut(CustomerForm cf) {

        if(!cf.hasGender()){
            return "Error:The gender dont choose";
        }
        if (syntaxErrors.hasProblemInUsername(cf.getUsername())){
            return "Error:The username is incorrect";
        }
        if (syntaxErrors.hasProblemInPassword(cf.getPassword())) {
            return "Error:The password is incorrect";
        }
        if (syntaxErrors.hasProblemInEmail(cf.getEmail())){
            return "Error:The email is incorrect";
        }
        if (dataCustomer.findByUsername(cf.getUsername()) != null) {
            return "Error:The username is busy";
        }
        if (dataCustomer.findByEmail(cf.getEmail()) != null) {
            return "Error:The email is busy";
        }

        return "Successful";
    }
}
