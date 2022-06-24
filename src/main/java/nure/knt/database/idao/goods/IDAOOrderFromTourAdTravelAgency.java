package nure.knt.database.idao.goods;

import nure.knt.entity.goods.OrderFromTourAdForTravelAgency;

import java.util.List;

public interface IDAOOrderFromTourAdTravelAgency<O extends OrderFromTourAdForTravelAgency> extends IDAOOrderFromTourAdCore<O> {
    public List<O> findByFirstnameContaining(Long userOrTuodAdId, String firstname);
    public List<O> findByLastnameContaining(Long userOrTuodAdId, String lastname);
    public List<O> findByEmailContaining(Long userOrTuodAdId, String email);
    public List<O> findByNumberContaining(Long userOrTuodAdId, String number);
    public List<O> findByMaleIs(Long userOrTuodAdId, Boolean male);
    public O findByTravelAgencyIdAndTourAdIdAndOrderId(Long travelAgencyId, Long tourAdId,Long orderId);
}
