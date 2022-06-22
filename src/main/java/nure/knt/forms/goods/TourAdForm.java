package nure.knt.forms.goods;

import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.goods.TourAd;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TourAdForm {
    @Min(value =1,message = "Ціна не повина бути менше 1")
    @Max(value = 1000000, message = "Ціна не повина бути більше 1000000")
    private Integer cost;
    @Min(value =0,message = "Знижка не повина бути менше 1")
    @Max(value = 100, message = "Знижка не повина бути більше 100")
    private Float prosent;
    @Min(value =0,message = "Кількість людей не повина бути менше 0")
    @Max(value = 10000, message = "Кількість людей не повина бути більше 10000")
    private Integer numberOfPeople;
    @Pattern(regexp = "^((?![/=;*+?!]).)*$",message = "Не повино в місці відпочинку бути знаків \"/;=*+?!\"")
    @Size(min=2,max=60, message = "Назва місця відпочинку повина бути більше 2 і не менше 60 символів")
    @NotBlank(message = "Назва місця відпочинку не повина бути з пробілами")
    private String place;
    @Pattern(regexp = "^((?![/=;*+?!]).)*$",message = "Не повино в назві країни відпочинку бути знаків \"/;=*+?!\"")
    @Size(min=2,max=60, message = "Назва країни повина бути більше 2 і не менше 60 символів")
    @NotBlank(message = "Назва назві країни не повина бути з пробілами")
    private String country;
    @Pattern(regexp = "^((?![/=;*+?!]).)*$",message = "Не повино в назві мітса бути знаків \"/;=*+?!\"")
    @Size(min=2,max=60, message = "Назва міста повина бути більше 2 і не менше 60 символів")
    @NotBlank(message = "Назва міста країни не повина бути з пробілами")
    private String city;
    @NotNull(message =" Введіть дату")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDateTourAd, endDateTourAd;

    private Long idTourAd;

    public TourAd toTourAd(Long travelAgencyId){
        TourAd tourAd = new TourAd();

        TourAdForm.setFormInsideTourAd(this,tourAd);

        tourAd.setCostService(0);
        tourAd.setOrderQuantity(0);
        tourAd.setRatingAgency(0.0f);
        tourAd.setTravelAgencyId(travelAgencyId);
        tourAd.setHidden(false);
        tourAd.setDateRegistration(LocalDateTime.now());
        tourAd.setConditionCommodity(ConditionCommodity.NOT_CONFIRMED);
        tourAd.setTypeState(TypeState.REGISTERED);

        return tourAd;
    }

    public TourAd toTourAd(TourAd tourAd){

        TourAdForm.setFormInsideTourAd(this,tourAd);

        tourAd.setTypeState(TypeState.EDITING);
        tourAd.setDateRegistration(LocalDateTime.now());

        return tourAd;
    }

    private static void setFormInsideTourAd(TourAdForm form, TourAd tourAd){
        tourAd.setCostOneCustomer(form.cost);
        tourAd.setDiscountPercentage(form.prosent);
        tourAd.setDiscountSizePeople(form.numberOfPeople);

        tourAd.setPlace(form.place);
        tourAd.setCountry(form.country);
        tourAd.setCity(form.city);

        tourAd.setDateStart(form.startDateTourAd);
        tourAd.setDateEnd(form.endDateTourAd);

    }

    public TourAdForm(Integer cost, Float prosent, Integer numberOfPeople, String place,
                      String country, String city, LocalDate startDateTourAd, LocalDate endDateTourAd) {
        this.cost = cost;
        this.prosent = prosent;
        this.numberOfPeople = numberOfPeople;
        this.place = place;
        this.country = country;
        this.city = city;
        this.startDateTourAd = startDateTourAd;
        this.endDateTourAd = endDateTourAd;
    }

    public TourAdForm(TourAd tourAd) {
        this(tourAd.getCostOneCustomer(),tourAd.getDiscountPercentage(),tourAd.getDiscountSizePeople(),tourAd.getPlace(),
                tourAd.getCountry(),tourAd.getCity(),tourAd.getDateStart(),tourAd.getDateEnd());
        this.idTourAd = tourAd.getId();
    }

    public TourAdForm() {
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Float getProsent() {
        return prosent;
    }

    public void setProsent(Float prosent) {
        this.prosent = prosent;
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getStartDateTourAd() {
        return startDateTourAd;
    }

    public void setStartDateTourAd(LocalDate startDateTourAd) {
        this.startDateTourAd = startDateTourAd;
    }

    public LocalDate getEndDateTourAd() {
        return endDateTourAd;
    }

    public void setEndDateTourAd(LocalDate endDateTourAd) {
        this.endDateTourAd = endDateTourAd;
    }

    public Long getIdTourAd() {
        return idTourAd;
    }

    public void setIdTourAd(Long idTourAd) {
        this.idTourAd = idTourAd;
    }

    @Override
    public String toString() {
        return "TourAdForm{" +
                "cost=" + cost +
                ", prosent=" + prosent +
                ", numberOfPeople=" + numberOfPeople +
                ", place='" + place + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", startDateTourAd=" + startDateTourAd +
                ", endDateTourAd=" + endDateTourAd +
                '}';
    }
}
