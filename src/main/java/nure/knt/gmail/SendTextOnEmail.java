package nure.knt.gmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component("Send message on email")
public class SendTextOnEmail implements ISendTextOnEmail{
    public JavaMailSender emailSender;
    @Autowired
    public void setEmailSender(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendMessageOnEmail(String emailWhereToSendTheMessage, String nameMessage, String describeMessage) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(emailWhereToSendTheMessage);

        message.setSubject(nameMessage);

        message.setText(describeMessage);
        // Send Message!
        this.emailSender.send(message);
    }

}
