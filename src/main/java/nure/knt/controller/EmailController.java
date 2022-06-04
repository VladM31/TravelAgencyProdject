package nure.knt.controller;

import nure.knt.database.idao.entity.IDAOTravelAgencySQL;
import nure.knt.database.idao.temporary.IDAOTravelAgencyTemporaryCode;
import nure.knt.entity.important.TravelAgency;
import nure.knt.entity.subordinate.TravelAgencyTemporary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EmailController {

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private IDAOTravelAgencySQL<TravelAgency> daoTravelAgency;

    @Autowired
    private IDAOTravelAgencyTemporaryCode idaoTravelAgencyTemporaryCode;

    private static final boolean enteredCodeIsWrong = true;
    private static final boolean dontHaveTheProblem = false;

    private static final String travelAgencyCheckUrl = "/confirm.mail.travel.agency";
    private static final String check_out_Email_Code_HTML_File = "checkOutEmailCodePage";
    private static final String go_to_the_login = "redirect:/login";

    @RequestMapping(value= {"/confirm.mail.travel.agency"},method = { RequestMethod.GET })
    public String sendCodeToEmailForTravelAgencyGet(Model model, String email) {

        TravelAgencyTemporary tat = idaoTravelAgencyTemporaryCode.getTravelAgencyTemporaryByEmail(email);

        this.sendCode(email,tat.getName(),idaoTravelAgencyTemporaryCode.getCodeByIdTempUser(tat));

        this.setAttributeCheck(model,tat.getName(),email,travelAgencyCheckUrl,dontHaveTheProblem);

        return check_out_Email_Code_HTML_File;
    }

    // @ResponseBody
    @RequestMapping(value= {"/confirm.mail.travel.agency"},method = { RequestMethod.POST })
    public String sendCodeToEmailForTravelAgencyPost(Model model,String email,Long cod) {

        TravelAgencyTemporary tat = idaoTravelAgencyTemporaryCode.getTravelAgencyTemporaryByCode(cod);

        if(tat.getEmail().equals(email)) {
            this.daoTravelAgency.save(tat.toTravelAgency());
            return go_to_the_login;
        }

        this.setAttributeCheck(model,tat.getName(),email,travelAgencyCheckUrl,enteredCodeIsWrong);

        return check_out_Email_Code_HTML_File;
    }

    private void sendCode(String email,String name,long cod){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);

        message.setSubject("Sign up Email");

        message.setText(String.format("Hello %s, your email use for sign up at site \"Tangerine summer\", use this code --> %d <--.",name,cod));
        // Send Message!
        this.emailSender.send(message);
    }

    private void setAttributeCheck(Model model,String name,String email,String url,boolean error) {
        model.addAttribute("helloUser","Hello " + name + "!!!");
        model.addAttribute("yourEmail","Your email is " + email + ".");
        model.addAttribute("errorCode", error);
        model.addAttribute("cod",0l);
        model.addAttribute("email",email);
        model.addAttribute("userURL",url);
    }

}