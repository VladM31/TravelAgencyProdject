package nure.knt.entity.important;

import nure.knt.entity.enums.Role;
import nure.knt.entity.enums.TypeState;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Courier extends User {
    private Long idCourier;
    private String city;
    private String address;
    private LocalDate dateBirth;
    private boolean doesHeWant;

    public Long getIdCourier() {
        return idCourier;
    }

    public void setIdCourier(Long idCourier) {
        this.idCourier = idCourier;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }

    public boolean isDoesHeWant() {
        return doesHeWant;
    }

    public void setDoesHeWant(boolean doesHeWant) {
        this.doesHeWant = doesHeWant;
    }

    public Courier() {
    }

    public Courier(Long id, String number, String email, String username, String password, boolean active, LocalDateTime dateRegistration, Role role,
                   String country, String name, TypeState typeState,
                   Long idCourier, String city, String address, LocalDate dateBirth, boolean doesHeWant) {
        super(id, number, email, username, password, active, dateRegistration, role, country, name, typeState);
        this.idCourier = idCourier;
        this.city = city;
        this.address = address;
        this.dateBirth = dateBirth;
        this.doesHeWant = doesHeWant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Courier courier = (Courier) o;

        return idCourier.equals(courier.idCourier);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + idCourier.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Courier{" +
                "idCourier=" + idCourier +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", dateBirth=" + dateBirth +
                ", doesHeWant=" + doesHeWant +
                "} " + super.toString();
    }
}
