package nure.knt.controller;

import nure.knt.database.idao.entity.IDAOCustomerSQL;
import nure.knt.database.idao.entity.IDAOTravelAgencySQL;
import nure.knt.database.idao.temporary.IDAOCustomerTemporary;
import nure.knt.database.idao.temporary.IDAOTravelAgencyTemporaryCode;
import nure.knt.entity.important.Customer;
import nure.knt.entity.enums.Role;
import nure.knt.entity.important.TravelAgency;
import nure.knt.entity.important.User;
import nure.knt.forms.signup.ChooseSignUpForm;
import nure.knt.forms.signup.CustomerForm;
import nure.knt.forms.signup.TravelAgencyForm;
import nure.knt.tempClasses.verify.VerifyTempTravelAgencyForm;
import nure.knt.tempClasses.verify.verify.inter.IVerifyCustomerForm;
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
    private IDAOCustomerTemporary daoCustTemp;
    @Autowired
    private IDAOTravelAgencyTemporaryCode idaoTravelAgencyTemporaryCode;
    //--------------------------------------------------
    @Autowired
    private IVerifyCustomerForm chechCustForm;
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
        HandlerMainWindows.setMenuModel(user,model);
        return "logout";
    }

    // ************* Вибір реєстрації *****************
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
            model.addAttribute("customer",new CustomerForm());
            return "sign_up_customerPage";
        }else{
            model.addAttribute("travel",new TravelAgencyForm());
            return "sign_up_travel_agencyPage";
        }
    }

    // ************* Реєстрація користувача *****************
    @RequestMapping(value= {"/sign_up_error_customer"},method = { RequestMethod.POST })
    public String signUpCustomerPOST(RedirectAttributes modelEmail,Model model, CustomerForm form) {
        if(!chechCustForm.checkOut(form).equals("Successful")) {
            model.addAttribute("customer", form.getErrorForm());
            return "sign_up_customerPage";
        }
       // System.out.println(form.toCustomerTemporary());
        this.daoCustTemp.save(form.toCustomerTemporary());

        modelEmail.addAttribute("email", form.getEmail());

        return "redirect:/confirm.mail.customer";
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


