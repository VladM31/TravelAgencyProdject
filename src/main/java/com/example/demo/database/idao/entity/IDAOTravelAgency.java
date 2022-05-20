package com.example.demo.database.idao.entity;

import com.example.demo.database.idao.core.IDAOCoreEditing;
import com.example.demo.entity.important.TravelAgency;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public interface IDAOTravelAgency<TA extends TravelAgency> extends IDAOUser<TA>, IDAOCoreEditing<TA> {

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


}
