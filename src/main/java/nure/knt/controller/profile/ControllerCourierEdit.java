package nure.knt.controller.profile;

import nure.knt.entity.important.Courier;
import nure.knt.forms.entities.CourierForm;
import nure.knt.tools.WorkWithCountries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@PropertySource("classpath:WorkerWithCustomer.properties")
public class ControllerCourierEdit {
    @Autowired
    private WorkWithCountries countries;

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
}
