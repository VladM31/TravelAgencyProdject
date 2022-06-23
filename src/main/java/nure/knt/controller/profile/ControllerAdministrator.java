package nure.knt.controller.profile;


import nure.knt.controller.HandlerController;
import nure.knt.database.idao.entity.IDAOUserOnly;
import nure.knt.database.idao.entity.IDAOUserSQL;
import nure.knt.entity.enums.Role;
import nure.knt.entity.important.User;
import nure.knt.forms.filter.FilterAllUsers;
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
@PropertySource("classpath:WorkerWithMessage.properties")
public class ControllerAdministrator {
    @Autowired
    private WorkWithCountries countries;
    @Autowired
    @Qualifier("DAO_MySQL_User")
    private IDAOUserOnly daoUsers;

    private final String PAGE_FOR_SHOW_ALL_USERS;
    private final String URL_FOR_SHOW_ALL_USERS;

    public ControllerAdministrator(@Value("${admin.show.all.users.page}")String PAGE_FOR_SHOW_ALL_USERS,
                                   @Value("${admin.show.all.users.url}")String URL_FOR_SHOW_ALL_USERS) {
        this.PAGE_FOR_SHOW_ALL_USERS = PAGE_FOR_SHOW_ALL_USERS;
        this.URL_FOR_SHOW_ALL_USERS = URL_FOR_SHOW_ALL_USERS;
    }

    @RequestMapping(value="${admin.show.all.users.url}",method = RequestMethod.GET)
    public String showAllUsers(@AuthenticationPrincipal User user, Model model, FilterAllUsers filter){

       this.setInfoForShowAllUsers(user,model,filter);

       // System.out.println(filter);
        return PAGE_FOR_SHOW_ALL_USERS;
    }


    @RequestMapping(value="${admin.show.all.users.url}",method = RequestMethod.PATCH)
    public String changeActiveUser(Long id,Boolean active){

        this.daoUsers.updateStateUser(id,!active);

        return "redirect:"+URL_FOR_SHOW_ALL_USERS;
    }

    private void setInfoForShowAllUsers(User user, Model model, FilterAllUsers filter){
        HandlerControllerAdministrator.setInfoForShowAllUsersLogic(user,model,filter,countries,daoUsers);
        HandlerController.setMenuModel(user,model);
    }
}


class HandlerControllerAdministrator{

    static void setInfoForShowAllUsersLogic(User user, Model model, FilterAllUsers filter,WorkWithCountries countries,IDAOUserOnly daoUsers){
        model.addAttribute("user",user);
        model.addAttribute("filter", filter);
        model.addAttribute("users",filter.filtering(daoUsers).stream().limit(10).toList());
        model.addAttribute("countries",countries.getCountry());
        model.addAttribute("isAdmin", user.getRole().equals(Role.ADMINISTRATOR));
    }
}