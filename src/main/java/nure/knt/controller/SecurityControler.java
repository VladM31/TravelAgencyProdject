package nure.knt.controller;

import nure.knt.database.idao.entity.IDAOCustomerSQL;
import nure.knt.database.idao.entity.IDAOTravelAgencySQL;
import nure.knt.database.idao.temporary.IDAOTravelAgencyTemporaryCode;
import nure.knt.entity.important.Customer;
import nure.knt.entity.important.TravelAgency;
import nure.knt.entity.important.User;
import nure.knt.forms.entities.ChooseSignUpForm;
import nure.knt.forms.entities.TravelAgencyForm;
import nure.knt.tempClasses.verify.VerifyTempTravelAgencyForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class SecurityControler {
    //--------------------------------------------------
    @Autowired
    private IDAOCustomerSQL<Customer> daoCustomer;
    @Autowired
    private IDAOTravelAgencySQL<TravelAgency> daoTravelAgency;
    //--------------------------------------------------

    @Autowired
    private IDAOTravelAgencyTemporaryCode idaoTravelAgencyTemporaryCode;
    //--------------------------------------------------
    @Autowired
    private VerifyTempTravelAgencyForm checkTravAgenForm;

    //--------------------------------------------------




    // ************* Вхід і вихід *****************
    @RequestMapping(value = { "/login"}, method = { RequestMethod.GET })
    public String signInGet(Model model) {
        return "login";
    }

    @RequestMapping(value = { "/logout"}, method = { RequestMethod.GET })
    public String signOutGet(@AuthenticationPrincipal User user,Model model) {
        HandlerController.setMenuModel(user,model);
        return "logout";
    }

    // ************* Вибір реєстрації *****************

    @RequestMapping(value= {"${registration.choose.sign.up}"},method = { RequestMethod.GET,RequestMethod.POST })
    public String showSignUpPage(Model model) {
        return "registration/Choose registration Page";
    }

    @RequestMapping(value= {"/sign_up"},method = { RequestMethod.GET })
    public String signUpGet(Model model) {
        model.addAttribute("setSignUp",new ChooseSignUpForm(null,false,true));
        return "choose_sign_upPage";
    }

    @RequestMapping(value= {"/sign_up"},method = { RequestMethod.POST })
    public String signUpPOST(Model model,ChooseSignUpForm form) {
        if (form.isEmpty()) {
            model.addAttribute("setSignUp", new ChooseSignUpForm(null, true, false));
            return "choose_sign_upPage";
        }

        if (form.isCustomer()) {
            return "redirect:/free/customer-registration";
        }else{
            model.addAttribute("travel",new TravelAgencyForm());
            return "sign_up_travel_agencyPage";
        }
    }


    // ************* Реєстрація агенції *****************
    @RequestMapping(value= {"/sign_up_error_travel"},method = { RequestMethod.POST })
    public String signUpTravelAgencyPOST(RedirectAttributes modelEmail,Model model, TravelAgencyForm form) {

        String checkOut = checkTravAgenForm.checkOut(form);
        if(!checkOut.equals("Successful")) {
            System.out.println(checkOut);
            model.addAttribute("travel", form.getErrorForm());
            return "sign_up_travel_agencyPage";
        }

        this.idaoTravelAgencyTemporaryCode.save(form.toTravelAgencyTemporary());
        modelEmail.addAttribute("email", form.getEmail());
        return "redirect:/confirm.mail.travel.agency";
    }


    @ResponseBody
    @RequestMapping("/all")
    public String allCustomer() {
        String allc = "";
        for (User user: this.daoCustomer.findAll()) {
            allc+= "<p>"+ user + "</p>";
        }
        for (User user: this.daoTravelAgency.findAll()) {
            allc+= "<p>"+ user + "</p>";
        }
        return allc;
    }




}


