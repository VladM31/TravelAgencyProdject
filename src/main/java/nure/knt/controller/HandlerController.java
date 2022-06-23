package nure.knt.controller;

import nure.knt.entity.enums.Role;
import nure.knt.entity.important.Customer;
import nure.knt.entity.important.User;
import nure.knt.forms.entities.CustomerForm;
import nure.knt.tools.WorkWithCountries;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.validation.constraints.NotNull;

public class HandlerController {
    public static void setMenuModel(User user, Model model) {
        model.addAttribute("sign_in", true);
        model.addAttribute("name",user.getRole().equals(Role.CUSTOMER) ? user.getName().replace('/',' ') : user.getName());
    }
}
