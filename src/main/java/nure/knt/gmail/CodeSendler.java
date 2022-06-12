package nure.knt.gmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class CodeSendler {
    private ISendTextOnEmail emailSender;
    @Autowired
    public void setEmailSender(ISendTextOnEmail emailSender) {
        this.emailSender = emailSender;
    }

    private static final String NAME_MESSAGE = "Sign up Email";
    private static final String TEMPLATE_DESCRIBE_MESSAGE = "Hello %s, your email use for sign up at site \"Tangerine summer\", use this code --> %s <--.";

    public void sendCode(String email,String name,String cod){
        emailSender.sendMessageOnEmail(email,NAME_MESSAGE,
                String.format(TEMPLATE_DESCRIBE_MESSAGE,name,cod)
        );
    }
}
