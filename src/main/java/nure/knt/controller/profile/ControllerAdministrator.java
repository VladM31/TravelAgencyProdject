package nure.knt.controller.profile;


import nure.knt.controller.HandlerController;
import nure.knt.database.idao.terms.fieldenum.UserField;
import nure.knt.database.idao.terms.users.ITermUser;
import nure.knt.database.idao.tools.IConnectorGetter;
import nure.knt.database.idao.entity.IDAOUserOnly;
import nure.knt.database.service.implement.users.IServiceUser;
import nure.knt.entity.enums.Role;
import nure.knt.entity.important.User;
import nure.knt.forms.filter.FilterAllUsers;
import nure.knt.forms.filter.terms.FilterUserTerm;
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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
@PropertySource("classpath:WorkerWithAdministrator.properties")
@PropertySource("classpath:WorkerWithMessage.properties")
public class ControllerAdministrator {
    @Autowired
    private WorkWithCountries countries;
    @Autowired
    @Qualifier("User_Service")
    private IServiceUser<User, ITermUser> serviceUser;

    private final String PAGE_FOR_SHOW_ALL_USERS;
    private final String URL_FOR_SHOW_ALL_USERS;

    public ControllerAdministrator(@Value("${admin.show.all.users.page}")String PAGE_FOR_SHOW_ALL_USERS,
                                   @Value("${admin.show.all.users.url}")String URL_FOR_SHOW_ALL_USERS) {
        this.PAGE_FOR_SHOW_ALL_USERS = PAGE_FOR_SHOW_ALL_USERS;
        this.URL_FOR_SHOW_ALL_USERS = URL_FOR_SHOW_ALL_USERS;
    }

    @RequestMapping(value="${admin.show.all.users.url}",method = RequestMethod.GET)
    public String showAllUsers(@AuthenticationPrincipal User user, Model model, FilterUserTerm filter){

       this.setInfoForShowAllUsers(user,model,filter);

       // System.out.println(filter);
        return PAGE_FOR_SHOW_ALL_USERS;
    }


    @RequestMapping(value="${admin.show.all.users.url}",method = RequestMethod.PATCH)
    public String changeActiveUser(Long id,Boolean active){

        User temp = new User();
        temp.setId(id);
        temp.setActive(!active);
        serviceUser.updateOneById(temp, UserField.ACTIVE);

        return "redirect:"+URL_FOR_SHOW_ALL_USERS;
    }

    @RequestMapping(value="${administrator.want.delete.other.user}", method = RequestMethod.DELETE)
    public String deleteUser(Long id){

        serviceUser.deleteById(id);
        return "redirect:"+URL_FOR_SHOW_ALL_USERS;
    }

    //@RequestMapping(value="${administrator.profile.select.url}", method = RequestMethod.GET)
    public String showTableDateFromSelect(Model model,IConnectorGetter connector,@Value("${administrator.profile.select.page}") String page,String script){
        if(script == null || script.isEmpty()){
            return page;
        }
        String[] arrayNameColumn = HandlerControllerAdministrator.getNameColumn(script);

        try {
            model.addAttribute("listRow",HandlerControllerAdministrator.getAllInfoFromSelect(arrayNameColumn,connector,script));
            model.addAttribute("arrayNameColumn",arrayNameColumn);

        } catch (Exception e) {
            model.addAttribute("MyExceptions",e.getStackTrace());
        }
        return page;
    }


    private void setInfoForShowAllUsers(User user, Model model, FilterUserTerm filter){
        HandlerControllerAdministrator.setInfoForShowAllUsersLogic(user,model,filter,countries,serviceUser);
        HandlerController.setMenuModel(user,model);
    }
}


class HandlerControllerAdministrator{

    static void setInfoForShowAllUsersLogic(User user, Model model, FilterUserTerm filter,WorkWithCountries countries, IServiceUser<User, ITermUser> service){
        model.addAttribute("user",user);
        model.addAttribute("filter", filter);
        model.addAttribute("users",service.findBy(filter.filtering(service.term()).end()));
        model.addAttribute("countries",countries.getCountry());
        model.addAttribute("isAdmin", user.getRole().equals(Role.ADMINISTRATOR));
    }

    static String[] getNameColumn(String script){

        script = script.substring(6,script.indexOf(" FROM "));

        String[] arr = script.split(",");

        for (int i = 0; i < arr.length; i++) {
            if (arr[i].contains(" AS ")){
                arr[i] = arr[i].substring(arr[i].indexOf(" AS ") + 3);
            }

            if(arr[i].contains(".")){
                arr[i] = arr[i].substring(arr[i].indexOf(".")+1);
            }

            if(arr[i].contains("\"")){
                arr[i] = arr[i].replace('\"',' ');
            }

            arr[i] = arr[i].trim();

        }

        return arr;
    }

    static List<List<String>> getAllInfoFromSelect(String[] column,IConnectorGetter connector,final String SCRIPT_SELECT) throws SQLException {
        List<List<String>> listRow = new LinkedList<>();

        try(PreparedStatement statement = connector.getSqlPreparedStatement(SCRIPT_SELECT)){
            try(ResultSet resultSet = statement.executeQuery()) {
                List<String> list = null;
                while(resultSet.next()){
                    list = new ArrayList<>(column.length);
                    for(String str : column){
                        list.add(resultSet.getString(str));
                    }
                    listRow.add(list);
                }
            }
        }

        return listRow;
    }
}