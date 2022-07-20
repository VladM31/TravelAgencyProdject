package nure.knt.database.dao.mysql.terms;

import nure.knt.database.idao.terms.ITermInformation;
import nure.knt.entity.enums.HowSortSQL;

import java.util.List;

public class TermStart {
    protected String privateLimit;
    protected Long[] privateLimitValue;

    protected String privateJoin;
    protected String privateWhere;
    protected String privateField;
    protected String privateGroupBy;
    protected String privateOrderBy;

    protected String privateHaving;
    protected final List<Object> privateParametersForWhere;
    protected final List<Object> privateParametersForHaving;

    public TermStart(List<Object> privateParametersForWhere, List<Object> privateParametersForHaving) {
        this.privateParametersForWhere = privateParametersForWhere;
        this.privateParametersForHaving = privateParametersForHaving;
        privateLimit = privateJoin = privateWhere = "";
        privateField = privateGroupBy =
                privateOrderBy = privateHaving = "";
    }

    protected static final String LIMIT_BEFORE_THIS = " LIMIT ? ";
    protected static final String LIMIT_BETWEEN_THIS = " LIMIT ?,? ";

    protected void setLimit(Long[] limits){
        if(limits.length == 1){
            this.privateLimit = LIMIT_BEFORE_THIS;
        }else if(limits.length == 2){
            this.privateLimit = LIMIT_BETWEEN_THIS;
        }
        this.privateLimitValue = limits;
    }

    protected static final String ORDER_BY = " ORDER BY %s %s ";
    protected void setOrderBy(String fieldName, HowSortSQL sort){
        this.privateOrderBy = String.format(ORDER_BY,fieldName,sort.name());
    }



    public ITermInformation toEnd() {

        if(!privateLimit.isEmpty()){
            this.privateParametersForWhere.add(privateLimitValue);
        }

        Object[] _privateParameters = HandlerTerm.concatList(privateParametersForWhere,privateParametersForHaving);

        ITermInformation information = HandlerTerm.toEnd(privateLimit,this.privateWhere,this.privateJoin,
                this.privateField,this.privateGroupBy,this.privateHaving,privateOrderBy,_privateParameters);


        this.privateGroupBy = privateLimit = this.privateHaving = "";
        this.privateWhere = this.privateJoin = this.privateField = "";
        privateOrderBy = "";

        return information;
    }
}
