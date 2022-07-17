package nure.knt.entity.goods;

import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.important.TravelAgency;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TourAd {
    private Long id;
    private Long travelAgencyId;

    private int costOneCustomer;
    private int costService;
    private int discountSizePeople;
    private int orderQuantity;

    private float discountPercentage;
    private float ratingAgency;

    private boolean hidden;

    private LocalDate dateStart;
    private LocalDate dateEnd;
    private LocalDateTime dateRegistration;

    private String place;
    private String city;
    private String country;
    private String nameAgency;

    private TypeState typeState;
    private ConditionCommodity conditionCommodity;

    public List<String> getStars(){
        return TravelAgency.getRetingStars(this.ratingAgency);
    }

    public long getTravelAgencyId() {
        return travelAgencyId;
    }

    public void setTravelAgencyId(long travelAgencyId) {
        this.travelAgencyId = travelAgencyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCostOneCustomer() {
        return costOneCustomer;
    }

    public void setCostOneCustomer(int costOneCustomer) {
        this.costOneCustomer = costOneCustomer;
    }

    public int getCostService() {
        return costService;
    }

    public void setCostService(int costService) {
        this.costService = costService;
    }

    public int getDiscountSizePeople() {
        return discountSizePeople;
    }

    public void setDiscountSizePeople(int discountSizePeople) {
        this.discountSizePeople = discountSizePeople;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public float getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(float discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public float getRatingAgency() {
        return ratingAgency;
    }

    public void setRatingAgency(float ratingAgency) {
        this.ratingAgency = ratingAgency;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public LocalDateTime getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(LocalDateTime dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNameAgency() {
        return nameAgency;
    }

    public void setNameAgency(String nameAgency) {
        this.nameAgency = nameAgency;
    }

    public TypeState getTypeState() {
        return typeState;
    }

    public void setTypeState(TypeState typeState) {
        this.typeState = typeState;
    }

    public ConditionCommodity getConditionCommodity() {
        return conditionCommodity;
    }

    public void setConditionCommodity(ConditionCommodity conditionCommodity) {
        this.conditionCommodity = conditionCommodity;
    }

    public TourAd(Long id, int costOneCustomer, int costService, int discountSizePeople, int orderQuantity, float discountPercentage, float ratingAgency, Long travelAgencyId, boolean hidden, LocalDate dateStart, LocalDate dateEnd, LocalDateTime dateRegistration, String place, String city, String country, String nameAgency, TypeState typeState, ConditionCommodity conditionCommodity) {
        this.id = id;
        this.costOneCustomer = costOneCustomer;
        this.costService = costService;
        this.discountSizePeople = discountSizePeople;
        this.orderQuantity = orderQuantity;
        this.discountPercentage = discountPercentage;
        this.ratingAgency = ratingAgency;
        this.travelAgencyId = travelAgencyId;
        this.hidden = hidden;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.dateRegistration = dateRegistration;
        this.place = place;
        this.city = city;
        this.country = country;
        this.nameAgency = nameAgency;
        this.typeState = typeState;
        this.conditionCommodity = conditionCommodity;
    }

    public TourAd() {
    }

    @Override
    public String toString() {
        return "TourAd{" +
                "id=" + id +
                ", costOneCustomer=" + costOneCustomer +
                ", costService=" + costService +
                ", discountSizePeople=" + discountSizePeople +
                ", orderQuantity=" + orderQuantity +
                ", discountPercentage=" + discountPercentage +
                ", ratingAgency=" + ratingAgency +
                ", travelAgencyId=" + travelAgencyId +
                ", hidden=" + hidden +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", dateRegistration=" + dateRegistration +
                ", place='" + place + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", nameAgency='" + nameAgency + '\'' +
                ", typeState=" + typeState +
                ", conditionCommodity=" + conditionCommodity +
                '}';
    }
}
