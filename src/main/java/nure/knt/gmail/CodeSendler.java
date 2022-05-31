package nure.knt.gmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class CodeSendler {
    public JavaMailSender emailSender;
    @Autowired
    public void setEmailSender(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendCode(String email,String name,String cod){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);

        message.setSubject("Sign up Email");

        message.setText(String.format("Hello %s, your email use for sign up at site \"Tangerine summer\", use this code --> %s <--.",name,cod));
        // Send Message!
        this.emailSender.send(message);
    }
}
