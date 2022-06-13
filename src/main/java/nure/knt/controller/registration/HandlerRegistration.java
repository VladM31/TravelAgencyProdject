package nure.knt.controller.registration;

import org.springframework.ui.Model;

public class HandlerRegistration {
    public static final String EMAIL_ATTRIBUTE = "email";
    public static final String NAME_ATTRIBUTE = "name";

    public static void setAttributeCheck(Model model, String name, String email, String url, boolean error) {
        model.addAttribute("helloUser","Hello " + name + "!!!");
        model.addAttribute("yourEmail","Your email is " + email + ".");
        model.addAttribute("errorCode", error);
        model.addAttribute("userURL",url);

        model.addAttribute(EMAIL_ATTRIBUTE,email);
        model.addAttribute(NAME_ATTRIBUTE,name);
    }
}
