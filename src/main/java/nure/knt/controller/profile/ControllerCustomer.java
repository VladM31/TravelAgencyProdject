package nure.knt.controller.profile;

import nure.knt.controller.HandlerController;
import nure.knt.controller.handlers.HandlerCustomerControllerInformation;
import nure.knt.database.idao.entity.IDAOCustomerSQL;
import nure.knt.database.idao.goods.IDAOOrderFromTourAdCustomer;
import nure.knt.database.idao.goods.IDAOTourAd;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.goods.OrderFromTourAdForCustomer;
import nure.knt.entity.goods.TourAd;
import nure.knt.entity.important.Customer;
import nure.knt.forms.entities.CustomerForm;
import nure.knt.forms.filter.FilterOrdersForCustomer;
import nure.knt.forms.goods.OrderForm;
import nure.knt.tools.WorkWithCountries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.function.Function;

@Controller
@PropertySource("classpath:WorkerWithCustomer.properties")
public class ControllerCustomer {

    @Autowired
    private IDAOOrderFromTourAdCustomer<OrderFromTourAdForCustomer> daoOrder;
    @Autowired
    private IDAOCustomerSQL<Customer> daoCustomer;
    @Autowired
    private WorkWithCountries countries;
    @Autowired
    private IDAOTourAd<TourAd> daoTourAd;

    private final String PAGE_CREATE_ORDER;
    private final String ORDER_PROFILE_URL;

    public ControllerCustomer(@Value("${customer.create.order.page}") String PAGE_CREATE_ORDER,
                              @Value("${customer.profile.order.url}")String ORDER_PROFILE_URL) {
        this.PAGE_CREATE_ORDER = PAGE_CREATE_ORDER;
        this.ORDER_PROFILE_URL = ORDER_PROFILE_URL;
    }

    @RequestMapping(value = "${customer.profile.order.url}",method = {RequestMethod.GET})
    public String showOrderGet(@AuthenticationPrincipal Customer user, Model model, FilterOrdersForCustomer filter){

        model.addAttribute("filter",(filter == null) ? new FilterOrdersForCustomer(): filter);
        model.addAttribute("orders",filter.filtering(user.getCustomerId(),daoOrder));
        this.start(user,model);
        return HandlerCustomerProfile.PAGE_PROFILE_ORDER;
    }

    @RequestMapping(value ="${customer.profile.order.update.state.canceled}",method = {RequestMethod.PATCH})
    public String updateStateOrder(@AuthenticationPrincipal Customer user,@ModelAttribute("orderId") long orderId) {

        if(daoOrder.isThisCustomerOrder(user.getCustomerId(),orderId)){
            daoOrder.updateConditionCommodity(orderId,ConditionCommodity.CANCELED);
        }
        return "redirect:"+ HandlerCustomerControllerInformation.getProfileCustomerOrderUrl();
    }


    @RequestMapping(value = "${customer.profile.edit}",method = {RequestMethod.GET})
    public String showEditPage(@AuthenticationPrincipal Customer user, Model model){
        model.addAttribute("countries",countries.getCountry());
        model.addAttribute(CUSTOMER_FORM_ATTRIBUTE, new CustomerForm().setFieldFromCustomer(user));
        return PAGE_PROFILE_EDIT;
    }

    private static final String ATTRIBUTE_ERROR_MESSAGE = "error_message";
    @RequestMapping(value = "${customer.profile.edit}",method = {RequestMethod.PATCH})
    public String editCustomer(@AuthenticationPrincipal Customer user, Model model, @Valid CustomerForm customerForm,@NotNull BindingResult bindingResult){

        if(setErrors(user, model, customerForm, bindingResult)){
            model.addAttribute("countries",countries.getCountry());
            model.addAttribute(CUSTOMER_FORM_ATTRIBUTE, customerForm);
            return PAGE_PROFILE_EDIT;
        }

        customerForm.setUsername(user.getUsername());
        daoCustomer.updateOneById(customerForm.toCustomer(user));

        return "redirect:/profile-message";
    }

    @RequestMapping(value = "${customer.create.order.from.travel.agency.url}",method = {RequestMethod.GET})
    public String showFormForCreateOrder(Model model,@ModelAttribute("tourAdId") Long tourAdId){

        TourAd chosenTourAd = this.daoTourAd.findByTourAdId(tourAdId,daoTourAd.where());

        OrderForm form = new OrderForm(chosenTourAd);

        HandlerCustomerProfile.setOrderForm(model,form);

        return PAGE_CREATE_ORDER;
    }

    @RequestMapping(value = "${customer.create.order.from.travel.agency.url}",method = {RequestMethod.POST})
    public String checkOrderForm(@AuthenticationPrincipal Customer customer,Model model, @Valid OrderForm form,@NotNull BindingResult bindingResult){

        if(HandlerCustomerProfile.hasErrorOrderForm(model,form,bindingResult)){
            HandlerCustomerProfile.setOrderForm(model,form);
            return PAGE_CREATE_ORDER;
        }
        this.daoOrder.save(form.toOrder(customer.getCustomerId()));
        return "redirect:" + ORDER_PROFILE_URL;
    }

