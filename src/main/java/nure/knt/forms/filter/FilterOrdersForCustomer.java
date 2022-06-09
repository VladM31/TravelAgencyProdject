package nure.knt.forms.filter;

public class FilterOrdersForCustomer  extends  FilterOrdersCore{
    private String nameTravelAgency;
    private String restingPlace;
    //+
    public String getNameTravelAgency() {
        return nameTravelAgency;
    }
    //+
    public void setNameTravelAgency(String nameTravelAgency) {
        this.nameTravelAgency = nameTravelAgency;
    }
    //+
    public String getRestingPlace() {
        return restingPlace;
    }
    //+
    public void setRestingPlace(String restingPlace) {
        this.restingPlace = restingPlace;
    }

    @Override
    public String toString() {
        return "FilterOrdersForCustomer{" +
                "nameTravelAgency='" + nameTravelAgency + '\'' +
                ", restingPlace='" + restingPlace + '\'' +
                "} " + super.toString();
    }
}
