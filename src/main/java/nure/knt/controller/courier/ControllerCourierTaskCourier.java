package nure.knt.controller.courier;

import nure.knt.database.idao.entity.IDAOCourierSQL;
import nure.knt.database.idao.goods.IDAOCourierTask;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.goods.CourierTask;
import nure.knt.entity.important.Courier;
import nure.knt.forms.filter.FilterCourierTaskCourier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.function.Function;

@Controller
@PropertySource("classpath:WorkerWithCourier.properties")
public class ControllerCourierTaskCourier {

    @Autowired
    private IDAOCourierTask <CourierTask> daoCourierTask;
    @Autowired
    private IDAOCourierSQL <Courier> daoCourierSQL;

    private final String PAGE_SHOW_COURIER_TASK;

    public ControllerCourierTaskCourier(@Value("${courier.profile.show.task.page}") String page_show_courier_task) {
        PAGE_SHOW_COURIER_TASK = page_show_courier_task;
    }

    @RequestMapping(value = "${courier.profile.show.task.url}", method = RequestMethod.GET)
    public String showCourierTaskPage(@AuthenticationPrincipal Courier courier, Model model, FilterCourierTaskCourier filter) {
        model.addAttribute("filter",filter);
        model.addAttribute("tasks", filter.filtering(courier.getIdCourier(), daoCourierTask));
        model.addAttribute("user", courier);
        model.addAttribute("chooseColor", HandlerControllerCourierTaskAdmin.ChooseColor);
        return PAGE_SHOW_COURIER_TASK;
    }
}

class HandlerCourierTaskCourier {
    public static Function <ConditionCommodity, Boolean> forButtonCanceled () {
        return (state) -> state.equals(ConditionCommodity.NOT_CONFIRMED) || state.equals(ConditionCommodity.CONFIRMED);
    }

    public static Function <ConditionCommodity, Boolean> forButtonConfirmed () {
        return (state) -> state.equals(ConditionCommodity.NOT_CONFIRMED);
    }

    public static Function <ConditionCommodity, Boolean> forButtonGone () {
        return (state) -> state.equals(ConditionCommodity.CONFIRMED);
    }

}