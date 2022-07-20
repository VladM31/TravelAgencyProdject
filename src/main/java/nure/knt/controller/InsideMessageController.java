package nure.knt.controller;

import nure.knt.database.service.implement.fiction.IServiceInsideMessage;
import nure.knt.entity.enums.Role;
import nure.knt.entity.important.TravelAgency;
import nure.knt.entity.important.User;
import nure.knt.entity.subordinate.Message;
import nure.knt.entity.subordinate.MessageShortData;
import nure.knt.forms.filter.terms.FilterInsideMessage;
import nure.knt.gmail.ISendTextOnEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Controller
public class InsideMessageController {
    @Autowired
    private IServiceInsideMessage serviceIM;

    private static final String DIRECTORY = "place_for_messages\\";
    private static final String SHOW_ALL_MESSAGES_PAGE = DIRECTORY + "show_all_messages.html";
    private static final String SHOW_MESSAGE_PAGE = DIRECTORY + "showMessagePage.html";
    private static final String SEND_MESSAGE_PAGE = DIRECTORY + "sendMessagePage.html";

    @Autowired
    private ISendTextOnEmail sendTextOnEmail;

    private static final String ATTRIBUTE_FILTER = "filter";
    private static final String ATTRIBUTE_MESSAGES = "messages";

    @RequestMapping(value = "/profile-message",method = {RequestMethod.GET})
    public String showProfileMessageGet(@AuthenticationPrincipal User user, Model model,FilterInsideMessage filter){

        HandlerController.setMenuModel(user,model);

        model.addAttribute(ATTRIBUTE_FILTER,filter);

        System.out.println(Arrays.toString(filter.getRoles()));

        model.addAttribute(ATTRIBUTE_MESSAGES,serviceIM
                .findMessagesShortData(HandlerSendMessage
                        .removeFields(filter)
                        .filtering(serviceIM
                                .term()
                                .idUserToWhom(user.getId())
                        )
                )
        );

        HendlerIMCForAll.setInformationAboutUserForShowAll(model,user);

        return SHOW_ALL_MESSAGES_PAGE;
    }


    private static final String ATTRIBUTE_MESSAGE_SHORT_DATE = "messageShortData";
    private static final String ATTRIBUTE_DESCRIBE_MESSAGE = "describeMessage";

    @RequestMapping(value = "/show-message",method = {RequestMethod.POST})
    public String showMessage(Model model, MessageShortData messageShortData,String sendDateTxt){
        messageShortData.setSendDate(LocalDateTime.parse(sendDateTxt));
        model.addAttribute(ATTRIBUTE_MESSAGE_SHORT_DATE,messageShortData);

        model.addAttribute(ATTRIBUTE_DESCRIBE_MESSAGE,serviceIM.findDescribeByMSD(messageShortData).get());

        return SHOW_MESSAGE_PAGE;
    }

    private static final String ATTRIBUTE_WRITE_MESSAGE = "writeMessage";
    private static final String ATTRIBUTE_WRITE_EMAIL = "writeEmail";
    private static final String ATTRIBUTE_ARE_YOU_ADMIN = "areYouAdmin";

