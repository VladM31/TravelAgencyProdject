package nure.knt.controller.registration;


import nure.knt.database.idao.registration.IDAOUserRegistration;
import nure.knt.entity.important.Courier;
import nure.knt.forms.entities.CourierForm;
import nure.knt.forms.entities.CustomerForm;
import nure.knt.gmail.CodeSendler;
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
import java.sql.SQLException;


@Controller
@PropertySource("classpath:WorkerWithCourier.properties")
public class RegistrationCourierController {
    @Autowired
    private WorkWithCountries countries;
    @Autowired
    @Qualifier("DAO_Courier_Registration_MySQL")
    private IDAOUserRegistration<Courier> daoRegistration;
    @Autowired
    private CodeSendler codeSendler;

    private final String PAGE_COURIER_REGISTRATION;
    private final String ATTRIBUTE_ERROR_VALID;
    private final String URL_CODE;
    private final String PAGE_CHECK_CODE;

    public RegistrationCourierController(@Value("${registration.courier.page}") String PAGE_COURIER_REGISTRATION,
                                         @Value("${registration.courier.error}") String ATTRIBUTE_ERROR_VALID,
                                         @Value("${registration.courier.check.code.url}") String URL_CODE,
                                         @Value("${registration.check.code.page}") String PAGE_CHECK_CODE) {
        this.PAGE_COURIER_REGISTRATION = PAGE_COURIER_REGISTRATION;
        this.ATTRIBUTE_ERROR_VALID = ATTRIBUTE_ERROR_VALID;
        this.URL_CODE = URL_CODE;
        this.PAGE_CHECK_CODE = PAGE_CHECK_CODE;
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

        if(HandlerRegistration.hasError(model,courierForm,bindingResult,countries,
                daoRegistration,ATTRIBUTE_ERROR_VALID,courierForm.toCourier()," ")){
            this.setAttributes(model,courierForm);
            return PAGE_COURIER_REGISTRATION;
        }

        Courier courier = courierForm.toCourier();

        final String CODE = daoRegistration.generateCode();

        try {
            daoRegistration.saveForRegistration(courier,CODE);
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute(ATTRIBUTE_ERROR_VALID,"Помилка на сайті, зареєструйтесь з іншою інформацією або спробуйте пізніше");
            return PAGE_COURIER_REGISTRATION;
        }

        codeSendler.sendCode(courier.getEmail(),courier.getName(),CODE);

        HandlerRegistration.setAttributeCheck(model,courier.getName(),courier.getEmail(),URL_CODE,false);

        return PAGE_CHECK_CODE;
    }

    @RequestMapping(value = "${registration.courier.check.code.url}",method = {RequestMethod.POST})
    public String checkCode(Model model,@ModelAttribute(HandlerRegistration.NAME_ATTRIBUTE) String name,
                            @ModelAttribute(HandlerRegistration.EMAIL_ATTRIBUTE) String email,@ModelAttribute("code")String code){

        Long id = daoRegistration.findUserIdByEmailAndCode(email,code);

        if(id == IDAOUserRegistration.userIdNotFound){
            HandlerRegistration.setAttributeCheck(model,name,email,URL_CODE,true);
            return PAGE_CHECK_CODE;
        }

        daoRegistration.saveAsRegistered(id);

        return "redirect:/login";
    }

}
