package nure.knt.controller.travel_agency;


import nure.knt.controller.HandlerController;
import nure.knt.database.idao.goods.IDAOTourAd;
import nure.knt.entity.goods.TourAd;
import nure.knt.entity.important.TravelAgency;
import nure.knt.forms.filter.FilterOrdersForTravelAgency;
import nure.knt.forms.filter.FilterTourAdProfileTravelAgency;
import nure.knt.tools.WorkWithCountries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TravelAgencyController {

    private final String PAGE_SHOW_ALL_TOURS;
    private final String URL_SHOW_ALL_TOURS;
    @Autowired
    private WorkWithCountries countries;
    @Autowired
    private IDAOTourAd<TourAd> daoTourAd;


    public TravelAgencyController(@Value("${travel.agency.profile.show.tour.ads.page}") String PAGE_SHOW_ALL_TOURS,
                                  @Value("${travel.agency.profile.show.tour.ads.url}") String URL_SHOW_ALL_TOURS) {
        this.PAGE_SHOW_ALL_TOURS = PAGE_SHOW_ALL_TOURS;
        this.URL_SHOW_ALL_TOURS = URL_SHOW_ALL_TOURS;
    }

    @RequestMapping(value = "${travel.agency.profile.show.tour.ads.url}", method = RequestMethod.GET)
    public String showAllTours(@AuthenticationPrincipal TravelAgency travelAgency, Model model, FilterTourAdProfileTravelAgency filter) {
        final String PAGE = PAGE_SHOW_ALL_TOURS;
        model.addAttribute("tourAds", filter.filtering(daoTourAd.where().idTravelAgencyIs(travelAgency.getTravelId()), daoTourAd));

        model.addAttribute("filter", filter);
        model.addAttribute("countries", countries.getCountry());
        HandlerController.setMenuModel(travelAgency, model);
        model.addAttribute("travelAgency",travelAgency);


        return PAGE;
    }


}
