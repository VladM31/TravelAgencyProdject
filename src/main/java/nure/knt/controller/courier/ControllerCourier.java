package nure.knt.controller.courier;


import nure.knt.database.idao.goods.IDAOCourierTask;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.goods.CourierTask;
import nure.knt.entity.important.User;
import nure.knt.forms.filter.FilterCourierTaskAdmin;
import nure.knt.tools.WorkWithCountries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.function.Function;

@Controller
public class ControllerCourier {
    private final String PAGE_NAME_COURIER_TASKS = "courier_directory/show_task";

    @Autowired
    private WorkWithCountries countries;

    @Autowired
    private IDAOCourierTask<CourierTask> daoTask;

    private final String ATTRIBUTE_TASKS = "tasks";
    private final String ATTRIBUTE_COLOR = "ChooseColor";
    private final String ATTRIBUTE_ROLE_ADMIN = "RoleAdmin";

    @RequestMapping(value = "/show_task")
    String showTasks(@AuthenticationPrincipal User user, Model model) {
        this.setInfoForTasks(user, model);
        return PAGE_NAME_COURIER_TASKS;
    }

    @RequestMapping(value = "${admin.profile.show.courier.task}", method = {RequestMethod.GET})
    public String showOrderGet(@AuthenticationPrincipal User user, Model model, FilterCourierTaskAdmin filter) {
        System.out.println(filter);

        model.addAttribute("filter", (filter == null) ? new FilterCourierTaskAdmin() : filter);
        model.addAttribute(ATTRIBUTE_TASKS, filter.filtering(user.getId(), daoTask));
        this.setInfoForTasks(user, model);
        return HandlerControllerCourier.PAGE_PROFILE_ORDER;
    }

    private void setInfoForTasks(User user, Model model) {

        model.addAttribute(ATTRIBUTE_COLOR, HandlerControllerCourier.ChooseColor);
        model.addAttribute(ATTRIBUTE_ROLE_ADMIN, true);
        model.addAttribute("countries", countries.getCountry());
    }
}

class HandlerControllerCourier {
    private static final String DIRECTORY = "courier_directory/";
    protected static final String PAGE_PROFILE_ORDER = DIRECTORY + "show_task";

    protected static final Function<ConditionCommodity, String> ChooseColor = (c) -> {
        switch (c) {
            case GONE -> {
                return " color-gray";
            }
            case CANCELED -> {
                return " color-red";
            }
            case CONFIRMED -> {
                return " color-green";
            }
            case NOT_CONFIRMED -> {
                return " color-yellow";
            }
            default -> {
                return "";
            }
        }
    };

    ////////////////////////////////////////////////////////////////////


}
