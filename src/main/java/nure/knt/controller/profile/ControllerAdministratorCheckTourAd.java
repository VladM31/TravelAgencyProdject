package nure.knt.controller.profile;

import nure.knt.controller.HandlerController;
import nure.knt.database.idao.goods.IDAOTourAd;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.goods.TourAd;
import nure.knt.entity.important.TravelAgency;
import nure.knt.entity.important.User;
import nure.knt.forms.filter.FilterTourAdAdminCheck;
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
@PropertySource("classpath:WorkerWithAdministrator.properties")
public class ControllerAdministratorCheckTourAd {

    private final String CHECK_TOUR_AD_URL;
    private final String CHECK_TOUR_AD_PAGE;

    @Autowired
    private IDAOTourAd<TourAd> daotourAd;

    public ControllerAdministratorCheckTourAd(@Value("${admin.profile.check.tour.ad.url}")String CHECK_TOUR_AD_URL,
                                              @Value("${admin.profile.check.tour.ad.page}")String CHECK_TOUR_AD_PAGE) {
        this.CHECK_TOUR_AD_URL = CHECK_TOUR_AD_URL;
        this.CHECK_TOUR_AD_PAGE = CHECK_TOUR_AD_PAGE;
    }

    @RequestMapping(value ="${admin.profile.check.tour.ad.url}", method = RequestMethod.GET)
    public String showAllTourAdsForCheck(@AuthenticationPrincipal User admin, Model model, FilterTourAdAdminCheck filter){

        model.addAttribute("tourAds", filter.filtering(daotourAd
                .where()
                .conditionCommodityIn(Set.of(ConditionCommodity.NOT_CONFIRMED))
                .typeStateIn(Set.of(TypeState.REGISTERED)), daotourAd));

model.addAttribute("admin",admin);
        HandlerController.setMenuModel(admin, model);

        model.addAttribute("filter", filter);


        return CHECK_TOUR_AD_PAGE;
    }

    @RequestMapping(value = "${admin.profile.check.tour.ad.confirmed.url}", method = RequestMethod.POST)
    public String confirmedTourAd(Integer valueCost, Long idTourAd){
       TourAd tourAd = daotourAd.findByTourAdId(idTourAd,
               daotourAd.where()
                       .conditionCommodityIn(Set.of(ConditionCommodity.NOT_CONFIRMED))
               .typeStateIn(Set.of(TypeState.REGISTERED)));
       if (tourAd==null){
           return "redirect:"+CHECK_TOUR_AD_URL;
       }

       tourAd.setCostService(valueCost);
       tourAd.setConditionCommodity(ConditionCommodity.CONFIRMED);
       daotourAd.updateConditionCommodityAndCostServiceById(tourAd);

        return "redirect:"+CHECK_TOUR_AD_URL;
    }

    @RequestMapping(value = "${admin.profile.check.tour.ad.canceled.url}", method = RequestMethod.POST)
    public String canceledTourAd(Long idTourAd) {

        TourAd tourAd = daotourAd.findByTourAdId(idTourAd,
                daotourAd.where()
                        .conditionCommodityIn(Set.of(ConditionCommodity.NOT_CONFIRMED))
                        .typeStateIn(Set.of(TypeState.REGISTERED)));
        if (tourAd == null) {
            return "redirect:" + CHECK_TOUR_AD_URL;
        }

        tourAd.setConditionCommodity(ConditionCommodity.CANCELED);
        daotourAd.updateConditionCommodityAndCostServiceById(tourAd);

        return "redirect:" + CHECK_TOUR_AD_URL;
    }





}
