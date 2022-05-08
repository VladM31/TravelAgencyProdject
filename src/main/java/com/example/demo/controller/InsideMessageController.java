package com.example.demo.controller;

import com.example.demo.database.idao.temporary.IDAOMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller(value = "/message")
public class InsideMessageController {
    private static final String DIRECTORY = "place_for_messages\\";
    private static final String SHOW_ALL_MESSAGES_PAGE = DIRECTORY + "show_all_messages.html";

    @Autowired
    private IDAOMessage idaoMessage;

    @RequestMapping(value = "/profile")
    public String showProfileMessageGet(Model model){

        return SHOW_ALL_MESSAGES_PAGE;
    }

}
