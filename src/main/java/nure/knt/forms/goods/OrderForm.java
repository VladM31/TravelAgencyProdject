package nure.knt.forms.goods;

import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.goods.OrderFromTourAdForCustomer;
import nure.knt.entity.goods.TourAd;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.DAYS;

public class OrderForm {
    @Pattern(regexp = "^((?![/=;*+?!]).)*$",message = "Не повино в імені бути знаків \"/;-=*+?!\"")
    @Size(min=2,max=60, message = "Назва міста повина бути більше 2 і не менше 101 символів")
    @NotBlank(message = "Назва міста не повина бути з пробілами")
    private String city;
    @Min(value =1,message = "Кількість людей не повинно бути менше 1")
    @Max(value = 1000, message = "Кількість людей не повинно бути більше 1000")
    private Integer countPeople;
    @NotNull(message =" Введіть дату")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDateOrder, endDateOrder;
    @NotNull(message ="Помилка, напишіть адміністрації")
    private Float prosent;
    @NotNull(message ="Помилка, напишіть адміністрації")
    private Integer cost;
    @NotNull(message ="Помилка, напишіть адміністрації")
    private Integer numberOfPeopleMax;

    private Long tourAdId;

    private LocalDate constEndDateOrder;

    public OrderForm(String city, Integer countPeople, LocalDate startDateOrder,
                     LocalDate endDateOrder, Float prosent, Integer cost, Integer numberOfPeopleMax, LocalDate constEndDateOrder,Long tourAdId) {
        this.city = city;
        this.countPeople = countPeople;
        this.startDateOrder = startDateOrder;
        this.endDateOrder = endDateOrder;
        this.prosent = prosent;
        this.cost = cost;
        this.numberOfPeopleMax = numberOfPeopleMax;
        this.constEndDateOrder = constEndDateOrder;
        this.tourAdId = tourAdId;
    }

    public OrderForm(TourAd tourAd) {
        this.prosent = tourAd.getDiscountPercentage();
        this.cost = tourAd.getCostOneCustomer();
        this.numberOfPeopleMax = tourAd.getDiscountSizePeople();
        this.constEndDateOrder = tourAd.getDateEnd();
        this.tourAdId = tourAd.getId();
    }

    public OrderForm() {
    }

    public OrderFromTourAdForCustomer toOrder(Long customerId){
        OrderFromTourAdForCustomer order = new OrderFromTourAdForCustomer();

        order.setNumberOfPeople(this.countPeople);
        order.setCost(this.calculate());

        order.setDateEnd(this.endDateOrder);
        order.setDateStart(this.startDateOrder);
        order.setDateRegistration(LocalDateTime.now());

        order.setCity(this.city);
        order.setConditionCommodity(ConditionCommodity.NOT_CONFIRMED);

        order.setIdCustomer(customerId);
        order.setIdTourAd(this.tourAdId);

        return order;
    }

    public int calculate(){
        if(countPeople == null){
            return -1;
        }
        int numberPersons = ((this.countPeople < this.numberOfPeopleMax) ? countPeople :numberOfPeopleMax ) -1;

        int cost = this.cost * countPeople;
        float prosentNow = numberPersons * this.prosent;

        int costDelete = (int) (cost/100*prosentNow);

        cost -= costDelete;

        var temp = ((int)DAYS.between(startDateOrder, endDateOrder) + 1);

        return (cost * temp);
    }


    public LocalDate getConstEndDateOrder() {
        return constEndDateOrder;
    }

    public void setConstEndDateOrder(LocalDate constEndDateOrder) {
        this.constEndDateOrder = constEndDateOrder;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCountPeople() {
        return countPeople;
    }

    public void setCountPeople(int countPeople) {
        this.countPeople = countPeople;
    }

    public LocalDate getStartDateOrder() {
        return startDateOrder;
    }

    public void setStartDateOrder(LocalDate startDateOrder) {
        this.startDateOrder = startDateOrder;
    }

    public LocalDate getEndDateOrder() {
        return endDateOrder;
    }

    public void setEndDateOrder(LocalDate endDateOrder) {
        this.endDateOrder = endDateOrder;
    }

    public Float getProsent() {
        return prosent;
    }

    public void setProsent(float prosent) {
        this.prosent = prosent;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Integer getNumberOfPeopleMax() {
        return numberOfPeopleMax;
    }

    public void setNumberOfPeopleMax(int numberOfPeopleMax) {
        this.numberOfPeopleMax = numberOfPeopleMax;
    }

    public void setCountPeople(Integer countPeople) {
        this.countPeople = countPeople;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public void setNumberOfPeopleMax(Integer numberOfPeopleMax) {
        this.numberOfPeopleMax = numberOfPeopleMax;
    }

    public Long getTourAdId() {
        return tourAdId;
    }

    public void setTourAdId(Long tourAdId) {
        this.tourAdId = tourAdId;
    }
}
