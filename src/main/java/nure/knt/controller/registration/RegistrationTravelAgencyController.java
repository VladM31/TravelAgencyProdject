package nure.knt.controller.registration;

import nure.knt.database.idao.registration.IDAOUserRegistrationConfirm;
import nure.knt.entity.important.TravelAgency;
import nure.knt.forms.entities.CustomerForm;
import nure.knt.gmail.CodeSendler;
import nure.knt.tools.WorkWithCountries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller


@PropertySource("classpath:WorkerTravelAgency.properties")

public class RegistrationTravelAgencyController {
    @Autowired
    private IDAOUserRegistrationConfirm<TravelAgency> daoAgency;
    @Autowired
    private CodeSendler codeSendler;
    @Autowired
    private WorkWithCountries countries;
    private final String URL_CODE;
    private final String PAGE_SIGN_UP;

    public RegistrationTravelAgencyController(@Value("${registration.travel.agency.url.check.code}") String URL_CODE,
                                              @Value("${registration.travel.agency.page.sign.up}") String PAGE_SIGN_UP){
        this.URL_CODE = URL_CODE;
        this.PAGE_SIGN_UP = PAGE_SIGN_UP;

        System.out.println(URL_CODE);
        System.out.println(PAGE_SIGN_UP);
    }

    @RequestMapping(value = "${registration.travel.agency.url.check.code}",method = {RequestMethod.GET})
    public String showInputForm(Model model){

        //this.setFormOnPage(model,new CustomerForm());
        return PAGE_SIGN_UP;
    }

}
