package nure.knt.controller.registration;

import nure.knt.database.idao.registration.IDAOUserRegistration;
import nure.knt.entity.important.User;
import nure.knt.forms.entities.UserForm;
import nure.knt.tools.WorkWithCountries;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public class HandlerRegistration {
    public static final String EMAIL_ATTRIBUTE = "email";
    public static final String NAME_ATTRIBUTE = "name";

    public static void setAttributeCheck(Model model, String name, String email, String url, boolean error) {
        model.addAttribute("errorCode", error);
        model.addAttribute("userURL",url);

        model.addAttribute(EMAIL_ATTRIBUTE,email);
        model.addAttribute(NAME_ATTRIBUTE,name);
    }

    public static <Entity extends User,Form extends UserForm> boolean hasError(Model model, Form from, BindingResult bindingResult,
                                                                                  WorkWithCountries countries, IDAOUserRegistration<Entity> daoRegistration,
                                                                                  final String ATTRIBUTE_ERROR, Entity entity, String dopScript){
        if(bindingResult.hasErrors()){
            model.addAttribute(ATTRIBUTE_ERROR,bindingResult.getAllErrors().get(0).getDefaultMessage());
            return true;
        }
        if (countries.getIdByCountry(from.getCountry()) == WorkWithCountries.NAME_NOT_FOUND) {
            model.addAttribute(ATTRIBUTE_ERROR,"Країна не знайдена");
            return true;
        }

        if(daoRegistration.userIsBooked(entity)){
            model.addAttribute(ATTRIBUTE_ERROR,"Номер телефону або логін або пошта " +dopScript +" зайнято");
            return true;
        }
        return false;
    }
}
