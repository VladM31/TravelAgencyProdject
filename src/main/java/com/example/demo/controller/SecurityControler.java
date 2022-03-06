package com.example.demo.controller;

import com.example.demo.Test;
import com.example.demo.entity.User;
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


}
