package nure.knt.controller.administrator;

import nure.knt.database.idao.entity.IDAOTravelAgencySQL;
import nure.knt.entity.important.TravelAgency;
import nure.knt.forms.filter.FilterTravelAgency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@PropertySource("classpath:WorkerWithAdministrator.properties")
public class ControllerAdministratorForWorkWithDateTravelAgency {
    @Autowired
    private IDAOTravelAgencySQL<TravelAgency> daoTravelAgency;

    private final String SHOW_REGISTRATION_TRAVEL_AGENCY_PAGE;
    private final String SHOW_REGISTRATION_TRAVEL_AGENCY_URL;


    public ControllerAdministratorForWorkWithDateTravelAgency(@Value("${admin.profile.check.registration.travel.agency.page}") String show_registration_travel_agency_page,
                                                              @Value("${admin.profile.check.registration.travel.agency.url}") String show_registration_travel_agency_url) {
        SHOW_REGISTRATION_TRAVEL_AGENCY_PAGE = show_registration_travel_agency_page;
        SHOW_REGISTRATION_TRAVEL_AGENCY_URL = show_registration_travel_agency_url;
    }

    @RequestMapping(value = "${admin.profile.check.registration.travel.agency.url}",method = RequestMethod.GET)
    public String showRegistrationTravelAgency(Model model, FilterTravelAgency filter){

        model.addAttribute("agencies",filter.filtering(daoTravelAgency));

        return SHOW_REGISTRATION_TRAVEL_AGENCY_PAGE;
    }
}
