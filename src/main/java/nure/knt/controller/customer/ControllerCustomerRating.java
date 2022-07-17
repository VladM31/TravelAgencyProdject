package nure.knt.controller.customer;

import nure.knt.database.idao.goods.IDAOOrderFromTourAdCustomer;
import nure.knt.database.idao.goods.IDAOTourAd;
import nure.knt.database.idao.temporary.IDAOSetRating;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.goods.OrderFromTourAdForCustomer;
import nure.knt.entity.goods.TourAd;
import nure.knt.entity.important.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Set;

@Controller
@PropertySource("classpath:WorkerWithCustomer.properties")
public class ControllerCustomerRating {

    @Autowired
    private IDAOOrderFromTourAdCustomer<OrderFromTourAdForCustomer> daoOrder;
    @Autowired
    private IDAOSetRating daoSetRating;

    private final String PAGE_RATING;
    private final String URL_RATING;
    private final String URL_SHOW_ALL_TOUR_ADS;

    public ControllerCustomerRating(@Value("${customer.to.evaluate.page}") String PAGE_RATING,
                                    @Value("${customer.to.evaluate.url}") String URL_RATING,
                                    @Value("${main.pages.find.all.url}") String url_show_all_tour_ads) {
        this.PAGE_RATING = PAGE_RATING;
        this.URL_RATING = URL_RATING;
        URL_SHOW_ALL_TOUR_ADS = url_show_all_tour_ads;
    }

    @RequestMapping(value = "${customer.to.evaluate.url}",method = RequestMethod.GET)
    public String showPageForRating(@AuthenticationPrincipal Customer customer, Model model,Long idTourAd){


        if(!daoOrder.hasOrder(customer.getCustomerId(),idTourAd)){
            return "redirect:" + URL_SHOW_ALL_TOUR_ADS;
        }

        model.addAttribute("valueTourAd",idTourAd);

        return PAGE_RATING;
    }

    @RequestMapping(value = "${customer.to.evaluate.url}",method = RequestMethod.POST)
    public String setRating(@AuthenticationPrincipal Customer customer, Model model,Long idTourAd,Integer theEvaluate){


        if(!daoOrder.hasOrder(customer.getCustomerId(),idTourAd)){
            return "redirect:" + URL_SHOW_ALL_TOUR_ADS;
        }

        if(daoSetRating.hasRating(customer.getCustomerId(),idTourAd)){
            daoSetRating.updateRating(customer.getCustomerId(),idTourAd,theEvaluate);
        }else{
            daoSetRating.insertRating(customer.getCustomerId(),idTourAd,theEvaluate);
        }

        return "redirect:" + URL_SHOW_ALL_TOUR_ADS;
    }
}
