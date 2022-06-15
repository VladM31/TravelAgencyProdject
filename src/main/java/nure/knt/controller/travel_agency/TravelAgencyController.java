package nure.knt.controller.travel_agency;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TravelAgencyController {

    @RequestMapping(value = "/dog")
    @ResponseBody()
    public String showAllTours(){
        return "<b>Hello</b>";
    }


}
