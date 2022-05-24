package nure.knt.controller;

import nure.knt.database.idao.entity.IDAOTravelAgencySQL;
import nure.knt.entity.enums.Role;
import nure.knt.entity.important.TravelAgency;
import nure.knt.entity.important.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class MainWindows {
    // ************* Головна сторінка *****************
    private static final String DIRECTORY = "main_pages/";

    @Autowired
    private IDAOTravelAgencySQL<TravelAgency> dapTravelAgency;

    private static final String MAIN_PAGE = DIRECTORY + "Main Page";

    private static final String ATTRIBUTE_TRAVEL_AGENCIES = "travelAgencies";
    @RequestMapping(value = { "/", "/mainWindow"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String showMainWindow(@AuthenticationPrincipal User user, Model model) {

        model.addAttribute(ATTRIBUTE_TRAVEL_AGENCIES,HandlerMainWindows.getNodes(dapTravelAgency.findAllAndLimit(3)));
        if (user != null) {
            HandlerMainWindows.setMenuModel(user,model);
        }

        return MAIN_PAGE;
    }

    private static final String PAGE_HELLO = DIRECTORY + "Hello Page";

    @RequestMapping(value = { "/hello" }, method = { RequestMethod.GET, RequestMethod.POST })
    public String showHelloWindow(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            model.addAttribute("home", true);
            HandlerMainWindows.setMenuModel(user,model);
        }

        return PAGE_HELLO;
    }


}

class HandlerMainWindows{
    public static void setMenuModel(User user,Model model) {
        model.addAttribute("sign_in", true);
        model.addAttribute("name",user.getRole().equals(Role.CUSTOMER) ? user.getName().replace('/',' ') : user.getName());
    }

    public static String[] styles = {"Popular-1 color-first","Popular-1 color-second","Popular-1 color-third"};

    public static NodeForMainPage[] getNodes(List<TravelAgency> travelAgencies){
        NodeForMainPage[] nodes = new NodeForMainPage[travelAgencies.size()];

        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new NodeForMainPage(travelAgencies.get(i),styles[i],TravelAgency.getRetingStars(travelAgencies.get(i).getRating()));

        }

        return nodes;
    }
}

class NodeForMainPage{
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