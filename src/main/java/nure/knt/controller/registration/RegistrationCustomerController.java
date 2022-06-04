package nure.knt.controller.registration;

import nure.knt.database.idao.registration.IDAOUserRegistration;
import nure.knt.entity.important.Customer;
import nure.knt.forms.signup.CustomerForm;
import nure.knt.gmail.CodeSendler;
import nure.knt.tools.WorkWithCountries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;

@Controller
public class RegistrationCustomerController {
    private IDAOUserRegistration daoRegistration;
    private CodeSendler codeSendler;
    private WorkWithCountries countries;

    private static final String URL = "/customer-registration";
    private static final String URL_CODE = "/customer-check-code";
    private static final String DIRECTORY = "registration/";
    private static final String SIGN_UP_FORM = DIRECTORY + "sign_up_customer_page";
    private static final String CUSTOMER_FORM_ATTRIBUTE = "customerForm";
    private static final String CHECK_CODE_PAGE = "checkOutEmailCodePage";

    private static final String EMAIL_ATTRIBUTE = "email";
    private static final String NAME_ATTRIBUTE = "name";

    @RequestMapping(value = URL,method = {RequestMethod.GET})
    public String showInputForm(Model model){

        this.setFormOnPage(model,new CustomerForm());
        return SIGN_UP_FORM;
    }

    @RequestMapping(value = URL,method = {RequestMethod.POST})
    public String checkInputForm(Model model, @Valid @ModelAttribute(CUSTOMER_FORM_ATTRIBUTE) CustomerForm customerForm, @NotNull  BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            this.setErrorField(model,customerForm,bindingResult);
            return SIGN_UP_FORM;
        }
        if (countries.getIdByCountry(customerForm.getCountry()) == WorkWithCountries.NAME_NOT_FOUND) {
            this.setErrorCountry(model,customerForm);
            return SIGN_UP_FORM;
        }

        Customer customer = customerForm.toCustomer();
        if(daoRegistration.userIsBooked(customer)){
            this.setErrorBooked(model,customerForm);
            return SIGN_UP_FORM;
        }


        final String CODE = daoRegistration.generateCode();

        try {
            daoRegistration.saveForRegistration(customer,CODE);
        } catch (SQLException e) {
            e.printStackTrace();
            this.setErrorBooked(model,customerForm);
            return SIGN_UP_FORM;
        }

        codeSendler.sendCode(customer.getEmail(),customer.getFormatName(),CODE);

        this.setAttributeCheck(model,customer.getFormatName(),customer.getEmail(),URL_CODE,false);

        return CHECK_CODE_PAGE;
    }

    @RequestMapping(value = URL_CODE,method = {RequestMethod.POST})
    public String checkCode(Model model,@ModelAttribute(NAME_ATTRIBUTE) String name,
                            @ModelAttribute(EMAIL_ATTRIBUTE) String email,@ModelAttribute("code")String code){

        Long id = daoRegistration.findUserIdByEmailAndCode(email,code);

        if(id == IDAOUserRegistration.userIdNotFound){
            this.setAttributeCheck(model,name,email,URL_CODE,true);
            return CHECK_CODE_PAGE;
        }

        daoRegistration.saveAsRegistered(id);

        return "redirect:/login";
    }

    private void setFormOnPage(Model model, CustomerForm customerForm){
        model.addAttribute("countries",countries.getCountry());
        model.addAttribute(CUSTOMER_FORM_ATTRIBUTE,customerForm);
    }

    private void setErrorCountry(Model model,CustomerForm customerForm){
        this.setFormOnPage(model,customerForm);
        model.addAttribute("hasCountry","Неправильно введено назву країни");
    }

    private void setErrorField(Model model,CustomerForm customerForm,BindingResult bindingResult){
        this.setFormOnPage(model,customerForm);
        model.addAttribute("fields",bindingResult);
    }

    private void setErrorBooked(Model model,CustomerForm customerForm){
        this.setFormOnPage(model,customerForm);
        model.addAttribute("userIsBooked","Вибачте але почта або номер, або логін вже зайняті");
    }

    private void setAttributeCheck(Model model,String name,String email,String url,boolean error) {
        model.addAttribute("helloUser","Hello " + name + "!!!");
        model.addAttribute("yourEmail","Your email is " + email + ".");
        model.addAttribute("errorCode", error);
        model.addAttribute("userURL",url);

        model.addAttribute(EMAIL_ATTRIBUTE,email);
        model.addAttribute(NAME_ATTRIBUTE,name);
    }

    @Autowired
    @Qualifier("DAO_MySQL_Customer_Registration")
    public void setDaoRegistration(IDAOUserRegistration daoRegistration) {
        this.daoRegistration = daoRegistration;
    }

    @Autowired
    public void setCodeSendler(CodeSendler codeSendler) {
        this.codeSendler = codeSendler;
    }

    @Autowired
    public void setCountries(WorkWithCountries countries) {
        this.countries = countries;
    }

}
