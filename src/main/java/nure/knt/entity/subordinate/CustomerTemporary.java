package nure.knt.entity.subordinate;

import nure.knt.entity.important.Customer;
import nure.knt.entity.enums.Role;
import nure.knt.forms.signup.CustomerForm;

import java.time.LocalDateTime;
import java.util.Objects;

public class CustomerTemporary {
    private Long idTemp;
    private Long idCustomer;
    private Long idTempCust;
    private Long number;
    private String email;
    private String username;
    private String password;
    private String surname;
    private String firstname;
    private String country;
    private Boolean isMale;
    private Boolean isUsed;
    private LocalDateTime dateRegistration;

    public Long getIdTempCust() {
        return idTempCust;
    }

    public void setIdTempCust(Long idTempCust) {
        this.idTempCust = idTempCust;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getIdTemp() {
        return idTemp;
    }

    public void setIdTemp(Long idTemp) {
        this.idTemp = idTemp;
    }

    public Long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Boolean getIsMale() {
        return isMale;
    }

    public void setIsMale(Boolean isMale) {
        this.isMale = isMale;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public LocalDateTime getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(LocalDateTime dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public CustomerTemporary(Long id_temp, Long id_customer, Long id_temp_cust, Long number, String email, String username, String password,
                             String surname, String firstname, String country, Boolean is_male,
                             Boolean isUsed, LocalDateTime dateRegistration) {
        this.idTemp = id_temp;
        this.idCustomer = id_customer;
        this.idTempCust = id_temp_cust;
        this.number = number;
        this.email = email;
        this.username = username;
        this.password = password;
        this.surname = surname;
        this.firstname = firstname;
        this.country = country;
        this.isMale = is_male;
        this.isUsed = isUsed;
        this.dateRegistration = dateRegistration;
    }

    public CustomerTemporary() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerTemporary that = (CustomerTemporary) o;

        return Objects.equals(idTemp, that.idTemp);
    }

    @Override
    public int hashCode() {
        return idTemp != null ? idTemp.hashCode() : 0;
    }

    public Customer toCustomer(){
        Customer newCustomer = new Customer();
        //**************** default
        newCustomer.setCustomerId(CustomerForm.getIdGenerator());
        newCustomer.setId(newCustomer.getCustomerId());
        newCustomer.setActive(true);
        newCustomer.setDateRegistration(LocalDateTime.now());
        newCustomer.setRole(Role.CUSTOMER);
        //**************** insert
        newCustomer.setNumber(""+this.number);
        newCustomer.setEmail(this.email);
        newCustomer.setUsername(this.username);
        newCustomer.setPassword(this.password);
        newCustomer.setCountry(this.country);
        newCustomer.setMale(this.isMale);
        newCustomer.setName(this.firstname + "/" + this.surname);
        return newCustomer;
    }

    @Override
    public String toString() {
        return "CustomerTemporary{" +
                "idTemp=" + idTemp +
                ", idCustomer=" + idCustomer +
                ", idTempCust=" + idTempCust +
                ", number=" + number +
                ", \nemail='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", surname='" + surname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", \ncountry='" + country + '\'' +
                ", isMale=" + isMale +
                ", isUsed=" + isUsed +
                ", dateRegistration=" + dateRegistration +
                '}';
    }
}
