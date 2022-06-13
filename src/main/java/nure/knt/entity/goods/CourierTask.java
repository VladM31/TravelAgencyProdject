package nure.knt.entity.goods;

import nure.knt.entity.enums.ConditionCommodity;

import java.time.LocalDateTime;

public class CourierTask {
    private Long id;
    private int numberOfFlyers;
    private String city;
    private Long idAdmin;
    private Long idCourier;

    public Long getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Long idAdmin) {
        this.idAdmin = idAdmin;
    }

    public Long getIdCourier() {
        return idCourier;
    }

    public void setIdCourier(Long idCourier) {
        this.idCourier = idCourier;
    }

    private String emailCourier;
    private String emailAdmin;
    private String nameCourier;
    private String nameAdmin;
    private String describeTask;
    private LocalDateTime dateRegistration;
    private ConditionCommodity conditionCommodity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumberOfFlyers() {
        return numberOfFlyers;
    }

    public void setNumberOfFlyers(int numberOfFlyers) {
        this.numberOfFlyers = numberOfFlyers;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmailCourier() {
        return emailCourier;
    }

    public void setEmailCourier(String emailCourier) {
        this.emailCourier = emailCourier;
    }

    public String getEmailAdmin() {
        return emailAdmin;
    }

    public void setEmailAdmin(String emailAdmin) {
        this.emailAdmin = emailAdmin;
    }

    public String getNameCourier() {
        return nameCourier;
    }

    public void setNameCourier(String nameCourier) {
        this.nameCourier = nameCourier;
    }

    public String getNameAdmin() {
        return nameAdmin;
    }

    public void setNameAdmin(String nameAdmin) {
        this.nameAdmin = nameAdmin;
    }

    public String getDescribeTask() {
        return describeTask;
    }

    public void setDescribeTask(String describeTask) {
        this.describeTask = describeTask;
    }

    public LocalDateTime getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(LocalDateTime dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public ConditionCommodity getConditionCommodity() {
        return conditionCommodity;
    }

    public void setConditionCommodity(ConditionCommodity conditionCommodity) {
        this.conditionCommodity = conditionCommodity;
    }

    public CourierTask(Long id, int numberOfFlyers, String city, Long idAdmin, Long idCourier, String emailCourier, String emailAdmin, String nameCourier, String nameAdmin, String describeTask, LocalDateTime dateRegistration, ConditionCommodity conditionCommodity) {
        this.id = id;
        this.numberOfFlyers = numberOfFlyers;
        this.city = city;
        this.idAdmin = idAdmin;
        this.idCourier = idCourier;
        this.emailCourier = emailCourier;
        this.emailAdmin = emailAdmin;
        this.nameCourier = nameCourier;
        this.nameAdmin = nameAdmin;
        this.describeTask = describeTask;
        this.dateRegistration = dateRegistration;
        this.conditionCommodity = conditionCommodity;
    }

    public CourierTask() {
    }
}
