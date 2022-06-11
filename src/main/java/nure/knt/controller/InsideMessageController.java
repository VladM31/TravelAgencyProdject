package nure.knt.controller;

import nure.knt.database.idao.temporary.IDAOMessage;
import nure.knt.entity.enums.Role;
import nure.knt.entity.important.TravelAgency;
import nure.knt.entity.important.User;
import nure.knt.entity.subordinate.Message;
import nure.knt.entity.subordinate.MessageShortData;
import nure.knt.forms.filter.FilterMessageShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.util.*;

@Controller
public class InsideMessageController {
    private static final String DIRECTORY = "place_for_messages\\";
    private static final String SHOW_ALL_MESSAGES_PAGE = DIRECTORY + "show_all_messages.html";
    private static final String SHOW_MESSAGE_PAGE = DIRECTORY + "showMessagePage.html";
    private static final String SEND_MESSAGE_PAGE = DIRECTORY + "sendMessagePage.html";

    @Autowired
    private IDAOMessage idaoMessage;

    private static final String ATTRIBUTE_FILTER = "filter";
    private static final String ATTRIBUTE_MESSAGES = "messages";

    @RequestMapping(value = "/profile-message",method = {RequestMethod.GET})
    public String showProfileMessageGet(@AuthenticationPrincipal User user, Model model){

        if (user != null) {
            HandlerController.setMenuModel(user,model);
        }


        model.addAttribute(ATTRIBUTE_FILTER,new FilterMessageShow());
        model.addAttribute(ATTRIBUTE_MESSAGES,idaoMessage.findMessageShortDataAllByToWhom(user.getId()));
        HendlerIMCForAll.setInformationAboutUserForShowAll(model,user);

        return SHOW_ALL_MESSAGES_PAGE;
    }

    @RequestMapping(value = "/profile-message",method = {RequestMethod.POST})
    public String showProfileMessagePOST(@AuthenticationPrincipal User user, Model model, FilterMessageShow filter){

        if (user != null) {
            HandlerController.setMenuModel(user,model);
        }

        model.addAttribute(ATTRIBUTE_FILTER,filter);
        model.addAttribute(ATTRIBUTE_MESSAGES,filter.filtering(user.getId(),this.idaoMessage));
        HendlerIMCForAll.setInformationAboutUserForShowAll(model,user);
        return SHOW_ALL_MESSAGES_PAGE;
    }

    private static final String ATTRIBUTE_MESSAGE_SHORT_DATE = "messageShortData";
    private static final String ATTRIBUTE_DESCRIBE_MESSAGE = "describeMessage";

    @RequestMapping(value = "/show-message",method = {RequestMethod.POST})
    public String showMessage(Model model, MessageShortData messageShortData,String sendDateTxt){
        messageShortData.setSendDate(LocalDateTime.parse(sendDateTxt));
        model.addAttribute(ATTRIBUTE_MESSAGE_SHORT_DATE,messageShortData);
        model.addAttribute(ATTRIBUTE_DESCRIBE_MESSAGE,this.idaoMessage.findDescribeByMSD(messageShortData));

        return SHOW_MESSAGE_PAGE;
    }

    private static final String ATTRIBUTE_WRITE_MESSAGE = "writeMessage";
    private static final String ATTRIBUTE_WRITE_EMAIL = "writeEmail";
    private static final String ATTRIBUTE_ARE_YOU_ADMIN = "areYouAdmin";

    @RequestMapping(value = "/send-message",method = {RequestMethod.GET})
    public String sendMessageGet(@AuthenticationPrincipal User user,Model model,String sendlerEmail){

        model.addAttribute(ATTRIBUTE_WRITE_EMAIL,(sendlerEmail != null) ? sendlerEmail : "");
        model.addAttribute(ATTRIBUTE_ARE_YOU_ADMIN,HendlerSendMessage.roleIsAdmin(user.getRole()));

        Message message = new Message();
        message.setId(1l);
        model.addAttribute(ATTRIBUTE_WRITE_MESSAGE,message);

        return SEND_MESSAGE_PAGE;
    }

    private static final String CHOSE_ALL_ROLE = "Role:All";

    @RequestMapping(value = "/send-message",method = {RequestMethod.POST})
    public String sendMessagePost(@AuthenticationPrincipal User user,Model model,Message writeMessage,String writeEmail, boolean doSendMessage){


        if(writeEmail == null || writeEmail.trim().isEmpty()){
            //todo
            return "redirect:/profile-message";
        }

        writeMessage.setId(IDAOMessage.NEED_TO_GENERATE_ID);

        if (writeEmail.contains(CHOSE_ALL_ROLE)){
            idaoMessage.save(writeMessage,user.getId(),HendlerSendMessage.getAllRoles());

            return "redirect:/profile-message";
        }

        String [] strings =writeEmail.split(",");
        //todo
        if (HendlerSendMessage.hasRole(writeEmail)){
            idaoMessage.save(writeMessage,user.getId(),HendlerSendMessage.getRoleFromString(strings));
        }
        idaoMessage.save(writeMessage,user.getId(),strings);

        return "redirect:/profile-message";
    }

}

class HendlerIMCForAll {

    private static final String ATTRIBUTE_NAME_USER = "nameUser";


    static void setInformationAboutUserForShowAll(Model model, User user) {

        model.addAttribute(ATTRIBUTE_NAME_USER, user.getName().replace('/',' '));

        switch (user.getRole()){
            case CUSTOMER:
                HendlerIMCForCustomer.setCustomer(model);
                break;
            case TRAVEL_AGENCY:
                HendlerIMCForTravelAgency.setTravelAgency(model,(TravelAgency)user );
                break;
            case COURIER:
                HendlerIMCForCourier.setCourier(model);
                break;
            case MODERATOR:
            case ADMINISTRATOR:
                HendlerIMCForAdministrations.setAdministration(model,user);
                break;
        }

    }

