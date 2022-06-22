package nure.knt.controller;

import nure.knt.database.idao.entity.IDAOUserSQL;
import nure.knt.entity.important.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class SecurityControler {

    @Autowired
    private List<IDAOUserSQL<User>> daoes;
    // ************* Вхід і вихід *****************
    @RequestMapping(value = { "/login"}, method = { RequestMethod.GET })
    public String signInGet(Model model) {
        return "login";
    }

    @RequestMapping(value = { "/logout"}, method = { RequestMethod.GET })
    public String signOutGet(@AuthenticationPrincipal User user,Model model) {
        HandlerController.setMenuModel(user,model);
        return "logout";
    }

    // ************* Вибір реєстрації *****************

    @RequestMapping(value= {"${registration.choose.sign.up}"},method = { RequestMethod.GET,RequestMethod.POST })
    public String showSignUpPage(Model model) {
        return "registration/Choose registration Page";
    }
    // ************* Реєстрація агенції *****************
    @ResponseBody
    @RequestMapping("/all")
    public String allCustomer() {
        StringBuilder allUsers = new StringBuilder();
        for(IDAOUserSQL dao:daoes){
            for (Object user: dao.findAll()) {
                allUsers.append( "<p>").append(user).append("</p>");

            }
        }
        return allUsers.toString();
    }
}


