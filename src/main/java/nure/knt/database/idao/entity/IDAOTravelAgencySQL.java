package nure.knt.database.idao.entity;

import nure.knt.database.idao.core.IDAOCoreEditing;
import nure.knt.database.idao.core.IDAOUpdateTypeState;
import nure.knt.database.idao.registration.IDAOFindAllTravelAgencyForRegistration;
import nure.knt.entity.important.TravelAgency;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public interface IDAOTravelAgencySQL<TA extends TravelAgency> extends IDAOUserSQL<TA>, IDAOCoreEditing<TA>, IDAOUpdateTypeState , IDAOFindAllTravelAgencyForRegistration {

     public  List<TA> findByTravelAgencyIdIn(@NonNull Iterable<Long> ids);
     public  List<TA> findByTravelAgencyObjectIdIn(@NonNull Iterable<TA> ids);
     @Nullable
     public  TA findByTravelAgencyId(@NonNull Long id);

     public List<TA> findByRatingBetween(float ratingF,float ratingE);

     public List<TA> findByKVEDContaining(String kved);

     public TA findByEGRPOY(Long egrpoy);
     public List<TA> findByEGRPOY(boolean isEGRPOY);

     @Nullable
     public TA findByRNEKPN(Long rnekpn);

     public  List<TA> findByAddressContaining(String address);

     public List<TA> findByAllNameDirectoContaining(String allNameDirector);

     public List<TA> findAllAndLimit(int limit);

}
