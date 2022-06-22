package nure.knt.forms.entities;

import nure.knt.entity.enums.Role;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.important.TravelAgency;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class TravelAgencyForm extends UserForm {
    @Pattern(regexp = "^((?![/;-=*+?!]).)*$",message = "Не повино в назві тур агенції бути знаків \"/;-=*+?!\"")
    @Size(min=2,max=101, message = "Назва тур агенції повина бути більше 2 і не менше 101 символів")
    @NotBlank(message = "Логін не повинен бути з пробілів")
    private String nameTravelAgency;// Назва турагенції

    @Pattern(regexp = "^((?![/=*+?!]).)*$",message = "Не повино в КВЕДі бути знаків \"/-=*+?!\"")
    @Size(min=2,max=10, message = "КВЕД повинен бути більше 2 і не менше 10 символів")
    @NotBlank(message = "КВЕД не повинен бути з пробілів")
    private String KVED;//КВЕД

    @Pattern(regexp = "^((?![/-=*+?!]).)*$",message = "Не повино в адресі турагенції бути знаків \"/-=*+?!\"")
    @Size(min=2,max=100, message = "Адрес турагенції повинен бути більше 2 і не менше 100 символів")
    @NotBlank(message = "Адрес турагенції не повинен бути з пробілів")
    private String addressTravelAgency;// Адреса офісу турагенції

    @Pattern(regexp = "^[0-9]+$",message = "ЕГРПОУ або РНУКПН повинен складатися з чисел")
    @Size(min=8,max=15, message = "ЕГРПОУ або РНУКПН повинен бути більше 8 і не менше 15 символів")
    @NotBlank(message = "ЕГРПОУ або РНУКПН не повинен бути з пробілів")
    private String EGRPOYorRNYKPN;// ЕГРПОУ або РНУКПН

    private boolean EGRPOY;// ЕГРПОУ/РНУКПН

    @Pattern(regexp = "^((?![/;-=*+?!]).)*$",message = "Не повино в імені голови агенції бути знаків \"/;-=*+?!\"")
    @Size(min=3,max=150, message = "Повне ім'я голови агенції повине бути більше 3 і не менше 150 символів")
    @NotBlank(message = "овне ім'я голови агенції не повине бути з пробілів")
    private String nameHeadAgency;// Голова турагенції (ПІБ)


    @Size(max=1000, message = "URL фото повине бути більше 1000 символів")
    private String urlPhoto;//

    @Size(max=1000, message = "Опис агенції повине бути більше 1000 символів")
    private String describeAgency;//

    public String getNameTravelAgency() {
        return nameTravelAgency;
    }

    public void setNameTravelAgency(String nameTravelAgency) {
        this.nameTravelAgency = nameTravelAgency;
    }

    public String getAddressTravelAgency() {
        return addressTravelAgency;
    }

    public void setAddressTravelAgency(String addressTravelAgency) {
        this.addressTravelAgency = addressTravelAgency;
    }

    public String getKVED() {
        return KVED;
    }

    public void setKVED(String KVED) {
        this.KVED = KVED;
    }

    public String getEGRPOYorRNYKPN() {
        return EGRPOYorRNYKPN;
    }

    public void setEGRPOYorRNYKPN(String EGRPOYorRNYKPN) {
        this.EGRPOYorRNYKPN = EGRPOYorRNYKPN;
    }

    public boolean isEGRPOY() {
        return EGRPOY;
    }

    public void setEGRPOY(boolean EGRPOY) {
        this.EGRPOY = EGRPOY;
    }

    public String getNameHeadAgency() {
        return nameHeadAgency;
    }

    public void setNameHeadAgency(String nameHeadAgency) {
        this.nameHeadAgency = nameHeadAgency;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getDescribeAgency() {
        return describeAgency;
    }

    public void setDescribeAgency(String describeAgency) {
        this.describeAgency = describeAgency;
    }

    public TravelAgencyForm(String username, String password, String number, String email,
                            String country, String nameTravelAgency, String addressTravelAgency,
                            String KVED, String EGRPOYorRNYKPN, boolean whatChoose, String nameHeadAgency,
                            String urlPhoto, String describeAgency) {
        super(username, password, number, email, country);
        this.nameTravelAgency = nameTravelAgency;
        this.addressTravelAgency = addressTravelAgency;
        this.KVED = KVED;
        this.EGRPOYorRNYKPN = EGRPOYorRNYKPN;
        this.EGRPOY = whatChoose;
        this.nameHeadAgency = nameHeadAgency;
        this.urlPhoto = urlPhoto;
        this.describeAgency = describeAgency;
    }

    public TravelAgencyForm() {
    }

    public TravelAgency toTravelAgency(){
        TravelAgency travelAgency = new TravelAgency();

        setFormInsideTravelAgency(travelAgency,this);

        travelAgency.setUsername(super.getUsername());
        travelAgency.setPassword(super.getPassword());
        travelAgency.setActive(true);
        travelAgency.setRole(Role.TRAVEL_AGENCY);
        travelAgency.setDateRegistration(LocalDateTime.now());
        travelAgency.setTypeState(TypeState.REGISTRATION);

        return travelAgency;
    }

    public TravelAgency toTravelAgency(TravelAgency travelAgency){
        setFormInsideTravelAgency(travelAgency,this);
        return travelAgency;
    }

    private static void setFormInsideTravelAgency(TravelAgency travelAgency,TravelAgencyForm form){

        travelAgency.setEmail(form.getEmail());//2
        travelAgency.setNumber(form.getNumber());//1
        travelAgency.setCountry(form.getCountry());//9

        travelAgency.setName(form.nameTravelAgency);
        travelAgency.setFullNameDirector(form.nameHeadAgency);

        travelAgency.setDescribeAgency(form.describeAgency);
        travelAgency.setAddress(form.addressTravelAgency);
        travelAgency.setUrlPhoto(form.urlPhoto);

        travelAgency.setKved(form.KVED);
        travelAgency.setEgrpoyOrRnekpn(Long.valueOf(form.EGRPOYorRNYKPN));
        travelAgency.setEgrpoy(form.EGRPOY);

    }

    public TravelAgencyForm setFieldFromCustomer(TravelAgency travelAgency){
        super.setCountry(travelAgency.getCountry());
        super.setEmail(travelAgency.getEmail());
        super.setNumber(travelAgency.getNumber());

        super.setUsername("here_must_be_username");
        super.setPassword("here_must_be_password");

        this.addressTravelAgency = travelAgency.getAddress();
        this.nameTravelAgency = travelAgency.getName();
        this.nameHeadAgency = travelAgency.getFullNameDirector();

        this.KVED = travelAgency.getKved();
        this.EGRPOYorRNYKPN = travelAgency.getEgrpoyOrRnekpn().toString();
        this.EGRPOY = travelAgency.isEgrpoy();

        this.urlPhoto = travelAgency.getUrlPhoto();
        this.describeAgency = travelAgency.getDescribeAgency();

        return this;
    }

    @Override
    public String toString() {
        return "TravelAgencyForm{" +
                "nameTravelAgency='" + nameTravelAgency + '\'' +
                ", addressTravelAgency='" + addressTravelAgency + '\'' +
                ", KVED='" + KVED + '\'' +
                ", EGRPOYorRNYKPN='" + EGRPOYorRNYKPN + '\'' +
                ", whatChoose=" + EGRPOY +
                ", nameHeadAgency='" + nameHeadAgency + '\'' +
                ", urlPhoto='" + urlPhoto + '\'' +
                ", describeAgency='" + describeAgency + '\'' +
                "} " + super.toString();
    }
}
