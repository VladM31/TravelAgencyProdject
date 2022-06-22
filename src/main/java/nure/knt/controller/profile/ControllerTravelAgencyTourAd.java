package nure.knt.controller.profile;

import nure.knt.database.idao.goods.IDAOTourAd;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.goods.TourAd;
import nure.knt.entity.important.TravelAgency;
import nure.knt.forms.filter.HandlerFilter;
import nure.knt.forms.goods.TourAdForm;
import nure.knt.tools.WorkWithCountries;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Controller
@PropertySource("classpath:WorkerWithTravelAgency.properties")
public class ControllerTravelAgencyTourAd {

    private IDAOTourAd<TourAd> daoTourAd;
    private WorkWithCountries countries;

    private final String PAGE_CREATE_OR_CHANGE_TOUR_AD;
    private final String URL_BACK_AT_PROFILE;


    public ControllerTravelAgencyTourAd(IDAOTourAd<TourAd> daoTourAd,
                                        WorkWithCountries countries,
                                        @Value("${travel.agency.create.tour.ad.page}") String PAGE_CREATE_OR_CHANGE_TOUR_AD,
                                        @Value("${travel.agency.profile.tour.ad.url}") String URL_BACK_AT_PROFILE) {
        this.daoTourAd = daoTourAd;
        this.countries = countries;
        this.PAGE_CREATE_OR_CHANGE_TOUR_AD = PAGE_CREATE_OR_CHANGE_TOUR_AD;
        this.URL_BACK_AT_PROFILE = URL_BACK_AT_PROFILE;
    }

    private static final String UP_LABEL_CREATE = "ДОДАВАННЯ ПОСЛУГИ";
    private static final String UP_LABEL_CHANGE = "РЕДАГУВАННЯ ПОСЛУГИ";

    @RequestMapping(value = "${travel.agency.create.tour.ad.url}", method = RequestMethod.GET)
    public String showFormForCreateTourAd(Model model){
        this.setFormOnPage(model,new TourAdForm(),UP_LABEL_CREATE,LocalDate.now(), HandlerFilter.MAX_DATE_TIME);
        return PAGE_CREATE_OR_CHANGE_TOUR_AD;
    }

    @RequestMapping(value = "${travel.agency.create.tour.ad.url}", method = RequestMethod.POST)
    public String showFormForCreateTourAd(@AuthenticationPrincipal TravelAgency travelAgency, Model model, @Valid TourAdForm form, @NotNull BindingResult bindingResult){

        if(this.hasErrorOnFormPage(model, form, bindingResult)){
            this.setFormOnPage(model,form,UP_LABEL_CREATE,LocalDate.now(), HandlerFilter.MAX_DATE_TIME);
            return PAGE_CREATE_OR_CHANGE_TOUR_AD;
        }

        this.daoTourAd.save(form.toTourAd(travelAgency.getTravelId()));
        return "redirect:" + URL_BACK_AT_PROFILE;
    }

    @RequestMapping(value = "${travel.agency.change.tour.ad.url}", method = RequestMethod.GET)
    public String showFormForChangeTourAd(@AuthenticationPrincipal TravelAgency travelAgency,Model model,Long idTourAd){
        TourAd tourAd = this.daoTourAd.findByTourAdId(idTourAd,this.daoTourAd.where()
                .idTravelAgencyIs(travelAgency.getTravelId())
                .conditionCommodityIn(Set.of(ConditionCommodity.CONFIRMED,ConditionCommodity.NOT_CONFIRMED))
                .typeStateIn(Set.of(TypeState.REGISTERED)));

        if(tourAd == null){
            return "redirect:" + URL_BACK_AT_PROFILE;
        }

        this.setFormOnPage(model,new TourAdForm(tourAd),UP_LABEL_CHANGE,tourAd.getDateStart(), HandlerFilter.MAX_DATE_TIME);
        return PAGE_CREATE_OR_CHANGE_TOUR_AD;
    }

    @RequestMapping(value = "${travel.agency.change.tour.ad.url}", method = RequestMethod.POST)
    public String changeTourAd(@AuthenticationPrincipal TravelAgency travelAgency,Model model,@Valid TourAdForm form, @NotNull BindingResult bindingResult){

        TourAd tourAd = this.daoTourAd.findByTourAdId(form.getIdTourAd(),this.daoTourAd.where()
                .idTravelAgencyIs(travelAgency.getTravelId())
                .conditionCommodityIn(Set.of(ConditionCommodity.CONFIRMED,ConditionCommodity.NOT_CONFIRMED))
                .typeStateIn(Set.of(TypeState.REGISTERED)));

        if(tourAd == null){
            model.addAttribute("error_message","Вибрана не ваша послуга або вона не має доступу до редагування");
            this.setFormOnPage(model,form,UP_LABEL_CHANGE,LocalDate.now(), HandlerFilter.MAX_DATE_TIME);
            return PAGE_CREATE_OR_CHANGE_TOUR_AD;
        }

        if(this.hasErrorOnFormPage(model, form, bindingResult)){
            this.setFormOnPage(model,form,UP_LABEL_CHANGE,LocalDate.now(), HandlerFilter.MAX_DATE_TIME);
            return PAGE_CREATE_OR_CHANGE_TOUR_AD;
        }


        this.daoTourAd.editing(tourAd.getId(),form.toTourAd(tourAd));

        return "redirect:" + URL_BACK_AT_PROFILE;
    }



    private boolean hasErrorOnFormPage(Model model,TourAdForm form,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.addAttribute("error_message",bindingResult.getAllErrors().get(0).getDefaultMessage());
            return true;
        }

        if (countries.getIdByCountry(form.getCountry()) == WorkWithCountries.NAME_NOT_FOUND){
            model.addAttribute("error_message","Країна не знайдена");
            return true;
        }

        if (form.getStartDateTourAd().isAfter(form.getEndDateTourAd())){
            model.addAttribute("error_message","Дата в неправильному порядку");
            return true;
        }

        return false;
    }

    private void setFormOnPage(Model model, TourAdForm form, String upLabel, LocalDate minDate, LocalDate maxDate){
        model.addAttribute("form",form);
        model.addAttribute("label_text",upLabel);
        model.addAttribute("countries",countries.getCountry());
        model.addAttribute("min_date",minDate);
        model.addAttribute("max_date",maxDate);
        model.addAttribute("itIsCreate",upLabel.equals(UP_LABEL_CREATE));
    }
}
