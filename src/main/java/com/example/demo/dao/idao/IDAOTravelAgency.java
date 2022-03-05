package com.example.demo.dao.idao;

import com.example.demo.entity.TravelAgency;
import org.springframework.lang.NonNull;

import java.util.List;

public interface IDAOTravelAgency<TA extends TravelAgency> extends IDAOUser<TA>{


    List<TA> findByTravelAgencyIdIn(@NonNull Iterable<Long> ids);
    List<TA> findByTravelAgencyObjectIdIn(@NonNull Iterable<TA> ids);
    TA findByTravelAgencyId(@NonNull Long id);

    List<TA> findByRating(float rating);
    List<TA> findByRatingLessThan(float rating);
    List<TA> findByRatingLessThanEqual(float rating);
    List<TA> findByRatingGreaterThan(float rating);
    List<TA> findByRatingGreaterThanEqual(float rating);

    List<TA> findByRatingGreaterThanAndLessThan(float rating);
    List<TA> findByRatingGreaterThanEqualAndLessThan(float rating);
    List<TA> findByRatingGreaterThanAndLessThanEqual(float rating);
    List<TA> findByRatingGreaterThanEqualAndLessThanEqual(float rating);

    TA findByKVED(long kved);
    List<TA> findByKVEDIn(@NonNull Iterable<Long> kveds);

    TA findByEGRPOY(Long egrpoy);
    List<TA> findByEGRPOYIn(@NonNull Iterable<Long> egrpoys);
    List<TA> findByEGRPOYIsNull();
    List<TA> findByEGRPOYIsNotNull();

    TA findByRNEKPN(Long rnekpn);
    List<TA> findByRNEKPNIn(@NonNull Iterable<Long> rnekpns);
    List<TA> findByRNEKPNIsNull();
    List<TA> findByRNEKPNIsNotNull();

    TA findByAddress(String address);
    List<TA> findByAddressLike(String script);

    List<TA> findByAllNameDirector(String allNameDirector);
    List<TA> findByAllNameDirectorLike(String script);
}
