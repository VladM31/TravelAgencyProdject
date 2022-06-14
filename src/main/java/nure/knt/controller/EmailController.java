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


public class EmailController {

}