    private void start(Customer user, Model model){
        HandlerCustomerProfile.setNameInPage(model,user.getName().replace('/',' '));
        HandlerCustomerProfile.setColorButton(model);
        HandlerCustomerProfile.setUkraineName(model);
        HandlerController.setMenuModel(user,model);
        HandlerCustomerProfile.setButtonType(model);
        model.addAttribute("countries",countries.getCountry());
    }

    private static final String CUSTOMER_FORM_ATTRIBUTE = "customerForm";
    private static final String PAGE_PROFILE_EDIT ="customer/Customer Edit Page";

    private boolean setErrors(Customer user, Model model, CustomerForm customerForm, BindingResult bindingResult){

        if(HandlerController.setErrors(user,model,customerForm,bindingResult,countries,ATTRIBUTE_ERROR_MESSAGE)){
            return true;
        }

        if(!daoCustomer.canUpdate(user,customerForm.toCustomer())){
            model.addAttribute(ATTRIBUTE_ERROR_MESSAGE,"Номер або пошта зайнята");
            return true;
        }

        return false;
    }
}

class HandlerCustomerProfile{
    private static final String DIRECTORY = "customer/";
    protected static final String PAGE_PROFILE_ORDER = DIRECTORY + "Show all order for customer Page.html";


    private final static String NAME_USER = "nameUser";

    protected static void setNameInPage(Model model, String name){
        model.addAttribute(NAME_USER,name);
    }

    // -----------------------------------------------------------------------------------------
    private static final Function<ConditionCommodity,String> FunctionSetColorButtonDependingState =
            (state) -> state.equals(ConditionCommodity.GONE) || state.equals(ConditionCommodity.CANCELED) ? "order-button-gray"  : "order-button-red";

    private static final String COLOR_FOR_CANCELED_BUTTON = "color_for_canceled_button";

    protected static void setColorButton(Model model){
        model.addAttribute(COLOR_FOR_CANCELED_BUTTON,HandlerCustomerProfile.FunctionSetColorButtonDependingState);
    }
    // -----------------------------------------------------------------------------------------
    private static final String STATE_UKRAINE_NAME = "state_name";

    private static final Function<ConditionCommodity,String> FunctionSetUkraineName = ConditionCommodity::getUkraineName;

    protected static void setUkraineName(Model model){
        model.addAttribute(STATE_UKRAINE_NAME,HandlerCustomerProfile.FunctionSetUkraineName);
    }
    // -----------------------------------------------------------------------------------------
    private static final String TYPE_BUTTON_DEPENDING_STATE_ORDER = "type_button";

    private static final Function<ConditionCommodity,String> FunctionSetTypeButton =
            (state) -> state.equals(ConditionCommodity.GONE) || state.equals(ConditionCommodity.CANCELED) ? "button"  : "submit";

    protected static void setButtonType(Model model){
        model.addAttribute(TYPE_BUTTON_DEPENDING_STATE_ORDER,HandlerCustomerProfile.FunctionSetTypeButton);
    }

    // -----------------------------------------------------------------------------------------
    private static final String ATTRIBUTE_ORDER_FORM = "form";
    protected static void setOrderForm(Model model, OrderForm form){
        model.addAttribute(ATTRIBUTE_ORDER_FORM,form);
    }
    // -----------------------------------------------------------------------------------------
    private static final String ATTRIBUTE_ERROR_ORDER_FORM = "error_message";
    protected static boolean hasErrorOrderForm(Model model, OrderForm form, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.addAttribute(ATTRIBUTE_ERROR_ORDER_FORM,bindingResult.getAllErrors().get(0).getDefaultMessage());
            return true;
        }

        if(form.getStartDateOrder().isAfter(form.getEndDateOrder())){
            model.addAttribute(ATTRIBUTE_ERROR_ORDER_FORM,"Дату вибрано в неправильному порядку");
            return true;
        }
        LocalDate now = LocalDate.now();
        if(now.isAfter(form.getStartDateOrder()) || now.isAfter(form.getEndDateOrder())){
            model.addAttribute(ATTRIBUTE_ERROR_ORDER_FORM,"Дата вибрано поза теперішньою датою");
            return true;
        }

        LocalDate theEnd = form.getConstEndDateOrder().plusDays(1);
        if(form.getStartDateOrder().isAfter(theEnd) || form.getEndDateOrder().isAfter(theEnd)){
            model.addAttribute(ATTRIBUTE_ERROR_ORDER_FORM,"Дата вибрано поза датою закінчення туру");
            return true;
        }


        return false;
    }
}
