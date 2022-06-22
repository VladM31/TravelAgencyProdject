package nure.knt.controller.travel_agency;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TravelAgencyController {

    private final String PAGE_SHOW_ALL_TOURS;
    private final String URL_SHOW_ALL_TOURS;

    public TravelAgencyController(@Value("${travel.agency.profile.show.tour.ads.page}")String PAGE_SHOW_ALL_TOURS,
                                  @Value("${travel.agency.profile.show.tour.ads.url}")String URL_SHOW_ALL_TOURS) {
        this.PAGE_SHOW_ALL_TOURS = PAGE_SHOW_ALL_TOURS;
        this.URL_SHOW_ALL_TOURS = URL_SHOW_ALL_TOURS;
    }

    @RequestMapping(value = "${travel.agency.profile.show.tour.ads.url}",method = RequestMethod.GET)
    public String showAllTours() {
        final String PAGE = PAGE_SHOW_ALL_TOURS;




        return PAGE;
    }



}
