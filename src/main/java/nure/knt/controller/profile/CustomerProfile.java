package nure.knt.controller.profile;

import nure.knt.controller.HandlerController;
import nure.knt.database.idao.entity.IDAOCustomerSQL;
import nure.knt.database.idao.goods.IDAOOrderFromTourAdCustomer;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.goods.OrderFromTourAdForCustomer;
import nure.knt.entity.important.Customer;
import nure.knt.entity.important.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.function.Function;

@Controller
public class CustomerProfile {

    @Autowired
    private IDAOOrderFromTourAdCustomer<OrderFromTourAdForCustomer> daoOrder;

    @RequestMapping(value = "${customer.profile.order.url}",method = {RequestMethod.GET})
    public String showOrderGet(@AuthenticationPrincipal Customer user, Model model){

       this.start(user,model);
        return HandlerCustomerProfile.PAGE_PROFILE_ORDER;
    }

    public void start(Customer user, Model model){
        HandlerCustomerProfile.setNameInPage(model,user.getName().replace('/',' '));
        model.addAttribute("orders",daoOrder.findAllById(user.getCustomerId()));
        HandlerCustomerProfile.setColorButton(model);
        HandlerCustomerProfile.setUkraineName(model);
        HandlerController.setMenuModel(user,model);
    }
}

class HandlerCustomerProfile{
    private static final String DIRECTORY = "customer/";
    protected static final String PAGE_PROFILE_ORDER = DIRECTORY + "Show all order for customer Page.html";

    private final static String NAME_USER = "nameUser";

    protected static void setNameInPage(Model model, String name){
        model.addAttribute(NAME_USER,name);
    }

    private static final Function<ConditionCommodity,String> FunctionSetColorButtonDependingState =
            (state) -> state.equals(ConditionCommodity.GONE) ? "order-button-gray"  : "order-button-red";

    private static final String COLOR_FOR_CANCELED_BUTTON = "color_for_canceled_button";

    protected static void setColorButton(Model model){
        model.addAttribute(COLOR_FOR_CANCELED_BUTTON,HandlerCustomerProfile.FunctionSetColorButtonDependingState);
    }

    private static final String STATE_UKRAINE_NAME = "state_name";

    private static final Function<ConditionCommodity,String> FunctionSetUkraineName = ConditionCommodity::getUkraineName;

    protected static void setUkraineName(Model model){
        model.addAttribute(STATE_UKRAINE_NAME,HandlerCustomerProfile.FunctionSetUkraineName);
    }
}
