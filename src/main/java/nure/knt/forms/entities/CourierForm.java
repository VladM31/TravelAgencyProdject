package nure.knt.forms.entities;

import nure.knt.entity.enums.Role;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.important.Courier;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CourierForm extends UserForm {

    @Pattern(regexp = "^((?![/-=;*+?!]).)*$",message = "Не повино в імені бути знаків \"/;-=*+?!\"")
    @Size(min=2,max=101, message = "Ім'я повинно бути більше 2 і не менше 101 символів")
    @NotBlank(message = "Ім'я не повинен бути з пробілами")
    private String name;

    @Pattern(regexp = "^((?![/-/=*+?!]).)*$",message = "Не повино в адресі бути знаків \"/-=*+?!\"")
    @Size(min=2,max=100, message = "В адресі повинно бути більше 2 і не менше 100 символів")
    @NotBlank(message = "Адреса не повинна бути з пробілами")
    private String address;

    @Pattern(regexp = "^((?![/-=;*+?!]).)*$",message = "Не повино в місті бути знаків \"/;-=*+?!\"")
    @Size(min=2,max=60, message = "Місто повинно бути більше 2 і не менше 60 символів")
    @NotBlank(message = "Місто не повинно бути з пробілами")
    private String city;

    @NotNull(message =" Введіть дату")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }


    public CourierForm() {
    }

    public CourierForm(String username, String password, String number, String email, String country, String name, String address, String city, LocalDate birthDate) {
        super(username, password, number, email, country);
        this.name = name;
        this.address = address;
        this.city = city;
        this.birthDate = birthDate;
    }

    public CourierForm(Courier courier) {
        this(courier.getName(),courier.getName(),courier.getNumber(),courier.getEmail(),courier.getCountry(),
                courier.getName(),courier.getAddress(),courier.getCity(),courier.getDateBirth());
    }

    public Courier toCourier(){
        Courier courier = new Courier();

        CourierForm.formToCourier(this,courier);

        courier.setUsername(super.getUsername());
        courier.setPassword(super.getPassword());
        courier.setActive(true);
        courier.setDateRegistration(LocalDateTime.now());
        courier.setRole(Role.COURIER);
        courier.setTypeState(TypeState.REGISTRATION);

        return courier;
    }

    public Courier toCourier(Courier courier){

        return courier;
    }

    private static void formToCourier(CourierForm courierForm,Courier courier){
        courier.setNumber(courierForm.getNumber());
        courier.setEmail(courierForm.getEmail());
        courier.setCountry(courierForm.getCountry());

        courier.setName(courierForm.name);
        courier.setCity(courierForm.city);
        courier.setAddress(courierForm.address);
        courier.setDateBirth(courierForm.birthDate);
    }

    @Override
    public String toString() {
        return "CourierForm{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", birthDate=" + birthDate +
                '}'+super.toString();
    }
}
