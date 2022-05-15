package com.example.demo.entity.important;

import com.example.demo.entity.enums.Role;

import java.time.LocalDateTime;
import java.util.Objects;

public class TravelAgency extends User{
    private Long travelId;
    private float rating;
    private long kved;
    private Long egrpoy_or_rnekpn;
    private boolean isEgrpoy;
    private String address;
    private String allNameDirector;
    private String describeAgency;
    private boolean can_use;

    public boolean isCan_use() {
        return can_use;
    }

    public void setCan_use(boolean can_use) {
        this.can_use = can_use;
    }

    public Long getTravelId() {
        return travelId;
    }

    public void setTravelId(Long travelId) {
        this.travelId = travelId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public long getKved() {
        return kved;
    }

    public void setKved(long kved) {
        this.kved = kved;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAllNameDirector() {
        return allNameDirector;
    }

    public void setAllNameDirector(String allNameDirector) {
        this.allNameDirector = allNameDirector;
    }

    public String getDescribeAgency() {
        return describeAgency;
    }

    public void setDescribeAgency(String describeAgency) {
        this.describeAgency = describeAgency;
    }

    public Long getEgrpoy_or_rnekpn() {
        return egrpoy_or_rnekpn;
    }

    public void setEgrpoy_or_rnekpn(Long egrpoy_or_rnekpn) {
        this.egrpoy_or_rnekpn = egrpoy_or_rnekpn;
    }

    public boolean isEgrpoy() {
        return isEgrpoy;
    }

    public void setEgrpoy(boolean egrpoy) {
        isEgrpoy = egrpoy;
    }

    public TravelAgency(Long id, long number, String email, String username, String password, boolean active, LocalDateTime dateRegistration,
                        Role role, String country, String name, Long travelId, float rating, long kved, Long egrpoy_or_rnekpn,
                        boolean isEgrpoy, String address, String allNameDirector, String describeAgency, boolean can_use) {
        super(id, number, email, username, password, active, dateRegistration, role, country, name);
        this.travelId = travelId;
        this.rating = rating;
        this.kved = kved;
        this.egrpoy_or_rnekpn = egrpoy_or_rnekpn;
        this.isEgrpoy = isEgrpoy;
        this.address = address;
        this.allNameDirector = allNameDirector;
        this.describeAgency = describeAgency;
        this.can_use = can_use;
    }

    public TravelAgency(){
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof TravelAgency)) return false;
        if (!super.equals(o)) return false;

        TravelAgency that = (TravelAgency) o;

        return Objects.equals(this.travelId,that.travelId);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (travelId != null ? travelId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TravelAgency{" +
                "travelId=" + travelId +
                ", rating=" + rating +
                ", kved=" + kved +
                ", egrpoy_or_rnekpn=" + egrpoy_or_rnekpn +
                ", isEgrpoy=" + isEgrpoy +
                ", address='" + address + '\'' +
                ", allNameDirector='" + allNameDirector + '\'' +
                ", describeAgency='" + describeAgency + '\'' +
                ", can_use=" + can_use +
                ", name=" + super.getName() +
                "} " + super.toString();
    }
}
