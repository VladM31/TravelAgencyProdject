package nure.knt.controller.registration;


import nure.knt.forms.entities.CourierForm;
import nure.knt.forms.entities.CustomerForm;
import nure.knt.tools.WorkWithCountries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Controller
@PropertySource("classpath:WorkerWithCourier.properties")
public class RegistrationCourierController {

    @Autowired
private WorkWithCountries countries;

    private final String PAGE_COURIER_REGISTRATION;

    public RegistrationCourierController(@Value("${registration.courier.page}") String PAGE_COURIER_REGISTRATION) {
        this.PAGE_COURIER_REGISTRATION = PAGE_COURIER_REGISTRATION;
    }

    @RequestMapping(value = "${registration.courier.url}",method = RequestMethod.GET)
    public String showInputPage(Model model){
        this.setAttributes(model, new CourierForm());
        return PAGE_COURIER_REGISTRATION;
    }

    private void setAttributes(Model model, CourierForm courierForm){
        model.addAttribute("countries",countries.getCountry());
        model.addAttribute("courierForm", courierForm);
    }


    @RequestMapping(value = "${registration.courier.url}",method = RequestMethod.POST)
    public String checkInformation(Model model,@Valid CourierForm courierForm,@NotNull BindingResult bindingResult){
        System.out.println(courierForm);
        this.setAttributes(model,courierForm);

        if(bindingResult.hasErrors()){
           model.addAttribute("error",bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
        return PAGE_COURIER_REGISTRATION;
    }

}
