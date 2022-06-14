package nure.knt.controller.registration;

import nure.knt.database.idao.registration.IDAOUserRegistrationConfirm;
import nure.knt.entity.important.TravelAgency;
import nure.knt.forms.entities.CustomerForm;
import nure.knt.forms.entities.TravelAgencyForm;
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

@Controller
@PropertySource("classpath:WorkerTravelAgency.properties")
public class RegistrationTravelAgencyController {
    @Autowired
    private IDAOUserRegistrationConfirm<TravelAgency> daoAgency;
    @Autowired
    private CodeSendler codeSendler;
    @Autowired
    private WorkWithCountries countries;

    private final String URL;
    private final String URL_CODE;
    private final String PAGE_SIGN_UP;
    private final String ATTRIBUTE_TRAVEL_AGENCY_FORM;

    public RegistrationTravelAgencyController(@Value("${registration.travel.agency.url}") String URL,
                                              @Value("${registration.travel.agency.url.check.code}") String URL_CODE,
                                              @Value("${registration.travel.agency.page.sign.up}") String PAGE_SIGN_UP,
                                              @Value("${registration.travel.agency.form}") String ATTRIBUTE_TRAVEL_AGENCY_FORM){
        this.URL = URL;
        this.URL_CODE = URL_CODE;
        this.PAGE_SIGN_UP = PAGE_SIGN_UP;
        this.ATTRIBUTE_TRAVEL_AGENCY_FORM =ATTRIBUTE_TRAVEL_AGENCY_FORM;

        System.out.println(URL);
        System.out.println(URL_CODE);
        System.out.println(PAGE_SIGN_UP);
        System.out.println(ATTRIBUTE_TRAVEL_AGENCY_FORM);
    }

    @RequestMapping(value = "${registration.travel.agency.url}",method = {RequestMethod.GET})
    public String showInputForm(Model model){

        model.addAttribute(ATTRIBUTE_TRAVEL_AGENCY_FORM,new TravelAgencyForm());
        return PAGE_SIGN_UP;
    }

    @RequestMapping(value = "${registration.travel.agency.url}",method = {RequestMethod.PATCH})
    public String checkInputForm(Model model, @Valid @ModelAttribute("nameTravelAgency") TravelAgencyForm travelAgencyForm, @NotNull BindingResult bindingResult){

        model.addAttribute(ATTRIBUTE_TRAVEL_AGENCY_FORM,travelAgencyForm);
        System.out.println(travelAgencyForm);
        return PAGE_SIGN_UP;
    }

}