    @RequestMapping(value = "/send-message",method = {RequestMethod.GET})
    public String sendMessageGet(@AuthenticationPrincipal User user,Model model,String sendlerEmail){

        model.addAttribute(ATTRIBUTE_WRITE_EMAIL,(sendlerEmail != null) ? sendlerEmail : "");
        model.addAttribute(ATTRIBUTE_ARE_YOU_ADMIN, HandlerSendMessage.roleIsAdmin(user.getRole()));

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

        writeMessage.setId(IServiceInsideMessage.NEED_TO_GENERATE_ID);
        serviceIM.save(writeMessage,user.getId());

        if (writeEmail.contains(CHOSE_ALL_ROLE)){
            serviceIM.send(writeMessage,user.getId(),Role.values());

            return "redirect:/profile-message";
        }

        String [] strings =writeEmail.split(",");
        //todo
        if (HandlerSendMessage.hasRole(writeEmail)){
            serviceIM.send(writeMessage,user.getId(),HandlerSendMessage.getRoleFromString(strings));
        }

        if(strings.length != 0){
            serviceIM.send(writeMessage,user.getId(),strings);
        }

        if(doSendMessage && strings.length != 0){
            HandlerSendMessage.sendMessageToGmail(strings,user,sendTextOnEmail);
        }

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
                HandlerIMCForTravelAgency.setTravelAgency(model,(TravelAgency)user );
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
    static final String ATTRIBUTE_USER_EDIT = "urlEdit";

    static void setAllInfo(Model model, String nameChoose, String urlChose,boolean haveThirdButton,String nameThirdButton,String urlProfileEdit){
        model.addAttribute(ATTRIBUTE_NAME_CHOOSE,nameChoose);
        model.addAttribute(ATTRIBUTE_URL_CHOOSE,urlChose);
        model.addAttribute(ATTRIBUTE_HAVE_THIRD_BUTTON,haveThirdButton);
        model.addAttribute(ATTRIBUTE_NAME_THIRD_BUTTON,nameThirdButton);
        model.addAttribute(ATTRIBUTE_USER_EDIT,urlProfileEdit);
    }

}

@Component
class HendlerIMCForCustomer{
    private static final String CUSTOMER_NAME_CHOOSE = "Замовлень";
    private static String CUSTOMER_URL_CHOOSE;
    private static String CUSTOMER_PROFILE_EDIT;

    static void setCustomer(Model model){
        HendlerIMCForAll.setAllInfo(model,
                CUSTOMER_NAME_CHOOSE,
                CUSTOMER_URL_CHOOSE,
                HendlerIMCForAll.NO_THIRD_BUTTON,
                HendlerIMCForAll.EMPTY_NAME,
                CUSTOMER_PROFILE_EDIT);
    }

    @Autowired
    public void setUrl(@Value("${customer.profile.order.url}") String CUSTOMER_URL_CHOOSE){
        HendlerIMCForCustomer.CUSTOMER_URL_CHOOSE = CUSTOMER_URL_CHOOSE;
    }
    @Autowired
    public void setCustomerProfileEdit(@Value("${customer.profile.edit}") String customerProfileEdit) {
        CUSTOMER_PROFILE_EDIT = customerProfileEdit;
    }
}

@Component
@PropertySource("classpath:WorkerWithTravelAgency.properties")
class HandlerIMCForTravelAgency {
    private static final String TRAVEL_AGENCY_NAME_CHOOSE = "Послуг";
    private static String TRAVEL_AGENCY_URL_CHOOSE ;
    private static final String TRAVEL_AGENCY_NAME_THIRD_BUTTON = "Створити послугу";
    private static final String ATTRIBUTE_HAVE_RATING = "haveReting";
    private static final String ATTRIBUTE_RATING_STARS = "stars";
    private static String ATTRIBUTE_FOR_URL_CREATE_TOUR_AD;
    private static String URL_CREATE_TOUR_AD;

    static void setTravelAgency(Model model, TravelAgency travelAgency){
        HendlerIMCForAll.setAllInfo(model,
                TRAVEL_AGENCY_NAME_CHOOSE,
                TRAVEL_AGENCY_URL_CHOOSE,
                HendlerIMCForAll.HAVE_THIRD_BUTTON,
                TRAVEL_AGENCY_NAME_THIRD_BUTTON,
                HendlerIMCForAll.EMPTY_NAME);
        model.addAttribute(ATTRIBUTE_HAVE_RATING,true);
        model.addAttribute(ATTRIBUTE_RATING_STARS,TravelAgency.getRetingStars(travelAgency.getRating()));
        model.addAttribute(ATTRIBUTE_FOR_URL_CREATE_TOUR_AD,URL_CREATE_TOUR_AD);
    }

    @Autowired
    public void setInformation(@Value("${profile.attribute.for.button.on.show.all.message}") String ATTRIBUTE_FOR_URL_CREATE_TOUR_AD,
                               @Value("${travel.agency.create.tour.ad.url}") String URL_CREATE_TOUR_AD,
                               @Value("${travel.agency.profile.show.tour.ads.url}") String TRAVEL_AGENCY_URL_CHOOSE){


        HandlerIMCForTravelAgency.TRAVEL_AGENCY_URL_CHOOSE = TRAVEL_AGENCY_URL_CHOOSE;
        HandlerIMCForTravelAgency.ATTRIBUTE_FOR_URL_CREATE_TOUR_AD = ATTRIBUTE_FOR_URL_CREATE_TOUR_AD;
        HandlerIMCForTravelAgency.URL_CREATE_TOUR_AD = URL_CREATE_TOUR_AD;
    }
}

@Component
@PropertySource("classpath:WorkerWithCourier.properties")
class HendlerIMCForCourier {
    private static final String COURIER_NAME_CHOOSE = "Завдання";
    private static String COURIER_EDIT;


