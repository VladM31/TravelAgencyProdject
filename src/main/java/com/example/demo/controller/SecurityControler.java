package com.example.demo.controller;

import com.example.demo.dao.idao.IDAOCustomer;
import com.example.demo.dao.idao.IDAOTravelAgency;
import com.example.demo.dao.idao.form.IDAOCustomerForm;
import com.example.demo.dao.idao.form.IDAOTravelAgencyForm;
import com.example.demo.entity.Customer;
import com.example.demo.entity.TravelAgency;
import com.example.demo.entity.User;
import com.example.demo.forms.ChooseSignUpForm;
import com.example.demo.forms.CustomerForm;
import com.example.demo.forms.TravelAgencyForm;
import com.example.demo.tempClasses.verify.VerifyTempTravelAgencyForm;
import com.example.demo.verify.inter.IVerifyCustomerForm;
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
    @Autowired
    private IDAOCustomer<Customer> daoCustomer;
    @Autowired
    private IDAOTravelAgency<TravelAgency> daoTravelAgency;
    @Autowired
    private IVerifyCustomerForm chechCustForm;
    @Autowired
    private VerifyTempTravelAgencyForm checkTravAgenForm;

    @Autowired
    private IDAOCustomerForm daoCustForm;
    @Autowired
    private IDAOTravelAgencyForm daoTravAgenForm;

    // ************* Головна сторінка *****************
    @RequestMapping(value = { "/", "/mainWindow","/hello" }, method = { RequestMethod.GET, RequestMethod.POST })
    public String showMainWindow(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            model.addAttribute("home", true);
            this.setMenuModel(user,model);
        }
        return "mainWindowPage";
    }

    // ************* Вхід і вихід *****************
    @RequestMapping(value = { "/login"}, method = { RequestMethod.GET })
    public String signInGet(Model model) {
        return "login";
    }

    @RequestMapping(value = { "/logout"}, method = { RequestMethod.GET })
    public String signOutGet(@AuthenticationPrincipal User user,Model model) {
        this.setMenuModel(user,model);
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

        this.daoCustForm.saveForm(form);

        modelEmail.addAttribute("email", form.getEmail());

        return "redirect:/confirm.mail.customer";
    }

    // ************* Реєстрація агенції *****************
    @RequestMapping(value= {"/sign_up_error_travel"},method = { RequestMethod.POST })
    public String signUpTravelAgencyPOST(RedirectAttributes modelEmail,Model model, TravelAgencyForm form) {
        if(!checkTravAgenForm.checkOut(form).equals("Successful")) {
            model.addAttribute("customer", form.getErrorForm());
            return "sign_up_customerPage";
        }

        this.daoTravAgenForm.saveForm(form);
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

    private void setMenuModel(User user,Model model) {
        model.addAttribute("sign_in", true);
        if (user instanceof Customer) {
            model.addAttribute("name", ((Customer) user).getFirstName());
        } else if (user instanceof TravelAgency) {
            model.addAttribute("name", ((TravelAgency) user).getNameTravelAgency());
        }
    }


}
