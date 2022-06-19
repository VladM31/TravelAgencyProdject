package nure.knt.controller.registration;

import nure.knt.database.idao.registration.IDAOUserRegistration;
import nure.knt.entity.important.Customer;
import nure.knt.forms.entities.CustomerForm;
import nure.knt.gmail.CodeSendler;
import nure.knt.tools.WorkWithCountries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    private static final String URL_CODE = "/customer-check-code";
    private static final String DIRECTORY = "registration/";
    private static final String SIGN_UP_FORM = DIRECTORY + "sign_up_customer_page";
    private static final String CUSTOMER_FORM_ATTRIBUTE = "customerForm";
    private final String PAGE_CHECK_CODE;


    public RegistrationCustomerController(@Value("${registration.check.code.page}") String PAGE_CHECK_CODE) {
        this.PAGE_CHECK_CODE = PAGE_CHECK_CODE;
    }

    @RequestMapping(value = "${customer.registration}",method = {RequestMethod.GET})
    public String showInputForm(Model model){

        this.setFormOnPage(model,new CustomerForm());
        return SIGN_UP_FORM;
    }

    @RequestMapping(value = "${customer.registration}",method = {RequestMethod.POST})
    public String checkInputForm(Model model, @Valid @ModelAttribute(CUSTOMER_FORM_ATTRIBUTE) CustomerForm customerForm, @NotNull  BindingResult bindingResult){

        if(checkCustomerForm(model,customerForm,bindingResult)){
            return SIGN_UP_FORM;
        }

        Customer customer = customerForm.toCustomer();

        final String CODE = daoRegistration.generateCode();

        try {
            daoRegistration.saveForRegistration(customer,CODE);
        } catch (SQLException e) {
            e.printStackTrace();
            this.setErrorBooked(model,customerForm);
            return SIGN_UP_FORM;
        }

        codeSendler.sendCode(customer.getEmail(),customer.getFormatName(),CODE);

        HandlerRegistration.setAttributeCheck(model,customer.getFormatName(),customer.getEmail(),URL_CODE,false);

        return PAGE_CHECK_CODE;
    }

    private boolean checkCustomerForm(Model model,CustomerForm customerForm,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            this.setErrorField(model,customerForm,bindingResult);
            return true;
        }
        if (countries.getIdByCountry(customerForm.getCountry()) == WorkWithCountries.NAME_NOT_FOUND) {
            this.setErrorCountry(model,customerForm);
            return true;
        }

        if(daoRegistration.userIsBooked(customerForm.toCustomer())){
            this.setErrorBooked(model,customerForm);
            return true;
        }
        return false;
    }

    @RequestMapping(value = URL_CODE,method = {RequestMethod.POST})
    public String checkCode(Model model,@ModelAttribute(HandlerRegistration.NAME_ATTRIBUTE) String name,
                            @ModelAttribute(HandlerRegistration.EMAIL_ATTRIBUTE) String email,@ModelAttribute("code")String code){

        Long id = daoRegistration.findUserIdByEmailAndCode(email,code);

        if(id == IDAOUserRegistration.userIdNotFound){
            HandlerRegistration.setAttributeCheck(model,name,email,URL_CODE,true);
            return PAGE_CHECK_CODE;
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

    @Autowired
    @Qualifier("DAO_Customer_Registration_MySQL")
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
