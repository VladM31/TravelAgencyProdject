package com.example.demo.tempClasses.verify;

import com.example.demo.dao.idao.IDAOCustomer;
import com.example.demo.dao.idao.IDAOTravelAgency;
import com.example.demo.entity.Customer;
import com.example.demo.entity.TravelAgency;
import com.example.demo.forms.CustomerForm;
import com.example.demo.verify.inter.IVerifyCustomerForm;
import com.example.demo.verify.inter.IVerifySyntaxErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerifyTempCustomerForm implements IVerifyCustomerForm {

    @Autowired
    private IDAOCustomer<Customer> dataCustomer;
    @Autowired
    private IVerifySyntaxErrors syntaxErrors;

    @Override
    public String checkOut(CustomerForm cf) {

        if (syntaxErrors.checkOutUsername(cf.getUsername())){
            return "Error:The username is incorrect";
        }
        if (syntaxErrors.checkOutPassword(cf.getPassword())) {
            return "Error:The password is incorrect";
        }
        if (syntaxErrors.checkOutEmail(cf.getEmail())){
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
