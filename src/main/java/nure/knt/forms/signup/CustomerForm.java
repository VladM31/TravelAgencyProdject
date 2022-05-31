package nure.knt.forms.signup;

import nure.knt.entity.enums.Role;
import nure.knt.entity.important.Customer;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class CustomerForm extends UserForm {
    @Size(min=2,max=50, message = "Ім'я повинне бути більше 2 і не менше 50 символів")
    private String firstname;
    @Size(min=2,max=50, message = "Прізвище повинне бути більше 2 і не менше 50 символів")
    private String lastname;
    private boolean male;

    public CustomerForm(String username, String password, String number, String email, String country, String firstname, String lastname, boolean male) {
        super(username, password, number, email, country);
        this.firstname = firstname;
        this.lastname = lastname;
        this.male = male;
    }

    public CustomerForm() {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public Customer toCustomer(){
        Customer cust = new Customer();

        cust.setUsername(super.getUsername());//3
        cust.setPassword(super.getPassword());//4
        cust.setEmail(super.getEmail());//2
        cust.setNumber(super.getNumber());//1
        cust.setCountry(super.getCountry());//9
        cust.setName(this.firstname + "/" + this.lastname);//5

        cust.setMale(this.male);//11
        cust.setActive(true);//6
        cust.setRole(Role.CUSTOMER);//10
        cust.setDateRegistration(LocalDateTime.now());//7

        return cust;
    }
}