package nure.knt.controller.registration;


import nure.knt.database.idao.registration.IDAOUserRegistration;
import nure.knt.entity.important.Courier;
import nure.knt.forms.entities.CourierForm;
import nure.knt.forms.entities.CustomerForm;
import nure.knt.tools.WorkWithCountries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Autowired
    @Qualifier("DAO_Courier_Registration_MySQL")
    private IDAOUserRegistration<Courier> daoRegistration;

    private final String PAGE_COURIER_REGISTRATION;
    private final String ATTRIBUTE_ERROR_VALID;

    public RegistrationCourierController(@Value("${registration.courier.page}") String PAGE_COURIER_REGISTRATION,
                                         @Value("${registration.courier.error}") String ATTRIBUTE_ERROR_VALID) {
        this.PAGE_COURIER_REGISTRATION = PAGE_COURIER_REGISTRATION;
        this.ATTRIBUTE_ERROR_VALID = ATTRIBUTE_ERROR_VALID;
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

        System.out.println(courierForm.toCourier());

        if(HandlerRegistration.hasError(model,courierForm,bindingResult,countries,
                daoRegistration,ATTRIBUTE_ERROR_VALID,courierForm.toCourier()," ")){
           model.addAttribute("error",bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
        return PAGE_COURIER_REGISTRATION;
    }

}
