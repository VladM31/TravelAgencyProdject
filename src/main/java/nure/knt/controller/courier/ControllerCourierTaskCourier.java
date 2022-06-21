package nure.knt.controller.courier;

import nure.knt.controller.HandlerController;
import nure.knt.database.idao.entity.IDAOCourierSQL;
import nure.knt.database.idao.goods.IDAOCourierTask;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.enums.Role;
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

import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Controller
@PropertySource("classpath:WorkerWithCourier.properties")
public class ControllerCourierTaskCourier {

    @Autowired
    private IDAOCourierTask <CourierTask> daoCourierTask;
    @Autowired
    private IDAOCourierSQL <Courier> daoCourierSQL;

    private final String PAGE_SHOW_COURIER_TASK;
    private final String URL_SHOW_COURIER_TASK;

    public ControllerCourierTaskCourier(@Value("${courier.profile.show.task.page}") String page_show_courier_task, @Value("${courier.profile.show.task.url}") String url_show_courier_task) {
        PAGE_SHOW_COURIER_TASK = page_show_courier_task;
        URL_SHOW_COURIER_TASK = url_show_courier_task;
    }

    @RequestMapping(value = "${courier.profile.show.task.url}", method = RequestMethod.GET)
    public String showCourierTaskPage(@AuthenticationPrincipal Courier courier, Model model, FilterCourierTaskCourier filter) {
        courier.setDoesHeWant(daoCourierSQL.findByIdCourier(courier.getIdCourier()).isDoesHeWant());
        model.addAttribute("filter",filter);
        model.addAttribute("tasks", filter.filtering(courier.getIdCourier(), daoCourierTask));
        model.addAttribute("user", courier);
        model.addAttribute("chooseColor", HandlerControllerCourierTaskAdmin.ChooseColor);
        HandlerController.setMenuModel(courier, model);
        model.addAttribute("buttonCanceled", HandlerCourierTaskCourier.forButtonCanceled());
        model.addAttribute("buttonConfirmed", HandlerCourierTaskCourier.forButtonConfirmed());
        model.addAttribute("buttonGone", HandlerCourierTaskCourier.forButtonGone());
        model.addAttribute("showButton", HandlerCourierTaskCourier.checkWantStatus(daoCourierTask).apply(courier));
        model.addAttribute("ukrNameState", HandlerCourierTaskCourier.setName());
        return PAGE_SHOW_COURIER_TASK;
    }

    @RequestMapping(value="${courier.profile.change.state.canceled.url}", method = RequestMethod.POST)
    public String setStateCanceled (@AuthenticationPrincipal Courier courier, Long idTask) {
        CourierTask task = daoCourierTask.findByRoleAndIdUserAndTaskId(Role.COURIER,courier.getIdCourier(),idTask);
        if(task == null) {
            return "redirect:" + URL_SHOW_COURIER_TASK;
        }
        if(HandlerCourierTaskCourier.forButtonCanceled().apply(task.getConditionCommodity())) {
            daoCourierTask.updateConditionCommodity(task.getId(), ConditionCommodity.CANCELED);
        }
        return "redirect:" + URL_SHOW_COURIER_TASK;
    }

    @RequestMapping(value="${courier.profile.change.state.confirmed.url}", method = RequestMethod.POST)
    public String setStateConfirmed (@AuthenticationPrincipal Courier courier, Long idTask) {
        CourierTask task = daoCourierTask.findByRoleAndIdUserAndTaskId(Role.COURIER,courier.getIdCourier(),idTask);
        if(task == null) {
            return "redirect:" + URL_SHOW_COURIER_TASK;
        }
        if(HandlerCourierTaskCourier.forButtonConfirmed().apply(task.getConditionCommodity())) {
            daoCourierTask.updateConditionCommodity(task.getId(), ConditionCommodity.CONFIRMED);
        }
        return "redirect:" + URL_SHOW_COURIER_TASK;
    }

    @RequestMapping(value="${courier.profile.change.state.gone.url}", method = RequestMethod.POST)
    public String setStateGone (@AuthenticationPrincipal Courier courier, Long idTask) {
        CourierTask task = daoCourierTask.findByRoleAndIdUserAndTaskId(Role.COURIER,courier.getIdCourier(),idTask);
        if(task == null) {
            return "redirect:" + URL_SHOW_COURIER_TASK;
        }
        if(HandlerCourierTaskCourier.forButtonGone().apply(task.getConditionCommodity())) {
            daoCourierTask.updateConditionCommodity(task.getId(), ConditionCommodity.GONE);
        }
        return "redirect:" + URL_SHOW_COURIER_TASK;
    }

    @RequestMapping(value="${courier.profile.request.task.url}", method = RequestMethod.POST)
    public String requestTask (@AuthenticationPrincipal Courier courier) {
        if(courier.isDoesHeWant()) {
            return "redirect:" + URL_SHOW_COURIER_TASK;
        }
        List<CourierTask> list = daoCourierTask.findByRoleAndIdUserAndConditionCommodityIn(Role.COURIER,
                courier.getIdCourier(),
                Set.of(ConditionCommodity.NOT_CONFIRMED, ConditionCommodity.CONFIRMED));
        if(list.isEmpty()) {
            courier.setDoesHeWant(true);
            daoCourierSQL.updateOneById(courier);
        }
        return "redirect:" + URL_SHOW_COURIER_TASK ;
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

    public static Function <Courier, Boolean> checkWantStatus(IDAOCourierTask <CourierTask> dao) {
        return courier -> dao.findByRoleAndIdUserAndConditionCommodityIn(Role.COURIER,
                courier.getIdCourier(),
                Set.of(ConditionCommodity.NOT_CONFIRMED, ConditionCommodity.CONFIRMED)).isEmpty();
    }

    public static Function <ConditionCommodity,String> setName () {
        return state -> state.equals(ConditionCommodity.GONE) ? "Виконано" : ConditionCommodity.getUkraineName(state);
    }

}