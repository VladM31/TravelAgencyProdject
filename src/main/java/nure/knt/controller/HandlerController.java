package nure.knt.controller;

import nure.knt.entity.enums.Role;
import nure.knt.entity.important.User;
import org.springframework.ui.Model;

public class HandlerController {
    public static void setMenuModel(User user, Model model) {
        model.addAttribute("sign_in", true);
        model.addAttribute("name",user.getRole().equals(Role.CUSTOMER) ? user.getName().replace('/',' ') : user.getName());
    }
}
