package nure.knt.controller.registration;

import nure.knt.database.idao.registration.IDAOUserRegistration;
import nure.knt.database.idao.registration.IDAOUserRegistrationConfirm;
import nure.knt.entity.important.TravelAgency;
import nure.knt.forms.entities.CustomerForm;
import nure.knt.forms.entities.TravelAgencyForm;
import nure.knt.forms.entities.UserForm;
import nure.knt.gmail.CodeSendler;
import nure.knt.tools.WorkWithCountries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
@PropertySource("classpath:WorkerTravelAgency.properties")
public class RegistrationTravelAgencyController {
    @Autowired
    private IDAOUserRegistrationConfirm<TravelAgency> daoRegistrationAgency;
    @Autowired
    private CodeSendler codeSendler;
    @Autowired
    private WorkWithCountries countries;

    private final String URL;
    private final String URL_CODE;
    private final String PAGE_CHECK_CODE;
    private final String PAGE_SIGN_UP;
    private final String ATTRIBUTE_TRAVEL_AGENCY_FORM;
    private final String ATTRIBUTE_ERROR;

    public RegistrationTravelAgencyController(@Value("${registration.travel.agency.url}") String URL,
                                              @Value("${registration.travel.agency.url.check.code}") String URL_CODE,
                                              @Value("${registration.travel.agency.page.sign.up}") String PAGE_SIGN_UP,
                                              @Value("${registration.check.code.page}") String PAGE_CHECK_CODE,
                                              @Value("${registration.travel.agency.form}") String ATTRIBUTE_TRAVEL_AGENCY_FORM,
                                              @Value("${registration.travel.agency.error}") String ATTRIBUTE_ERROR){
        this.URL = URL;
        this.URL_CODE = URL_CODE;
        this.PAGE_SIGN_UP = PAGE_SIGN_UP;
        this.ATTRIBUTE_TRAVEL_AGENCY_FORM =ATTRIBUTE_TRAVEL_AGENCY_FORM;
        this.ATTRIBUTE_ERROR = ATTRIBUTE_ERROR;
        this.PAGE_CHECK_CODE = PAGE_CHECK_CODE;
    }

    @RequestMapping(value = "${registration.travel.agency.url}",method = {RequestMethod.GET})
    public String showInputForm(Model model){

        model.addAttribute(ATTRIBUTE_TRAVEL_AGENCY_FORM,new TravelAgencyForm());
        model.addAttribute("countries",countries.getCountry());
        return PAGE_SIGN_UP;
    }

    @RequestMapping(value = "${registration.travel.agency.url}",method = {RequestMethod.PATCH})
    public String checkInputForm(Model model, @Valid @ModelAttribute("nameTravelAgency") TravelAgencyForm travelAgencyForm, @NotNull BindingResult bindingResult){

        if(Handler.hasError(model,travelAgencyForm,bindingResult,countries,daoRegistrationAgency,ATTRIBUTE_ERROR)){
            Handler.setInformationInPage(model,travelAgencyForm,countries,ATTRIBUTE_TRAVEL_AGENCY_FORM);
            return PAGE_SIGN_UP;
        }

        TravelAgency travelAgency = travelAgencyForm.toTravelAgency();

        final String CODE = daoRegistrationAgency.generateCode();

        try {
            daoRegistrationAgency.saveForRegistration(travelAgency,CODE);
        } catch (SQLException e) {
            e.printStackTrace();
            Handler.setInformationInPage(model,travelAgencyForm,countries,ATTRIBUTE_TRAVEL_AGENCY_FORM);
            model.addAttribute(ATTRIBUTE_ERROR,"Номер телефону або логін або пошта або ЕГРПОУ або РНУКПН зайнято");
            return PAGE_SIGN_UP;
        }

        codeSendler.sendCode(travelAgency.getEmail(),travelAgency.getName(),CODE);
        HandlerRegistration.setAttributeCheck(model,travelAgency.getName(),travelAgency.getEmail(),URL_CODE,false);

        return PAGE_CHECK_CODE;
    }

    @RequestMapping(value = "${registration.travel.agency.url.check.code}",method = {RequestMethod.POST})
    public String checkCode(Model model,@ModelAttribute(HandlerRegistration.NAME_ATTRIBUTE) String name,
                            @ModelAttribute(HandlerRegistration.EMAIL_ATTRIBUTE) String email,@ModelAttribute("code")String code){

        Long id = daoRegistrationAgency.findUserIdByEmailAndCode(email,code);

        if(id == IDAOUserRegistration.userIdNotFound){
            HandlerRegistration.setAttributeCheck(model,name,email,URL_CODE,true);
            return PAGE_CHECK_CODE;
        }

        daoRegistrationAgency.saveForConfirmation(id);

        return "redirect:/login";
    }

}

class Handler{

    protected static boolean hasError(Model model, TravelAgencyForm travelAgencyForm,BindingResult bindingResult,
                                      WorkWithCountries countries,IDAOUserRegistrationConfirm<TravelAgency> daoRegistration,final String ATTRIBUTE_ERROR){
        if(bindingResult.hasErrors()){
            model.addAttribute(ATTRIBUTE_ERROR,bindingResult.getAllErrors().get(0).getDefaultMessage());
            return true;
        }
        if (countries.getIdByCountry(travelAgencyForm.getCountry()) == WorkWithCountries.NAME_NOT_FOUND) {
            model.addAttribute(ATTRIBUTE_ERROR,"Країна не знайдена");
            return true;
        }

        if(daoRegistration.userIsBooked(travelAgencyForm.toTravelAgency())){
            model.addAttribute(ATTRIBUTE_ERROR,"Номер телефону або логін або пошта або ЕГРПОУ або РНУКПН зайнято");
            return true;
        }
        return false;
    }

    protected static void setInformationInPage(Model model, TravelAgencyForm travelAgencyForm,WorkWithCountries countries,String nameAttribute){
        model.addAttribute("countries",countries.getCountry());
        model.addAttribute(nameAttribute,travelAgencyForm);
    }
}
