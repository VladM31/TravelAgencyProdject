package nure.knt.controller.administrator;

import nure.knt.controller.HandlerController;
import nure.knt.database.idao.entity.IDAOTravelAgencySQL;
import nure.knt.database.idao.registration.IDAOUserRegistrationConfirm;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.important.TravelAgency;
import nure.knt.entity.important.User;
import nure.knt.forms.filter.FilterTravelAgency;
import nure.knt.tools.WorkWithCountries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@PropertySource("classpath:WorkerWithAdministrator.properties")
public class ControllerAdministratorForWorkWithDateTravelAgency {
    @Autowired
    private IDAOTravelAgencySQL<TravelAgency> daoTravelAgency;
    @Autowired
    private WorkWithCountries countries;

    private final String SHOW_REGISTRATION_TRAVEL_AGENCY_PAGE;
    private final String SHOW_REGISTRATION_TRAVEL_AGENCY_URL;


    public ControllerAdministratorForWorkWithDateTravelAgency(@Value("${admin.profile.check.registration.travel.agency.page}") String show_registration_travel_agency_page,
                                                              @Value("${admin.profile.check.registration.travel.agency.url}") String show_registration_travel_agency_url) {
        SHOW_REGISTRATION_TRAVEL_AGENCY_PAGE = show_registration_travel_agency_page;
        SHOW_REGISTRATION_TRAVEL_AGENCY_URL = show_registration_travel_agency_url;
    }

    @RequestMapping(value = "${admin.profile.check.registration.travel.agency.url}",method = RequestMethod.GET)
    public String showRegistrationTravelAgency(@AuthenticationPrincipal User admin, Model model, FilterTravelAgency filter){

        model.addAttribute("agencies",filter.filtering(daoTravelAgency));
        model.addAttribute("filter",filter);
        model.addAttribute("countries",countries.getCountry());
        model.addAttribute("admin",admin);
        HandlerController.setMenuModel(admin,model);

        System.out.println(filter);

        return SHOW_REGISTRATION_TRAVEL_AGENCY_PAGE;
    }
    @RequestMapping(value = "${admin.profile.check.registration.travel.agency.confirm.url}",method = RequestMethod.POST)
    public String confirmTravelAgency(Long idTravelAgency){

        daoTravelAgency.updateTypeStateById(idTravelAgency, TypeState.REGISTERED);

        return "redirect:"+SHOW_REGISTRATION_TRAVEL_AGENCY_URL;
    }

    @RequestMapping(value = "${admin.profile.check.registration.travel.agency.canceled.url}",method = RequestMethod.POST)
    public String canceledTravelAgency(Long idTravelAgency){

        daoTravelAgency.updateCodeConfirmed(false,idTravelAgency);

        return "redirect:"+SHOW_REGISTRATION_TRAVEL_AGENCY_URL;
    }


}
