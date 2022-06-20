package nure.knt.controller.profile;

import nure.knt.database.idao.entity.IDAOUserSQL;
import nure.knt.entity.important.User;
import nure.knt.tools.WorkWithCountries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@PropertySource("classpath:WorkerWithAdministrator.properties")
public class ControllerAdministrator {
    @Autowired
    private WorkWithCountries countries;
    @Autowired
    @Qualifier("DAO_MySQL_User")
    private IDAOUserSQL<User> daoUsers;

    private final String PAGE_FOR_SHOW_ALL_USERS;
    private final String URL_FOR_SHOW_ALL_USERS;

    public ControllerAdministrator(@Value("${admin.show.all.users.page}")String PAGE_FOR_SHOW_ALL_USERS,
                                   @Value("${admin.show.all.users.url}")String URL_FOR_SHOW_ALL_USERS) {
        this.PAGE_FOR_SHOW_ALL_USERS = PAGE_FOR_SHOW_ALL_USERS;
        this.URL_FOR_SHOW_ALL_USERS = URL_FOR_SHOW_ALL_USERS;
    }

    @RequestMapping(value="${admin.show.all.users.url}",method = RequestMethod.GET)
    public String showAllUsers(@AuthenticationPrincipal User user, Model model){

        model.addAttribute("user",user);
        return PAGE_FOR_SHOW_ALL_USERS;
    }

}
