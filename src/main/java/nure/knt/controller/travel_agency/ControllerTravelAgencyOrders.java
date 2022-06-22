package nure.knt.controller.travel_agency;

import nure.knt.controller.HandlerController;
import nure.knt.database.idao.goods.IDAOOrderFromTourAdTravelAgency;
import nure.knt.database.idao.goods.IDAOTourAd;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.goods.OrderFromTourAdForTravelAgency;
import nure.knt.entity.goods.TourAd;
import nure.knt.entity.important.TravelAgency;
import nure.knt.forms.filter.FilterOrdersForTravelAgency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.function.Function;

@Controller
@PropertySource("classpath:WorkerWithMessage.properties")
@PropertySource("classpath:WorkerWithTravelAgency.properties")
public class ControllerTravelAgencyOrders {
    @Autowired
    private IDAOOrderFromTourAdTravelAgency<OrderFromTourAdForTravelAgency> daoOrder;
    @Autowired
    private IDAOTourAd<TourAd> daoTourAd;

    private final String PAGE_SHOW_ALL_ORDERS;
    private final String URL_SHOW_ALL_ORDERS;
    private final String URL_SHOW_ALL_TOUR_AD;

    public ControllerTravelAgencyOrders(@Value("${travel.agency.show.orders.by.tour.ad.id.page}") String page_show_all_orders,
                                        @Value("${travel.agency.show.orders.by.tour.ad.id.url}") String url_show_all_orders,
                                        @Value("/") String url_show_all_tour_ad) {
        PAGE_SHOW_ALL_ORDERS = page_show_all_orders;
        URL_SHOW_ALL_ORDERS = url_show_all_orders;
        URL_SHOW_ALL_TOUR_AD = url_show_all_tour_ad;
    }

    @RequestMapping(value ="${travel.agency.show.orders.by.tour.ad.id.url}", method = RequestMethod.GET)
    public String showAllOrdersByTourAdId(@AuthenticationPrincipal TravelAgency agency, Model model, Long tourAdId, FilterOrdersForTravelAgency filter){


       TourAd tourAd = daoTourAd.findByTourAdId(tourAdId,daoTourAd.where().idTravelAgencyIs(agency.getTravelId()));
        if(tourAd==null){
            return "redirect:" + URL_SHOW_ALL_TOUR_AD;
        }
        model.addAttribute("stars",agency.getStars());
        HandlerController.setMenuModel(agency,model);
        model.addAttribute("tourAd", tourAd);
        model.addAttribute("getterNameMale",HandlerControllerTravelAgencyOrders.getNameMale());
        model.addAttribute("filter",filter);
        model.addAttribute("orders",filter.filtering(tourAd.getId(),daoOrder));
        model.addAttribute("getColor",HandlerControllerTravelAgencyOrders.getColorDependingOnTheCondition());
        model.addAttribute("canCanceled",HandlerControllerTravelAgencyOrders.showButtonCanceled());
        model.addAttribute("canConfirmed",HandlerControllerTravelAgencyOrders.showButtonConfirmed());
        return  PAGE_SHOW_ALL_ORDERS;
    }

    @RequestMapping(value = "${travel.agency.set.condition.to.canceled.for.order.url}", method = RequestMethod.POST)
    public String setConditionCanceledForOrder(@AuthenticationPrincipal TravelAgency agency,Long tourAdId,Long orderId){
        OrderFromTourAdForTravelAgency order = daoOrder.findByTravelAgencyIdAndTourAdIdAndOrderId(agency.getTravelId(),tourAdId,orderId);

        if(!(order == null || !HandlerControllerTravelAgencyOrders.showButtonCanceled().apply(order.getConditionCommodity()))){
            daoOrder.updateConditionCommodity(order.getId(),ConditionCommodity.CANCELED);
        }

        return "redirect:"+URL_SHOW_ALL_ORDERS;
    }

    @RequestMapping(value = "${travel.agency.set.condition.to.confirmed.for.order.url}", method = RequestMethod.POST)
    public String setConditionConfirmedForOrder(@AuthenticationPrincipal TravelAgency agency,Long tourAdId,Long orderId){
        OrderFromTourAdForTravelAgency order = daoOrder.findByTravelAgencyIdAndTourAdIdAndOrderId(agency.getTravelId(),tourAdId,orderId);

        if(!(order == null || !HandlerControllerTravelAgencyOrders.showButtonConfirmed().apply(order.getConditionCommodity()))){
            daoOrder.updateConditionCommodity(order.getId(),ConditionCommodity.CONFIRMED);
        }

        return "redirect:"+URL_SHOW_ALL_ORDERS;
    }
}

class HandlerControllerTravelAgencyOrders {
    public static Function<Boolean, String> getNameMale() {
        return (male) -> (male) ? "Чоловік" : "Жінка";
    }

    public static Function<ConditionCommodity, String> getColorDependingOnTheCondition() {
        return (condition) -> {
            switch (condition) {
                case NOT_CONFIRMED:
                    return "active";
                case CONFIRMED:
                    return "accepted";
                case CANCELED:
                    return "canceled";
                case GONE:
                    return "used";
            }
            return "";
        };
    }

    public static Function<ConditionCommodity, Boolean> showButtonCanceled(){
        return (condition) -> condition.equals(ConditionCommodity.NOT_CONFIRMED) || condition.equals(ConditionCommodity.CONFIRMED);
    }

    public static Function<ConditionCommodity, Boolean> showButtonConfirmed(){
        return (condition) -> condition.equals(ConditionCommodity.NOT_CONFIRMED);
    }
}
