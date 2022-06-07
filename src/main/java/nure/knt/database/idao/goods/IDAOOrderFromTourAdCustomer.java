package nure.knt.database.idao.goods;

import nure.knt.database.idao.core.IDAOCoreSave;
import nure.knt.entity.goods.OrderFromTourAdForCustomer;

import java.util.List;

public interface IDAOOrderFromTourAdCustomer<O extends OrderFromTourAdForCustomer> extends IDAOOrderFromTourAdCore<O>, IDAOCoreSave<O> {

    public List<O> findByNameTravelAgencyContaining(Long userOrTuodAdId, String country);//Турфірма 1
    public List<O> findByRestingPlaceContaining(Long userOrTuodAdId, String restingPlace);//Місце відпочинку 4
}
