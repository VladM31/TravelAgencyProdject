package nure.knt.controller;

import nure.knt.database.idao.tools.IConnectorGetter;
import nure.knt.database.idao.entity.IDAOUserSQL;
import nure.knt.entity.important.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


@Controller
public class SecurityControler {

    private Thread resetAndCheck;

    @Autowired
    private IConnectorGetter conector;

    @PostConstruct()
    private void init(){
        resetAndCheck = new Thread( () ->{

            try(java.sql.PreparedStatement statement = conector.getSqlPreparedStatement("call updateTourAdAndOrderTourAnd_AVG_UserRating(?);")) {

                while(true){



                    statement.setDate(1, Date.valueOf(LocalDate.now()));
                        System.out.println("test " + statement.executeUpdate());
                    Thread.sleep(20000000);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        });

        resetAndCheck.start();
    }

    @PreDestroy
    private void destroy(){
        System.out.println("end");
        //todo
        resetAndCheck.stop();
    }


    @Autowired
    private List<IDAOUserSQL<User>> daoes;
    // ************* Вхід і вихід *****************
    @RequestMapping(value = { "/login"}, method = { RequestMethod.GET })
    public String signInGet() {
        return "login";
    }

    @RequestMapping(value = { "/logout"}, method = { RequestMethod.GET })
    public String signOutGet(@AuthenticationPrincipal User user,Model model) {
        HandlerController.setMenuModel(user,model);
        return "logout";
    }

    // ************* Вибір реєстрації *****************

    @RequestMapping(value= {"${registration.choose.sign.up}"},method = { RequestMethod.GET,RequestMethod.POST })
    public String showSignUpPage() {
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


