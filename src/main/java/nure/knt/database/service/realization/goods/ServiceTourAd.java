package nure.knt.database.service.realization.goods;

import nure.knt.database.idao.goods.IDAOTourAdWithTerms;
import nure.knt.database.idao.terms.ITermCore;
import nure.knt.database.idao.terms.ITermTourAd;
import nure.knt.database.service.implement.goods.IServiceTourAd;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.goods.TourAd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PropertySource("classpath:property/goods/WorkerWithTourAds.properties")
public class ServiceTourAd implements IServiceTourAd<TourAd> {

    private IDAOTourAdWithTerms<TourAd> dao;

    @Value("${dao.tour.ad.for.service}")
    private String daoBeanName;

    @Autowired
    public void setDao(ApplicationContext context) {
        this.dao = context.getBean(daoBeanName,IDAOTourAdWithTerms.class);
    }

    @Override
    public boolean editing(Long id, TourAd entity) {
        return false;
    }

    @Override
    public boolean saveEdit(Long id) {
        return IServiceTourAd.super.saveEdit(id);
    }

    @Override
    public boolean removeEdit(Long id) {
        return IServiceTourAd.super.removeEdit(id);
    }

    @Override
    public boolean saveAll(Iterable<TourAd> entities) {
        return false;
    }

    @Override
    public boolean save(TourAd entity) {
        return false;
    }

    @Override
    public boolean updateTypeStateById(Long id, TypeState typeState) {
        return false;
    }

    @Override
    public boolean updateConditionCommodityAndCostServiceById(TourAd tourAd) {
        return false;
    }

    @Override
    public ITermTourAd term() {
        return null;
    }

    @Override
    public List<TourAd> findByTerms(ITermCore iTermCore) {
        return null;
    }

    @Override
    public TourAd findOneByTerms(ITermCore iTermCore) {
        return null;
    }
}
