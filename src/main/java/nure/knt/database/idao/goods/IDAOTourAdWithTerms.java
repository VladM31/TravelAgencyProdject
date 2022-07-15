package nure.knt.database.idao.goods;

import nure.knt.database.idao.core.IDAOCoreEditing;
import nure.knt.database.idao.core.IDAOCoreSave;
import nure.knt.database.idao.core.IDAOUpdateTypeState;
import nure.knt.database.idao.terms.ITermCore;
import nure.knt.database.idao.terms.ITermTourAd;
import nure.knt.entity.goods.TourAd;

import java.util.List;

public interface IDAOTourAdWithTerms<T extends TourAd> extends IDAOCoreSave<T>, IDAOCoreEditing<T>, IDAOUpdateTypeState {
    public boolean updateConditionCommodityAndCostServiceById(TourAd tourAd);

    public ITermTourAd term();

    public List<T> findByTerms(ITermCore iTermCore);

    public T findOneByTerms(ITermCore iTermCore);
}
