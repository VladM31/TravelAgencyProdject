package nure.knt.database.dao.mysql.factory.goods;

import nure.knt.database.idao.factory.goods.IFactoryTourAd;
import nure.knt.entity.enums.ConditionCommodity;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.goods.TourAd;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component("Factory_Tour_Ad_MySQL")
public class FactoryTourAdMySQL implements IFactoryTourAd {
    @Override
    public TourAd getTourAd(ResultSet resultSet) {
        TourAd tourAd = new TourAd();

        try {
            tourAd.setPlace(resultSet.getString(("place")));
            tourAd.setId(resultSet.getLong(("id")));
            tourAd.setCity(resultSet.getString(("city")));
            tourAd.setCountry(resultSet.getString(("country")));
            tourAd.setNameAgency(resultSet.getString(("travel_agency_name")));
            tourAd.setDateStart(resultSet.getDate(("date_start")).toLocalDate());
            tourAd.setDateEnd(resultSet.getDate(("date_end")).toLocalDate());
            tourAd.setDateRegistration(resultSet.getTimestamp(("date_registration")).toLocalDateTime());
            tourAd.setCostOneCustomer(resultSet.getInt(("cost_one_customer")));

            tourAd.setDiscountPercentage(resultSet.getFloat(("discount_percentage")));
            tourAd.setDiscountSizePeople(resultSet.getInt(("discount_size_people")));
            tourAd.setHidden(resultSet.getBoolean(("hidden")));
            tourAd.setRatingAgency(resultSet.getFloat(("rating")));
            tourAd.setTypeState(TypeState.valueOf(resultSet.getString(("type_state"))));
            tourAd.setConditionCommodity(ConditionCommodity.valueOf(resultSet.getString(("condition_commodity"))));
            tourAd.setTravelAgencyId(resultSet.getLong(("travel_agency_id")));

            try{
                tourAd.setCostService(resultSet.getInt(("cost_service")));
            }catch (SQLException e){
            }

            try {
                tourAd.setOrderQuantity(resultSet.getInt("count_orders"));
            } catch (SQLException e) {
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tourAd;
    }
}
