package nure.knt.database.idao.factory;

import nure.knt.entity.goods.TourAd;

import java.util.function.Function;

public interface IFactoryTourAd {
    public TourAd getTourAd(java.sql.ResultSet resultSet);
}
