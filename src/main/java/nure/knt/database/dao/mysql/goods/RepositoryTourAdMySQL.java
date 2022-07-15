package nure.knt.database.dao.mysql.goods;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.terms.TermTourAdMySQL;
import nure.knt.database.dao.mysql.tools.MySQLCore;
import nure.knt.database.idao.factory.IFactoryTourAd;
import nure.knt.database.idao.goods.IDAOTourAdWithTerms;
import nure.knt.database.idao.terms.ITermCore;
import nure.knt.database.idao.terms.ITermTourAd;
import nure.knt.database.idao.tools.IConcatScripts;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.goods.TourAd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositoryTourAdMySQL extends MySQLCore implements IDAOTourAdWithTerms<TourAd> {
    @Autowired
    @Qualifier("Factory_Tour_Ad_MySQL")
    private IFactoryTourAd factoryTourAd;
    @Autowired
    @Qualifier("Concat_Script_MySQL")
    private IConcatScripts concator;

    @Override
    public boolean editing(Long id, TourAd entity) {
        return false;
    }

    @Override
    public boolean saveAll(Iterable<TourAd> entities) {
        return false;
    }

    @Override
    public boolean save(TourAd entity) {
        return false;
    }

    @Override
    public boolean updateTypeStateById(Long id, TypeState typeState) {
        return false;
    }

    @Override
    public boolean updateConditionCommodityAndCostServiceById(TourAd tourAd) {
        return false;
    }

    @Override
    public ITermTourAd term() {
        return new TermTourAdMySQL();
    }

    @Override
    public List<TourAd> findByTerms(ITermCore iTermCore) {
        return HandlerSqlDAO.useSelectScript(super.conn,this.toScript(iTermCore),factoryTourAd::getTourAd,iTermCore.getParameters());
    }

    @Override
    public TourAd findOneByTerms(ITermCore iTermCore) {
        return HandlerSqlDAO.useSelectScriptAndGetOneObject(super.conn,this.toScript(iTermCore),factoryTourAd::getTourAd,iTermCore.getParameters());
    }

    static final String SELECT_AND_FIELD = "select \n" +
            "tour_ad.id,\n" +
            "tour_ad.place,\n" +
            "tour_ad.city,\n" +
            "country.name AS country,\n" +
            "user.name AS travel_agency_name,\n" +
            "tour_ad.date_start,\n" +
            "tour_ad.date_end,\n" +
            "tour_ad.date_registration,\n" +
            "tour_ad.cost_one_customer,\n" +
            "tour_ad.cost_service,\n" +
            "tour_ad.discount_size_people,\n" +
            "tour_ad.discount_percentage,\n" +
            "tour_ad.hidden,\n" +
            "travel_agency.rating,\n" +
            "type_state.name AS type_state,\n" +
            "condition_commodity.name AS condition_commodity,\n" +
            "tour_ad.travel_agency_id\n";
    static final String FROM_AND_JOIN = "from tour_ad\n" +
            " LEFT JOIN travel_agency on tour_ad.travel_agency_id = travel_agency.id\n" +
            " LEFT JOIN user on travel_agency.user_id = user.id\n" +
            " LEFT JOIN type_state on tour_ad.type_state_id = type_state.id\n" +
            " LEFT JOIN condition_commodity on tour_ad.condition_commodity_id = condition_commodity.id\n" +
            " LEFT JOIN country on tour_ad.country_id = country.id";

    private String toScript(ITermCore iterm){
        return this.concator.concatScripts("",
                SELECT_AND_FIELD,
                iterm.getSelectField(),
                FROM_AND_JOIN,
                iterm.getJoin(),
                (iterm.getWhere().isEmpty()) ? "" : " WHERE " + iterm.getWhere(),
                (iterm.getGroupBy().isEmpty()) ? "" : " GROUP BY " + iterm.getGroupBy(),
                (iterm.getHaving().isEmpty()) ? "" : " HAVING " + iterm.getHaving(),
                iterm.getLimit());
    }
}
