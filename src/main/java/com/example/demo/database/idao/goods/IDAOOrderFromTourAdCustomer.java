package com.example.demo.database.idao.goods;

import com.example.demo.entity.goods.OrderFromTourAdForCustomer;

import java.util.List;

public interface IDAOOrderFromTourAdCustomer<O extends OrderFromTourAdForCustomer> extends IDAOOrderFromTourAdCore<O>{

    public List<O> findByCountryContaining(Long userOrTuodAdId, String country);
    public List<O> findByRestingPlaceContaining(Long userOrTuodAdId, String restingPlace);
}
