package nure.knt.forms.goods;

import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.goods.CourierTask;
import nure.knt.entity.important.Courier;

import java.time.LocalDateTime;
import java.util.List;

public class CourierTaskForm {
    private String infoCourier;
    private String describe;
    private Integer numberOfFlyers;

    @Override
    public String toString() {
        return "CourierTaskForm{" +
                "infoCourier='" + infoCourier + '\'' +
                ", describe='" + describe + '\'' +
                ", numberOfFlyers=" + numberOfFlyers +
                '}';
    }

    private static final String INFO = "id=%d, країна %s, m. %s, адреса %s, дата народження %s;";

    public static String[] listToArray(List <Courier> list){
        String[] arrayInfoCourier = new String[list.size()];
        int i = 0;
        for (Courier courier:list) {
            arrayInfoCourier[i++] = String.format(INFO, courier.getIdCourier(), courier.getCountry(), courier.getCity(), courier.getAddress(), courier.getDateBirth().toString());
        }
        return arrayInfoCourier;
    }

    public String getInfoCourier() {
        return infoCourier;
    }

    public void setInfoCourier(String infoCourier) {
        this.infoCourier = infoCourier;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getNumberOfFlyers() {
        return numberOfFlyers;
    }

    public void setNumberOfFlyers(Integer numberOfFlyers) {
        this.numberOfFlyers = numberOfFlyers;
    }

    public static Long getCourierId(String info) {
        String strNow = info.substring(3,info.indexOf(","));
        return Long.parseLong(strNow);
    }

    public CourierTask toCourierTask(Courier courier, Long idAdmin) {
        CourierTask task = new CourierTask();


        task.setDescribeTask(this.describe);
        task.setNumberOfFlyers(this.numberOfFlyers);
        task.setDateRegistration(LocalDateTime.now());
        task.setCity(courier.getCity());
        task.setIdAdmin(idAdmin);
        task.setIdCourier(courier.getIdCourier());
        task.setConditionCommodity(ConditionCommodity.NOT_CONFIRMED);


        return task;
    }
}
