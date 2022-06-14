package nure.knt.forms.filter;

import nure.knt.entity.enums.ConditionCommodity;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class FilterCourierTaskCore {
    private String city;
    private String describeTask;
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateRegistrationStart;
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateRegistrationEnd;
    private Integer numberOfFlyersStart;
    private Integer numberOfFlyersEnd;
    private Set<ConditionCommodity> conditionCommodities;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescribeTask() {
        return describeTask;
    }

    public void setDescribeTask(String describeTask) {
        this.describeTask = describeTask;
    }

    public LocalDateTime getDateRegistrationStart() {
        return dateRegistrationStart;
    }

    public void setDateRegistrationStart(LocalDateTime dateRegistrationStart) {
        this.dateRegistrationStart = dateRegistrationStart;
    }

    public LocalDateTime getDateRegistrationEnd() {
        return dateRegistrationEnd;
    }

    public void setDateRegistrationEnd(LocalDateTime dateRegistrationEnd) {
        this.dateRegistrationEnd = dateRegistrationEnd;
    }

    public Integer getNumberOfFlyersStart() {
        return numberOfFlyersStart;
    }

    public void setNumberOfFlyersStart(Integer numberOfFlyersStart) {
        this.numberOfFlyersStart = numberOfFlyersStart;
    }

    public Integer getNumberOfFlyersEnd() {
        return numberOfFlyersEnd;
    }

    public void setNumberOfFlyersEnd(Integer numberOfFlyersEnd) {
        this.numberOfFlyersEnd = numberOfFlyersEnd;
    }

    public Set<ConditionCommodity> getConditionCommodities() {
        return conditionCommodities;
    }

    public void setConditionCommodities(ConditionCommodity[] conditionCommodities) {
        this.conditionCommodities = Arrays.stream(conditionCommodities).collect(Collectors.toSet());
    }

    public FilterCourierTaskCore(String city, String describeTask, LocalDateTime dateRegistrationStart, LocalDateTime dateRegistrationEnd, Integer numberOfFlyersStart, Integer numberOfFlyersEnd, Set<ConditionCommodity> conditionCommodities) {
        this.city = city;
        this.describeTask = describeTask;
        this.dateRegistrationStart = dateRegistrationStart;
        this.dateRegistrationEnd = dateRegistrationEnd;
        this.numberOfFlyersStart = numberOfFlyersStart;
        this.numberOfFlyersEnd = numberOfFlyersEnd;
        this.conditionCommodities = conditionCommodities;
    }

    public FilterCourierTaskCore() {
        this.conditionCommodities = Set.of();
    }

    @Override
    public String toString() {
        return "FilterCourierTaskCore{" +
                "city='" + city + '\'' +
                ", describeTask='" + describeTask + '\'' +
                ", dateRegistrationStart=" + dateRegistrationStart +
                ", dateRegistrationEnd=" + dateRegistrationEnd +
                ", numberOfFlyersStart=" + numberOfFlyersStart +
                ", numberOfFlyersEnd=" + numberOfFlyersEnd +
                ", conditionCommodities=" + conditionCommodities +
                '}';
    }
}
