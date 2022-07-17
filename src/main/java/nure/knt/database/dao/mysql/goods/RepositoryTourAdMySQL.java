package nure.knt.database.dao.mysql.goods;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.dao.mysql.terms.TermTourAdMySQL;
import nure.knt.database.dao.mysql.tools.MySQLCore;
import nure.knt.database.idao.factory.IFactoryTourAd;
import nure.knt.database.idao.goods.IDAOTourAdWithTerms;
import nure.knt.database.idao.terms.ITermCore;
import nure.knt.database.idao.terms.ITermTourAd;
import nure.knt.database.idao.tools.IConcatScripts;
import nure.knt.database.idao.tools.IConnectorGetter;
import nure.knt.entity.enums.TypeState;
import nure.knt.entity.goods.TourAd;
import nure.knt.tools.WorkWithCountries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

import static nure.knt.database.dao.HandlerSqlDAO.ERROR_BOOLEAN_ANSWER;


@Scope("singleton")
@Repository("Repository_Tour_Ad_MySQL")
@PropertySource("classpath:property/goods/WorkerWithTourAds.properties")
public class RepositoryTourAdMySQL extends MySQLCore implements IDAOTourAdWithTerms<TourAd> {
    @Autowired
    @Qualifier("Factory_Tour_Ad_MySQL")
    private IFactoryTourAd factoryTourAd;
    @Autowired
    @Qualifier("Concat_Script_MySQL")
    private IConcatScripts concator;
    @Autowired
    @Qualifier("MySQL Countries")
    private WorkWithCountries countries;

    private IdWorker idWorker;

    private static final String SELECT_MAX_ID = "SELECT MAX(id) AS max_id FROM tour_ad;";

    private final Map<ITermTourAd.OrderByValue,String> ORDER_BY_VALUE_STRING_MAP;

    public RepositoryTourAdMySQL(@Value("${dao.tour.ad.order.by.enums.properties}") String fileName) {
        this.ORDER_BY_VALUE_STRING_MAP = HandlerRepositoryTourAdMySQL.setNameScriptForEnumsTourAdOrderByValue(fileName);
    }

