package nure.knt.controller.courier;


import nure.knt.controller.HandlerController;
import nure.knt.database.idao.entity.IDAOCourierSQL;
import nure.knt.database.idao.goods.IDAOCourierTask;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.goods.CourierTask;
import nure.knt.entity.important.Courier;
import nure.knt.entity.important.User;
import nure.knt.forms.filter.FilterCourierTaskAdmin;
import nure.knt.forms.goods.CourierTaskForm;
import nure.knt.tools.WorkWithCountries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;
import java.util.function.Function;



@Controller
@PropertySource("classpath:WorkerWithAdministrator.properties")
public class ControllerCourierTaskAdmin {
    private final String PAGE_NAME_COURIER_TASKS = "courier_directory/show_task";

    @Autowired
    private IDAOCourierSQL <Courier> daoCourier;

    @Autowired
    private WorkWithCountries countries;

    @Autowired
    private IDAOCourierTask<CourierTask> daoTask;

    private final String ATTRIBUTE_TASKS = "tasks";
    private final String ATTRIBUTE_COLOR = "ChooseColor";
    private final String ATTRIBUTE_ROLE_ADMIN = "RoleAdmin";
    private final String URL_ADMINPROFILE_SHOW_COURIERTASK;
    private final String URL_CREATE_TASK;
    private final String PAGE_CREATE_TASK;

    public ControllerCourierTaskAdmin(@Value("${admin.profile.show.courier.task}") String url_adminprofile_show_couriertask,
                                      @Value("${admin.profile.create.courier.task.url}")String url_create_task,
                                      @Value("${admin.profile.create.courier.task.page}") String page_create_task) {
        URL_ADMINPROFILE_SHOW_COURIERTASK = url_adminprofile_show_couriertask;
        URL_CREATE_TASK = url_create_task;
        PAGE_CREATE_TASK = page_create_task;
    }

    @RequestMapping(value = "${admin.profile.show.courier.task}", method = {RequestMethod.GET})
    public String showTaskGet(@AuthenticationPrincipal User user, Model model, FilterCourierTaskAdmin filter) {

        model.addAttribute("filter", (filter == null) ? new FilterCourierTaskAdmin() : filter);
        model.addAttribute(ATTRIBUTE_TASKS, filter.filtering(user.getId(), daoTask));
        this.setInfoForTasks(user, model);
        HandlerController.setMenuModel(user, model);
        model.addAttribute("user", user);
        return HandlerControllerCourierTaskAdmin.PAGE_PROFILE_ORDER;
    }

    private void setInfoForTasks(User user, Model model) {

        model.addAttribute(ATTRIBUTE_COLOR, HandlerControllerCourierTaskAdmin.ChooseColor);
        model.addAttribute(ATTRIBUTE_ROLE_ADMIN, true);
        model.addAttribute("countries", countries.getCountry());
    }

    @RequestMapping(value="${admin.profile.show.courier.task}", method = {RequestMethod.PATCH})
    public String setStateCanceled(Long id) {
        daoTask.updateConditionCommodity(id, ConditionCommodity.CANCELED);
        return "redirect:" + URL_ADMINPROFILE_SHOW_COURIERTASK;
    }

    @RequestMapping(value="${admin.profile.create.courier.task.url}", method = {RequestMethod.GET})
    public String findCourierByCountryAndCity(Model model,String country, String city) {
        var list = daoCourier.findByDoesHeWant(true).stream().filter(courier ->
                courier.getCountry().equals(country) && courier.getCity().toLowerCase().contains(city.toLowerCase())).toList();
        if(list.isEmpty()) {
            return "redirect:" + URL_ADMINPROFILE_SHOW_COURIERTASK;
        }
        model.addAttribute("form", new CourierTaskForm());
        model.addAttribute("information", CourierTaskForm.listToArray(list));
        return PAGE_CREATE_TASK;
    }

    @RequestMapping(value="${admin.profile.create.courier.task.url}", method = {RequestMethod.POST})
    public String showFormForCreateTask (@AuthenticationPrincipal User user, Model model, CourierTaskForm form) {
        Courier courier = daoCourier.findByIdCourier(
                CourierTaskForm.getCourierId(
                        form.getInfoCourier()));
        daoTask.save(
                form.toCourierTask(courier, user.getId()));

        courier.setDoesHeWant(false);

        daoCourier.updateOneById(courier);
        return "redirect:" + URL_ADMINPROFILE_SHOW_COURIERTASK;
    }
}

class HandlerControllerCourierTaskAdmin {
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
