package nure.knt.tempClasses.verify;

import nure.knt.database.idao.entity.IDAOCustomerSQL;
import nure.knt.entity.important.Customer;
import nure.knt.forms.entities.CustomerForm;
import nure.knt.tempClasses.verify.verify.inter.IVerifyCustomerForm;
import nure.knt.tempClasses.verify.verify.inter.IVerifySyntaxErrors;
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

        return "Successful";
    }
}
