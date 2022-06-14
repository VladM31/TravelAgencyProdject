package nure.knt.controller.courier;


import nure.knt.database.idao.goods.IDAOCourierTask;
import nure.knt.entity.goods.CourierTask;
import nure.knt.entity.important.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControllerCourier {
    private final String PAGE_NAME_COURIER_TASKS = "courier_directory/show_task";

    @Autowired
    private IDAOCourierTask <CourierTask> daoTask;

    private final String ATTRIBUTE_TASKS = "tasks";

    @RequestMapping(value="/show_task")
    String showTasks(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute(ATTRIBUTE_TASKS, daoTask.findByRoleAndIdUser(user.getRole(), user.getId()));
        return PAGE_NAME_COURIER_TASKS;
    }
}
