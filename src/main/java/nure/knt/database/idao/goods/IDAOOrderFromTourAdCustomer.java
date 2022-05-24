package nure.knt.database.idao.goods;

import nure.knt.entity.goods.OrderFromTourAdForCustomer;

import java.util.List;

public interface IDAOOrderFromTourAdCustomer<O extends OrderFromTourAdForCustomer> extends IDAOOrderFromTourAdCore<O>{

    public List<O> findByCountryContaining(Long userOrTuodAdId, String country);
    public List<O> findByRestingPlaceContaining(Long userOrTuodAdId, String restingPlace);
}
