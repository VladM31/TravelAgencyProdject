package nure.knt.forms.signup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserForm {
    @Size(min=2,max=60, message = "Логін повинен бути більше 2 і не менше 60 символів")
    @NotBlank(message = "Логін не повинен бути з пробілів")
    private String username;
    @Size(min=8,max=60, message = "Пароль повинен бути більше 8 і не менше 60 символів")
    @NotBlank(message = "Пароль не повинен бути з пробілів")
    private String password;

    @Pattern(regexp = "\\d{10}",message = "Номер повинен складатися з чисел 10")
    private String number;
    @Size(min=5,max=60, message = "Пошта повинна бути більше 5 і не менше 60 символів")
    private String email;
    @Size(min=2,max=60, message = "Країна повинна бути більше 2 і не менше 60 символів")
    private String country;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }



    public UserForm(String username, String password, String number, String email, String country) {
        this.username = username;
        this.password = password;
        this.number = number;
        this.email = email;
        this.country = country;
    }

    public UserForm() {
    }


}
