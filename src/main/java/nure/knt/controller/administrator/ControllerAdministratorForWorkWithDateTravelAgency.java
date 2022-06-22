package nure.knt.controller.administrator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@PropertySource("classpath:WorkerWithAdministrator.properties")
public class ControllerAdministratorForWorkWithDateTravelAgency {

    private final String SHOW_REGISTRATION_TRAVEL_AGENCY_PAGE;
    private final String SHOW_REGISTRATION_TRAVEL_AGENCY_URL;


    public ControllerAdministratorForWorkWithDateTravelAgency(@Value("${admin.profile.check.registration.travel.agency.page}") String show_registration_travel_agency_page,
                                                              @Value("${admin.profile.check.registration.travel.agency.url}") String show_registration_travel_agency_url) {
        SHOW_REGISTRATION_TRAVEL_AGENCY_PAGE = show_registration_travel_agency_page;
        SHOW_REGISTRATION_TRAVEL_AGENCY_URL = show_registration_travel_agency_url;
    }

    @RequestMapping(value = "${admin.profile.check.registration.travel.agency.url}",method = RequestMethod.GET)
    public String showRegistrationTravelAgency(){
        return SHOW_REGISTRATION_TRAVEL_AGENCY_PAGE;
    }
}
