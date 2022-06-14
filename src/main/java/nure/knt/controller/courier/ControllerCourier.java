package nure.knt.controller.courier;


import nure.knt.database.idao.goods.IDAOCourierTask;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.goods.CourierTask;
import nure.knt.entity.important.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.function.Function;

@Controller
public class ControllerCourier {
    private final String PAGE_NAME_COURIER_TASKS = "courier_directory/show_task";

    @Autowired
    private IDAOCourierTask <CourierTask> daoTask;

    private final String ATTRIBUTE_TASKS = "tasks";
    private final String ATTRIBUTE_COLOR = "ChooseColor";
    private final String ATTRIBUTE_ROLE_ADMIN = "RoleAdmin";

    @RequestMapping(value="/show_task")
    String showTasks(@AuthenticationPrincipal User user, Model model) {
        this.setInfoForTasks(user, model);
        return PAGE_NAME_COURIER_TASKS;
    }

    private void setInfoForTasks(User user, Model model) {
        model.addAttribute(ATTRIBUTE_TASKS, daoTask.findByRoleAndIdUser(user.getRole(), user.getId()));
        model.addAttribute(ATTRIBUTE_COLOR, HandlerControllerCourier.ChooseColor);
        model.addAttribute(ATTRIBUTE_ROLE_ADMIN, true);
    }
}

class HandlerControllerCourier {
    protected static final Function<ConditionCommodity, String> ChooseColor = (c)-> {
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
}
