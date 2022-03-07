package com.example.demo.controller;

import com.example.demo.Test;
import com.example.demo.entity.User;
import com.example.demo.forms.ChooseSignUpForm;
import com.example.demo.forms.CustomerForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class SecurityControler {

    @RequestMapping(value = { "/", "/mainWindow" }, method = { RequestMethod.GET, RequestMethod.POST })
    public String showMainWindow(Model model) {

        return "mainWindowPage";
    }

    @RequestMapping(value = { "/login"}, method = { RequestMethod.GET })
    public String signInGet(Model model)
    {


        model.addAttribute("tes", new Test(false,true));
        model.addAttribute("user",new User());
        System.out.println("signInGet");
        return "login";
    }
    @RequestMapping(value = { "/login"}, method = { RequestMethod.POST })
    public String signInPOST(Model model, User user)
    {
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        return "mainWindowPage";
    }

    @RequestMapping(value= {"/sign_up"},method = { RequestMethod.GET })
    public String signUpGet(Model model)
    {
        model.addAttribute("setSignUp",new ChooseSignUpForm(null,false,true));

        return "choose_sign_upPage";
    }

    @RequestMapping(value= {"/sign_up"},method = { RequestMethod.POST })
    public String signUpPOST(Model model,ChooseSignUpForm form)
    {
        if (form.isEmpty()) {
            model.addAttribute("setSignUp", new ChooseSignUpForm(null, true, false));
            return "choose_sign_upPage";
        }
        if (form.isCustomer()) {
            model.addAttribute("customer",new CustomerForm());
            return "sign_up_customerPage";
        }
        return "redirect:/mainWindow";
    }

    @RequestMapping(value= {"/sign_up_error"},method = { RequestMethod.POST })
    public String signUpCustomerPOST(Model model, CustomerForm form)
    {
        System.out.println(form);
        return "redirect:/login";
    }
}
