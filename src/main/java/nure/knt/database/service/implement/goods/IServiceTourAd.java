package nure.knt.database.service.implement.goods;

import nure.knt.database.idao.core.IDAOCoreEditing;
import nure.knt.database.idao.core.IDAOCoreSave;
import nure.knt.database.idao.core.IDAOUpdateTypeState;
import nure.knt.database.idao.terms.ITermInformation;
import nure.knt.database.idao.terms.ITermTourAd;
import nure.knt.entity.goods.TourAd;

import java.util.List;

public interface IServiceTourAd<T extends  TourAd> extends IDAOCoreSave<T>, IDAOCoreEditing<T>, IDAOUpdateTypeState {

    public boolean updateConditionCommodityAndCostServiceById(T tourAd);

    public ITermTourAd term();

    public List<T> findByTerms(ITermInformation iTermInformation);

    public T findOneByTerms(ITermInformation iTermInformation);
}