    static final String ATTRIBUTE_NAME_CHOOSE = "nameChoose";
    static final String ATTRIBUTE_URL_CHOOSE = "urlChoose";
    static final String ATTRIBUTE_HAVE_THIRD_BUTTON = "haveButton";
    static final String ATTRIBUTE_NAME_THIRD_BUTTON = "nameButton";
    static final String EMPTY_NAME = "";
    static final boolean NO_THIRD_BUTTON = false;
    static final boolean HAVE_THIRD_BUTTON = true;

    static void setAllInfo(Model model, String nameChoose, String urlChose,boolean haveThirdButton,String nameThirdButton){
        model.addAttribute(ATTRIBUTE_NAME_CHOOSE,nameChoose);
        model.addAttribute(ATTRIBUTE_URL_CHOOSE,urlChose);
        model.addAttribute(ATTRIBUTE_HAVE_THIRD_BUTTON,haveThirdButton);
        model.addAttribute(ATTRIBUTE_NAME_THIRD_BUTTON,nameThirdButton);
    }

}

@Component
class HendlerIMCForCustomer{
    private static final String CUSTOMER_NAME_CHOOSE = "Замовлень";
    private static final String CUSTOMER_URL = "";
    private static String CUSTOMER_URL_CHOOSE;

    static void setCustomer(Model model){
        HendlerIMCForAll.setAllInfo(model,
                CUSTOMER_NAME_CHOOSE,
                CUSTOMER_URL_CHOOSE,
                HendlerIMCForAll.NO_THIRD_BUTTON,
                HendlerIMCForAll.EMPTY_NAME);
    }

    @Autowired
    public void setUrl(@Value("${customer.profile.order.url}") String CUSTOMER_URL_CHOOSE){
        HendlerIMCForCustomer.CUSTOMER_URL_CHOOSE = CUSTOMER_URL + CUSTOMER_URL_CHOOSE;
    }
}

class HendlerIMCForTravelAgency{
    private static final String TRAVEL_AGENCY_NAME_CHOOSE = "Послуг";
    private static final String TRAVEL_AGENCY_URL_CHOOSE = "/";
    private static final String TRAVEL_AGENCY_NAME_THIRD_BUTTON = "Створити послугу";
    private static final String ATTRIBUTE_HAVE_RATING = "haveReting";
    private static final String ATTRIBUTE_RATING_STARS = "stars";

    static void setTravelAgency(Model model, TravelAgency travelAgency){
        HendlerIMCForAll.setAllInfo(model,
                TRAVEL_AGENCY_NAME_CHOOSE,
                TRAVEL_AGENCY_URL_CHOOSE,
                HendlerIMCForAll.HAVE_THIRD_BUTTON,
                TRAVEL_AGENCY_NAME_THIRD_BUTTON);
        model.addAttribute(ATTRIBUTE_HAVE_RATING,true);
        model.addAttribute(ATTRIBUTE_RATING_STARS,TravelAgency.getRetingStars(travelAgency.getRating()));
    }

}

class HendlerIMCForCourier {
    private static final String COURIER_NAME_CHOOSE = "Завдання";
    private static final String COURIER_URL_CHOOSE = "/";

    static void setCourier(Model model){
        HendlerIMCForAll.setAllInfo(model,
                COURIER_NAME_CHOOSE,
                COURIER_URL_CHOOSE,
                HendlerIMCForAll.NO_THIRD_BUTTON,
                HendlerIMCForAll.EMPTY_NAME);
    }
}

class HendlerIMCForAdministrations {
    private static final String ADMINISTRATIONS_NAME_CHOOSE = "Керування";
    private static final String ADMINISTRATIONS_URL_CHOOSE = "/";
    private static final String ATTRIBUTE_ARE_YOU_ADMINS = "IamAdmin";
    private static final String ATTRIBUTE_NAME_ROLE = "nameRole";

    static void setAdministration(Model model,User user){
        HendlerIMCForAll.setAllInfo(model,
                ADMINISTRATIONS_NAME_CHOOSE,
                ADMINISTRATIONS_URL_CHOOSE,
                HendlerIMCForAll.NO_THIRD_BUTTON,
                HendlerIMCForAll.EMPTY_NAME);
        model.addAttribute(ATTRIBUTE_ARE_YOU_ADMINS,true);
        model.addAttribute(ATTRIBUTE_NAME_ROLE,user.getRole());
    }
}

class HendlerSendMessage{

    static boolean roleIsAdmin(Role role){
        return role == Role.ADMINISTRATOR || role == Role.MODERATOR;
    }

    static String[] getEmail(String emailLine){
        return emailLine.replace(" ","").split(",");
    }

    static boolean sendMessageToGmail(boolean doSendMessage){
        if (!doSendMessage){
            return false;
        }
        // todo
        return doSendMessage;
    }

    static Set<Role> getAllRoles(){
        return Set.of(Role.MODERATOR,Role.TRAVEL_AGENCY,Role.CUSTOMER,Role.COURIER);
    }

    static boolean hasRole(String writeEmail){
        return writeEmail.contains("Role:");
    }

    static boolean hasComa(String writeEmail){
        return writeEmail.contains(",");
    }

    private static final boolean IT_IS_EMAIL = false;
    static Set<Role> getRoleFromString(String[] rolesAndEmeils){
        Set<Role> roles = new HashSet<>();
        try{
            for (String str:rolesAndEmeils) {
                if(hasRole(str) == IT_IS_EMAIL){
                    break;
                }
                roles.add(Role.valueOf(str.substring(str.indexOf(':')+1)));
            }
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return roles;
    }
}