    @PostConstruct
    private void init(){
        try(java.sql.Statement statement = super.conn.getSqlStatement();ResultSet resultSet = statement.executeQuery(SELECT_MAX_ID)){
            if(resultSet.next()){
                this.idWorker = new IdWorker(resultSet.getLong("max_id"));
            }else {
                this.idWorker = new IdWorker(0l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("MAX ID not fount nure/knt/database/dao/mysql/goods/RepositoryTourAdMySQL.java");
        }
    }

    @Override
    public boolean editing(Long id, TourAd entity) {

        long editId = HandlerRepositoryTourAdMySQL.saveTourAd(super.conn,List.of(entity),this.countries,this.idWorker);
        if(editId == HandlerRepositoryTourAdMySQL.ERROR_ID){
            return false;
        }


        try (PreparedStatement statement = super.conn.getSqlPreparedStatement(INSERT_INSIDE_EDIT_TOUR_AD)) {
            int position = 0;
            statement.setBoolean(++position, false);
            statement.setBoolean(++position, false);
            statement.setLong(++position, id);
            statement.setLong(++position, editId);

            return statement.executeUpdate() != 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public boolean saveEdit(Long id){
        try(PreparedStatement statement = super.conn.getSqlPreparedStatement(UPDATE_EDIT)){
            statement.setLong(1,id);
            if(statement.executeUpdate() == 0){
                return false;
            }

            return statement.executeUpdate(String.format(UPDATE_EDITE_TOUR_AD_AFTER_SAVE,id)) != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeEdit(Long id) {
        try(PreparedStatement statement = super.conn.getSqlPreparedStatement(UPDATE_EDITE_TOUR_AD_FOR_REMOVE)){
            statement.setLong(1,id);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean saveAll(Iterable<TourAd> entities) {
        return HandlerRepositoryTourAdMySQL.saveTourAd(super.conn,entities,this.countries,this.idWorker) != HandlerRepositoryTourAdMySQL.ERROR_ID;
    }

    @Override
    public boolean save(TourAd entity) {
        return this.saveAll(List.of(entity));
    }

    @Override
    public boolean updateTypeStateById(Long id, TypeState typeState) {
        try(PreparedStatement preStatement = super.conn.getSqlPreparedStatement(UPDATE_TYPE_STATE_BY_ID)){
            preStatement.setInt(1,typeState.getId());
            preStatement.setLong(2, id);
            return preStatement.executeUpdate()!=0;

        }catch(Exception exc){
            exc.printStackTrace();
        }
        return ERROR_BOOLEAN_ANSWER;
    }


    @Override
    public boolean updateConditionCommodityAndCostServiceById(TourAd tourAd) {
        try(PreparedStatement preStatement = super.conn.getSqlPreparedStatement(CONFIRM_TOUR_AD)){
            preStatement.setInt(1,tourAd.getConditionCommodity().getId());
            preStatement.setInt(2, tourAd.getCostService());
            preStatement.setLong(3, tourAd.getId());
            return preStatement.executeUpdate()!=0;

        }catch(Exception exc){
            exc.printStackTrace();
        }
        return ERROR_BOOLEAN_ANSWER;
    }

    @Override
    public ITermTourAd term() {
        return new TermTourAdMySQL(ORDER_BY_VALUE_STRING_MAP);
    }

    @Override
    public List<TourAd> findByTerms(ITermCore iTermCore) {
        return HandlerSqlDAO.useSelectScript(super.conn,
                HandlerRepositoryTourAdMySQL
                        .toScript(iTermCore,concator),
                factoryTourAd::getTourAd,
                iTermCore.getParameters());
    }

    @Override
    public TourAd findOneByTerms(ITermCore iTermCore) {
        return HandlerSqlDAO.useSelectScriptAndGetOneObject(super.conn,
                HandlerRepositoryTourAdMySQL
                        .toScript(iTermCore,concator),
                factoryTourAd::getTourAd,
                iTermCore.getParameters());
    }

    class IdWorker{
        private long id;

        public IdWorker(long id) {
            this.id = id;
        }

        public long next(){
            synchronized (this){
                return ++id;
            }
        }
    }

    class HandlerRepositoryTourAdMySQL {


        protected static Map<ITermTourAd.OrderByValue,String> setNameScriptForEnumsTourAdOrderByValue(String fileName){
            HashMap<ITermTourAd.OrderByValue,String> map = new HashMap<>();
            Properties appProps = new Properties();

            try(FileInputStream fileInputStream = new FileInputStream(fileName)) {
                appProps.load(fileInputStream);
                final String FIRST_PIECE = appProps.getProperty("dao.terms.tour.ads.what.add");

                map.put(null,appProps.getProperty("dao.terms.tour.ads.error"));

                for (ITermTourAd.OrderByValue enumObject: ITermTourAd.OrderByValue.values()) {
                    map.put(enumObject,appProps.getProperty(FIRST_PIECE + enumObject,"Error."+enumObject));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return Collections.unmodifiableMap(map);
        }

        private static final String INSERT_TOUR_AD =
                " INSERT INTO tour_ad (place,city,date_start,date_end,date_registration," +
                        "cost_one_customer,cost_service,discount_size_people,discount_percentage," +
                        "hidden,travel_agency_id,condition_commodity_id,type_state_id,country_id,id)" +
                        " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

        static long tourAdToMySqlScript(PreparedStatement preStat, TourAd tourAd,WorkWithCountries countries,long id) {
            try {
                int position = 0;
                preStat.setString(++position, tourAd.getPlace());
                preStat.setString(++position, tourAd.getCity());
                preStat.setDate(++position, Date.valueOf(tourAd.getDateStart()));
                preStat.setDate(++position, Date.valueOf(tourAd.getDateEnd()));
                preStat.setTimestamp(++position, Timestamp.valueOf(tourAd.getDateRegistration()));
                preStat.setInt(++position, tourAd.getCostOneCustomer());
                preStat.setInt(++position, tourAd.getCostService());
                preStat.setInt(++position, tourAd.getDiscountSizePeople());
                preStat.setFloat(++position, tourAd.getDiscountPercentage());
                preStat.setBoolean(++position, tourAd.isHidden());
                preStat.setLong(++position, tourAd.getTravelAgencyId());
                preStat.setLong(++position, tourAd.getConditionCommodity().getId());
                preStat.setLong(++position, tourAd.getTypeState().getId());
                preStat.setLong(++position, countries.getIdByCountry(tourAd.getCountry()));
                preStat.setLong(++position,id);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return id;
        }

        public static final long ERROR_ID = -111;

        protected static long saveTourAd(IConnectorGetter conn, Iterable<TourAd> entities, WorkWithCountries countries, IdWorker idWorker){
            try(PreparedStatement preparedStatement = conn.getSqlPreparedStatement(INSERT_TOUR_AD)){
                long id = ERROR_ID;
                for (TourAd tourAd: entities) {

                    id = HandlerRepositoryTourAdMySQL.tourAdToMySqlScript(preparedStatement, tourAd,countries,idWorker.next());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
                return id;

            }catch(Exception exc){
                exc.printStackTrace();
                return ERROR_ID;
            }
        }

        static final String SELECT_AND_FIELD = "select \n" +
                "tour_ad.id,\n" +
                "tour_ad.travel_agency_id,\n" +

                "tour_ad.place,\n" +
                "tour_ad.city,\n" +
                "country.name AS country,\n" +
                "user.name AS travel_agency_name,\n" +

                "tour_ad.date_start,\n" +
                "tour_ad.date_end,\n" +
                "tour_ad.date_registration,\n" +

                "tour_ad.cost_one_customer,\n" +
                "tour_ad.discount_size_people,\n" +

                "tour_ad.hidden,\n" +

                "tour_ad.discount_percentage,\n" +
                "travel_agency.rating,\n" +

                "type_state.name AS type_state,\n" +
                "condition_commodity.name AS condition_commodity\n";

        static final String FROM_AND_JOIN = "from tour_ad\n" +
                " LEFT JOIN travel_agency on tour_ad.travel_agency_id = travel_agency.id\n" +
                " LEFT JOIN user on travel_agency.user_id = user.id\n" +
                " LEFT JOIN type_state on tour_ad.type_state_id = type_state.id\n" +
                " LEFT JOIN condition_commodity on tour_ad.condition_commodity_id = condition_commodity.id\n" +
                " LEFT JOIN country on tour_ad.country_id = country.id";

        public static String toScript(ITermCore iterm,IConcatScripts concator){
            return concator.concatScripts("",
                    SELECT_AND_FIELD,
                    iterm.getSelectField(),
                    FROM_AND_JOIN,
                    iterm.getJoin(),
                    (iterm.getWhere().isEmpty()) ? "" : " WHERE " + iterm.getWhere(),
                    (iterm.getGroupBy().isEmpty()) ? "" : " GROUP BY " + iterm.getGroupBy(),
                    (iterm.getHaving().isEmpty()) ? "" : " HAVING " + iterm.getHaving(),
                    iterm.getOrderBy(),
                    iterm.getLimit());
        }
    }

    private static final String UPDATE_TYPE_STATE_BY_ID = "UPDATE tour_ad left join travel_agency on travel_agency_id = travel_agency.id  " +
            "SET type_state_id = ? WHERE user.id = ?;";

    private static final String CONFIRM_TOUR_AD = "UPDATE tour_ad " +
            "SET condition_commodity_id = ? , cost_service = ? WHERE id = ?;";

    private static final String UPDATE_EDIT = "UPDATE tour_ad\n" +
            "left join edit_tour_ad on edit_tour_ad.whom_need_change_id = tour_ad.id\n" +
            "left join tour_ad as changes on edit_tour_ad.what_to_change = changes.id\n" +
            "SET \n" +
            "tour_ad.place =changes.place,\n" +
            "tour_ad.city = changes.city ,\n" +
            "tour_ad.date_start =changes.date_start, \n" +
            "tour_ad.date_end = changes.date_end ,\n" +
            "tour_ad.cost_one_customer = changes.cost_one_customer, \n" +
            "tour_ad.discount_size_people = changes.discount_size_people ,\n" +
            "tour_ad.discount_percentage =changes.discount_percentage, \n" +
            "tour_ad.country_id = changes.country_id \n" +
            " WHERE changes.id = ?;";

    private static final String UPDATE_EDITE_TOUR_AD_AFTER_SAVE = "UPDATE edit_tour_ad SET confirmed = true,need_delete = true WHERE what_to_change = %d;";
    private static final String UPDATE_EDITE_TOUR_AD_FOR_REMOVE = "UPDATE edit_tour_ad SET need_delete = true WHERE what_to_change = ?;";

    private static final String INSERT_INSIDE_EDIT_TOUR_AD = "INSERT INTO edit_tour_ad(confirmed,need_delete,whom_need_change_id,what_to_change) VALUE(?,?,?,?);";

}
