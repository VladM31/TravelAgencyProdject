package nure.knt.controller;

import nure.knt.database.idao.entity.IDAOTravelAgencySQL;
import nure.knt.database.idao.goods.IDAOTourAd;
import nure.knt.entity.goods.TourAd;
import nure.knt.entity.important.TravelAgency;
import nure.knt.entity.important.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@PropertySource("classpath:MainPages.properties")
@PropertySource("classpath:customer.properties")
public class MainWindowsController {

    private final String PAGE_TOUR_ADS;
    private final String REDIRECT_ON_CHECKOUT_CUSTOMER;
    private static final String MAIN_PAGE =  "main_pages/Main Page";

    @Autowired
    public MainWindowsController(@Value("${main.pages.find.all.name.page}") String PAGE_TOUR_ADS,
                                 @Value("${main.pages.find.all.url}") String REDIRECT_ON_CHECKOUT_CUSTOMER) {
        this.PAGE_TOUR_ADS = PAGE_TOUR_ADS;
        this.REDIRECT_ON_CHECKOUT_CUSTOMER = REDIRECT_ON_CHECKOUT_CUSTOMER;
    }

    // ************* Головна сторінка *****************

    @Autowired
    private IDAOTravelAgencySQL<TravelAgency> dapTravelAgency;
    @Autowired
    private IDAOTourAd<TourAd> daoTourAd;


    private static final String ATTRIBUTE_TRAVEL_AGENCIES = "travelAgencies";
    @RequestMapping(value = { "/", "/mainWindow"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String showMainWindow(@AuthenticationPrincipal User user, Model model) {

        model.addAttribute(ATTRIBUTE_TRAVEL_AGENCIES,HandlerMainWindows.getNodes(dapTravelAgency.findAllAndLimit(3)));
        if (user != null) {
            HandlerController.setMenuModel(user,model);
        }

        return MAIN_PAGE;
    }

    private static final String PAGE_HELLO = "main_pages/Hello Page";

    @RequestMapping(value = { "/hello" }, method = { RequestMethod.GET, RequestMethod.POST })
    public String showHelloWindow(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            model.addAttribute("home", true);
            HandlerController.setMenuModel(user,model);
        }

        return PAGE_HELLO;
    }

    @RequestMapping(value = "${main.pages.find.all.url}", method = {RequestMethod.GET})
    public String showPageForFindAllTourAd(Model model){

        model.addAttribute("tourAds",daoTourAd.findAll(daoTourAd.where()));
        return PAGE_TOUR_ADS;
    }

}

class HandlerMainWindows{


    public static String[] styles = {"Popular-1 color-first","Popular-1 color-second","Popular-1 color-third"};

    public static NodeForMainPage[] getNodes(List<TravelAgency> travelAgencies){
        NodeForMainPage[] nodes = new NodeForMainPage[travelAgencies.size()];

        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new NodeForMainPage(travelAgencies.get(i),styles[i],TravelAgency.getRetingStars(travelAgencies.get(i).getRating()));

        }

        return nodes;
    }

    public static class NodeForMainPage{
        private TravelAgency travelAgency;
        private String style;
        private List<String> stars;

        public NodeForMainPage(TravelAgency travelAgency, String color, List<String> stars) {
            this.travelAgency = travelAgency;
            this.style = color;
            this.stars = stars;
        }

        public NodeForMainPage() {
        }

        public TravelAgency getTravelAgency() {
            return travelAgency;
        }

        public void setTravelAgencies(TravelAgency travelAgency) {
            this.travelAgency = travelAgency;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public List<String> getStars() {
            return stars;
        }

        public void setStars(List<String> stars) {
            this.stars = stars;
        }

    }

}