    public HendlerIMCForCourier(@Value("${courier.profile.edit.url}")String COURIER_EDIT) {
        HendlerIMCForCourier.COURIER_EDIT = COURIER_EDIT;
    }

    @Autowired
    public void setCourierUrlChoose(@Value("${courier.profile.show.task.url}") String courierUrlChoose) {
        COURIER_URL_CHOOSE = courierUrlChoose;
    }

    private static String COURIER_URL_CHOOSE = "/";

    static void setCourier(Model model){
        HendlerIMCForAll.setAllInfo(model,
                COURIER_NAME_CHOOSE,
                COURIER_URL_CHOOSE,
                HendlerIMCForAll.NO_THIRD_BUTTON,
                HendlerIMCForAll.EMPTY_NAME,
                HendlerIMCForCourier.COURIER_EDIT);
    }
}

@Component
@PropertySource("classpath:WorkerWithAdministrator.properties")
class HendlerIMCForAdministrations {
    private static final String ADMINISTRATIONS_NAME_CHOOSE = "Керування";
    private static String ADMINISTRATIONS_URL_CHOOSE;
    private static final String ATTRIBUTE_ARE_YOU_ADMINS = "IamAdmin";
    private static final String ATTRIBUTE_NAME_ROLE = "nameRole";

    static void setAdministration(Model model,User user){
        HendlerIMCForAll.setAllInfo(model,
                ADMINISTRATIONS_NAME_CHOOSE,
                ADMINISTRATIONS_URL_CHOOSE,
                HendlerIMCForAll.NO_THIRD_BUTTON,
                HendlerIMCForAll.EMPTY_NAME,
                HendlerIMCForAll.EMPTY_NAME);
        model.addAttribute(ATTRIBUTE_ARE_YOU_ADMINS,true);
        model.addAttribute(ATTRIBUTE_NAME_ROLE,user.getRole());
        model.addAttribute(ATTRIBUTE_FOR_URL_CREATE_TOUR_AD,"/");
    }

    private static String ATTRIBUTE_FOR_URL_CREATE_TOUR_AD;
    @Autowired
    public void setAdministrationsUrlChoose(@Value("${admin.show.all.users.url}") String ADMINISTRATIONS_URL_CHOOSE){
        HendlerIMCForAdministrations.ADMINISTRATIONS_URL_CHOOSE = ADMINISTRATIONS_URL_CHOOSE;
    }
    @Autowired
    public void setAttributeForUrlCreateTourAd(@Value("${profile.attribute.for.button.on.show.all.message}") String attributeForUrlCreateTourAd) {
        ATTRIBUTE_FOR_URL_CREATE_TOUR_AD = attributeForUrlCreateTourAd;
    }

}

class HandlerSendMessage {

    static FilterInsideMessage removeFields(FilterInsideMessage filter){
        filter.setHowSort(null);
        filter.setOrderBy(null);
        filter.setLimits(null);
        return filter;
    }

    static boolean roleIsAdmin(Role role){
        return role == Role.ADMINISTRATOR || role == Role.MODERATOR;
    }

    private static final String MESSAGE_NOTIFY = "Вам на сайті написав повідомлення %s. Перейдіть на сайт, щоб прочитати повідомлення.";
    private static final String MESSAGE_NAME = "Tangerine summer message";

    static void sendMessageToGmail(String[] emails,User user,ISendTextOnEmail sendTextOnEmail){
        String message = String.format(MESSAGE_NOTIFY,user.getName().replace('/',' '));
        for(String email : emails){
           sendTextOnEmail.sendMessageOnEmail(email,MESSAGE_NAME,message);
        }
    }


    static boolean hasRole(String writeEmail){
        return writeEmail.contains("Role:");
    }

    private static final boolean IT_IS_EMAIL = false;
    static Role[] getRoleFromString(String[] rolesAndEmeils){
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
        return roles.toArray(Role[]::new);
    }
}