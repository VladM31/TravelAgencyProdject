package nure.knt.database.dao.mysql.terms;

import nure.knt.database.dao.HandlerSqlDAO;
import nure.knt.database.idao.terms.ITermInformation;
import nure.knt.database.idao.terms.ITermTourAd;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HandlerTerm {
    public static String checkScriptForAddedOtherScript(String check, String added){
        if(check.isEmpty()){
            return added;
        }
        return  String.join(" AND ",check,added) + "\n";
    }

    public static String printParameters(List<Object> parameters){
        String str = "";
        for (Object object:parameters) {
            if(object instanceof Object[]){
                str+= ", " + Arrays.toString((Object[])object);
            }else {
                str+= ", " + object;
            }
        }
        return str.replaceFirst(",","");
    }

    public static String setScript(String startScript,String added,List<Object> parametersList,Object ...parametersForSetInsideList){
        for (Object obj:parametersForSetInsideList) {
            parametersList.add(obj);
        }
        return checkScriptForAddedOtherScript(startScript,added);
    }

    public static String addGroupBy(String privateGroupBy,String addedGroupBy){
        if(privateGroupBy.isEmpty() || privateGroupBy.contains(addedGroupBy)){
            return addedGroupBy;
        }
        return  String.join(" , ",privateGroupBy,addedGroupBy);
    }

    public static String addJoin(String privateJoin,String addedJoin){
        if(privateJoin.contains(addedJoin)){
            return privateJoin;
        }
        return String.join(" ",privateJoin,addedJoin);
    }

    public static String setFieldsIn(String termsScript,final String WHERE_SET_PARAMETERS,List<Object> termsList,Object[] elements){
        String scriptTemp =  HandlerSqlDAO.setInInsideScript(WHERE_SET_PARAMETERS,elements.length);
        termsList.add(elements);
        return HandlerTerm.checkScriptForAddedOtherScript(termsScript,scriptTemp);
    }

    public static boolean hasErrorInOrderBy(Map<? extends Enum<?>,String> map, String sortScript, String privateField, String field){
        return sortScript.contains(map.get(null)) && !privateField.contains(field);
    }

    public static Object[] concatList(@NotNull List<Object> mainList, @NotNull List<Object> ... otherLists){
        for (List<Object> list : otherLists) {
            mainList.addAll(list);
            list.clear();
        }
        Object[] objects = mainList.toArray();
        mainList.clear();
        return objects;
    }

    public static ITermInformation toEnd(String _privateLimit, String _privateWhere, String _privateJoin,
        String _privateField, String _privateGroupBy, String _privateHaving, String _privateOrderBy, Object[] _privateParameters){

        return new ITermInformation(){

            @Override
            public String getLimit() {
                return _privateLimit;
            }

            @Override
            public String getWhere() {
                return _privateWhere;
            }

            @Override
            public String getJoin() {
                return _privateJoin;
            }

            @Override
            public String getGroupBy() {
                return _privateGroupBy;
            }

            @Override
            public String getHaving() {
                return _privateHaving;
            }

            @Override
            public String getOrderBy() {
                return _privateOrderBy;
            }

            @Override
            public String getSelectField() {
                return _privateField;
            }

            @Override
            public Object[] getParameters() {
                return _privateParameters;
            }
        };
    }

    public static final String LIMIT_BEFORE_THIS = " LIMIT ? ";
    public static final String LIMIT_BETWEEN_THIS = " LIMIT ?,? ";
    public static final String ORDER_BY = " ORDER BY %s %s ";
}
