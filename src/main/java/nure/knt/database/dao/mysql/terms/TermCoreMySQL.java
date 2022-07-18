package nure.knt.database.dao.mysql.terms;

import nure.knt.database.idao.terms.ITermCore;
import nure.knt.database.idao.terms.ITermInformation;
import nure.knt.entity.enums.HowSortSQL;

import javax.validation.constraints.NotNull;
import java.util.Map;

public abstract class TermCoreMySQL<E extends Enum,IT extends ITermCore<E,IT>> implements ITermCore<E,IT> {

    protected String privateOrderBy;
    protected String privateLimit;
    protected Long[] privateLimitValue;
    protected final Map<E ,String> orderByValueStringMap;

    private static final String LIMIT_BEFORE_THIS = " LIMIT ? ";
    private static final String LIMIT_BETWEEN_THIS = " LIMIT ?,? ";

    protected TermCoreMySQL(Map<E , String> orderByValueStringMap) {
        this.orderByValueStringMap = orderByValueStringMap;
        privateOrderBy= privateLimit = "";
    }

    @Override
    public IT limitIs(@NotNull Long... limits) {
        if(limits.length == 1){
            this.privateLimit = LIMIT_BEFORE_THIS;
        }else if(limits.length == 2){
            this.privateLimit = LIMIT_BETWEEN_THIS;
        }
        this.privateLimitValue = limits;
        return (IT)this;
    }

    protected static final String ORDER_BY = " ORDER BY %s %s ";

    @Override
    public IT orderBy(E orderByValue, HowSortSQL sort) {
        this.privateOrderBy = String.format(ORDER_BY,this.orderByValueStringMap.getOrDefault(orderByValue,"Error_Not_Fount_Field"),sort.name());
        return (IT)this;
    }

    @Override
    public abstract ITermInformation end();
}
