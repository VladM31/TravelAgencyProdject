package nure.knt.controller;

import nure.knt.entity.enums.Role;
import nure.knt.entity.important.Courier;
import nure.knt.entity.important.Customer;
import nure.knt.entity.important.User;
import nure.knt.forms.entities.CourierForm;
import nure.knt.forms.entities.CustomerForm;
import nure.knt.forms.entities.UserForm;
import nure.knt.tools.WorkWithCountries;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.validation.constraints.NotNull;

public class HandlerController {
    public static void setMenuModel(User user, Model model) {
        model.addAttribute("sign_in", true);
        model.addAttribute("name",user.getRole().equals(Role.CUSTOMER) ? user.getName().replace('/',' ') : user.getName());
    }

    public static boolean setErrors(User user, Model model, UserForm userForm, BindingResult bindingResult, WorkWithCountries countries, final String ATTRIBUTE_ERROR_MESSAGE){

        if(bindingResult.hasErrors()){
            model.addAttribute(ATTRIBUTE_ERROR_MESSAGE,bindingResult.getAllErrors().get(0).getDefaultMessage());
            return true;
        }

        if(countries.getIdByCountry(userForm.getCountry()) == WorkWithCountries.NAME_NOT_FOUND){
            model.addAttribute(ATTRIBUTE_ERROR_MESSAGE,"Країна не знайдена або введена не правильно");
            return true;
        }

        return false;
    }
}
