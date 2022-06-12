package nure.knt.forms.signup;

import nure.knt.entity.enums.Role;
import nure.knt.entity.important.Customer;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class CustomerForm extends UserForm {

    @Size(min=2,max=50, message = "Ім'я повинне бути більше 2 і не менше 50 символів")
    @Pattern(regexp = "^((?![/;-=*+?!]).)*$",message = "Не повинно в імені бути знаку \"/\"")
    private String firstname;
    @Size(min=2,max=50, message = "Прізвище повинне бути більше 2 і не менше 50 символів")
    @Pattern(regexp = "^((?![/;-=*+?!]).)*$",message = "Не повинно в прізвище бути знаку \"/\"")
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
        Customer customer = new Customer();

        CustomerForm.setFormInsideCustomer(this,customer);

        customer.setMale(this.male);//11
        customer.setActive(true);//6
        customer.setRole(Role.CUSTOMER);//10
        customer.setPassword(super.getPassword());//4
        customer.setDateRegistration(LocalDateTime.now());//7

        return customer;
    }

    public Customer toCustomer(Customer customer){
        CustomerForm.setFormInsideCustomer(this,customer);
        return customer;
    }

    public void setFieldFromCustomer(Customer customer){
        super.setCountry(customer.getCountry());
        super.setEmail(customer.getEmail());
        super.setNumber(customer.getNumber());
        super.setUsername(customer.getUsername());
        this.lastname = customer.getFirstName();
        this.firstname = customer.getFirstName();
        this.male = customer.isMale();
    }

    private static void setFormInsideCustomer(CustomerForm form,Customer customer){
        customer.setUsername(form.getUsername());//3

        customer.setEmail(form.getEmail());//2
        customer.setNumber(form.getNumber());//1
        customer.setCountry(form.getCountry());//9
        customer.setName(form.firstname + "/" + form.lastname);//5

        customer.setMale(form.male);//11

    }

}