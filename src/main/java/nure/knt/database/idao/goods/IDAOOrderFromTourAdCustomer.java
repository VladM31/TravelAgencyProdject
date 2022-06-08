package nure.knt.database.idao.goods;

import nure.knt.database.idao.core.IDAOCoreSave;
import nure.knt.entity.goods.OrderFromTourAdForCustomer;

import java.sql.SQLException;
import java.util.List;

public interface IDAOOrderFromTourAdCustomer<O extends OrderFromTourAdForCustomer> extends IDAOOrderFromTourAdCore<O>, IDAOCoreSave<O> {

    public List<O> findByNameTravelAgencyContaining(Long customerId, String country);//Турфірма 1
    public List<O> findByRestingPlaceContaining(Long customerId, String restingPlace);//Місце відпочинку 4
    public boolean isThisCustomerOrder(Long customerId,Long orderId);
}
