package nure.knt.controller.registration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegistrationCustomerController {
    private static final String URL = "";

    @RequestMapping(value = URL,method = {RequestMethod.GET})
    public String showInputForm(){
        return null;
    }

    @RequestMapping(value = URL,method = {RequestMethod.POST})
    public String checkInputForm(Model model){
        return null;
    }

    @RequestMapping(value = URL,method = {RequestMethod.GET})
    public String checkCode(Model model){
        return null;
    }
}
