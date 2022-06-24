package nure.knt.controller.profile;

import nure.knt.controller.HandlerController;
import nure.knt.database.idao.entity.IDAOCourierSQL;
import nure.knt.entity.important.Courier;
import nure.knt.entity.important.Customer;
import nure.knt.forms.entities.CourierForm;
import nure.knt.forms.entities.CustomerForm;
import nure.knt.tools.WorkWithCountries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller
@PropertySource("classpath:WorkerWithCustomer.properties")
public class ControllerCourierEdit {
    @Autowired
    private WorkWithCountries countries;
    @Autowired
    private IDAOCourierSQL<Courier> daoCourierSQL;

    private final String EDIT_COURIER_PAGE;
    private final String EDIT_COURIER_URL;

    public ControllerCourierEdit(@Value("${courier.profile.edit.page}") String edit_courier_page,
                                 @Value("${courier.profile.edit.url}") String edit_courier_url) {
        EDIT_COURIER_PAGE = edit_courier_page;
        EDIT_COURIER_URL = edit_courier_url;
    }

    @RequestMapping(value = "${courier.profile.edit.url}", method = RequestMethod.GET)
    public String shopPageForEdit(@AuthenticationPrincipal Courier courier, Model model){

        model.addAttribute("countries",courier.getCountry());
        model.addAttribute("courier_form", new CourierForm(courier));
        return EDIT_COURIER_PAGE;
    }


    @RequestMapping(value = "${courier.profile.edit.url}",method = {RequestMethod.PATCH})
    public String editCustomer(@AuthenticationPrincipal Courier courier, Model model, @Valid CourierForm courierForm, @NotNull BindingResult bindingResult){

        if(setErrors(courier, model, courierForm, bindingResult)){
            model.addAttribute("countries",countries.getCountry());
            model.addAttribute("courier_form", courierForm);
            return EDIT_COURIER_PAGE;
        }

        courierForm.setUsername(courier.getUsername());
        daoCourierSQL.updateOneById(courierForm.toCourier(courier));

        return "redirect:/profile-message";
    }

    private static final String ATTRIBUTE_ERROR_MESSAGE = "error_message";

    private boolean setErrors(Courier courier, Model model, CourierForm courierForm, BindingResult bindingResult){

        if(HandlerController.setErrors(courier,model,courierForm,bindingResult,countries,ATTRIBUTE_ERROR_MESSAGE)){
            return true;
        }

        if(!daoCourierSQL.canUpdate(courier,courierForm.toCourier())){
            model.addAttribute(ATTRIBUTE_ERROR_MESSAGE,"Номер або пошта зайнята");
            return true;
        }

        return false;
    }
}
