package nure.knt.database.dao;

import nure.knt.database.dao.mysql.entity.HandlerUser;
import nure.knt.database.idao.IConnectorGetter;
import nure.knt.tools.WorkWithCountries;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToLongFunction;

@Component
public class HandlerSqlDAO {

    private static WorkWithCountries countries;

    public static WorkWithCountries getCountries() {
        return countries;
    }

    @Autowired
    public void setCountries(WorkWithCountries countries) {
        HandlerSqlDAO.countries = countries;
    }

    public static final String SORT_TO_DATE_REGISTRATION = " ORDER BY date_registration ASC;";
    public static final int EMPTY_CAPACITY = 0;
    public static final String REPLACE_SYMBOL = "!?#!@#@_REPLACE_ME_@#@!#?!";

    public static final Consumer<PreparedStatement> DEFAULT_PARAMETER = (PreparedStatement p) -> {};

    public static String containingString(String str){
        return "%".concat(str).concat("%");
    }

    public static boolean arrayHasOnlyOne(int[] arr){
        for(int i: arr){
            if(i != 1){
                return false;
            }
        }
        return true;
    }

    public static @NotNull String setInInsideScript(@NonNull String script, Iterable<?> ids){
        return script.replaceFirst(HandlerSqlDAO.REPLACE_SYMBOL,HandlerSqlDAO.symbolsInDependsFromSize(ids));
    }

    public static String symbolsInDependsFromSize(Iterable<?> ids){
        StringBuilder symbols = new StringBuilder();
        ids.forEach(i -> symbols.append("?,"));
        if(symbols.isEmpty()){
            return "";
        }
        return symbols.substring(0,symbols.length()-1).toString();
    }

    public static <T> void substituteIds(java.sql.PreparedStatement preparedStatement, Iterable<T> ids, Function<T,Long> function) throws SQLException {
        int index = 0;
        for (T iter:ids) {
            preparedStatement.setLong(++index,function.apply(iter));
        }
    }

    public static <T> boolean isEmpty(Iterable<T> items){
        return items == null || !items.iterator().hasNext();
    }

    public static String concatScriptToEnd(@NonNull String startScript,@NonNull String ...ExtraScripts){
        if(ExtraScripts.length == 0) {
            return startScript;
        }
        return startScript.replace(";",String.join(" ",ExtraScripts));
    }



    public static <T> List<T> useSelectScript(IConnectorGetter connectorGetter, final String script,
                                              Consumer<java.sql.PreparedStatement> extraSet, Function<ResultSet,T> getObject){
        try(java.sql.PreparedStatement stat = connectorGetter.getSqlPreparedStatement(script)) {
            extraSet.accept(stat);
            try(ResultSet resultSet = stat.executeQuery()){
                List<T> list = new ArrayList<>();
                while (resultSet.next()){
                    list.add(getObject.apply(resultSet));
                }
                return list;
            }finally {
            }

        }catch (SQLException e){
            e.printStackTrace();
            return new ArrayList<>(HandlerSqlDAO.EMPTY_CAPACITY);
        }
    }

    private static final int START_POSITION = 0;

    public static <T> List<T> useSelectScript(IConnectorGetter connectorGetter, final String script,
                                              Function<ResultSet,T> getObject, @NonNull Object ...array){
        try(java.sql.PreparedStatement stat = connectorGetter.getSqlPreparedStatement(script)) {
            int position = START_POSITION;
            for(Object obj : array){
                if(obj instanceof Iterable){
                    Iterable<?> iter = (Iterable<?>) obj;
                    for (Object it:iter) {
                        substituteVariable(stat,++position,it);
                    }
                }else {
                    substituteVariable(stat,++position,obj);
                }
            }

            try(ResultSet resultSet = stat.executeQuery()){
                List<T> list = new ArrayList<>();
                while (resultSet.next()){
                    list.add(getObject.apply(resultSet));
                }
                return list;
            }finally {
            }

        }catch (SQLException e){
            e.printStackTrace();
            return new ArrayList<>(HandlerSqlDAO.EMPTY_CAPACITY);
        }
    }

    private static void substituteVariable(PreparedStatement preparedStatement,int position,Object object) throws SQLException{
        if(object == null){
            preparedStatement.setNull(position, Types.OTHER);
            return;
        }

        if (object instanceof Long) {
            preparedStatement.setLong(position, (Long) object);
        } else if (object instanceof String) {
            preparedStatement.setString(position, object.toString());
        } else if (object instanceof Integer){
            preparedStatement.setInt(position, (Integer) object);
        } else if (object instanceof LocalDateTime){
            preparedStatement.setTimestamp(position, Timestamp.valueOf(((LocalDateTime) object)));
        } else if( object instanceof LocalDate){
            preparedStatement.setDate(position, Date.valueOf((LocalDate) object));
        } else if (object instanceof Boolean) {
            preparedStatement.setBoolean(position, (Boolean) object);
        } else if (object instanceof Float){
            preparedStatement.setFloat(position, (Float) object);
        } else if (object instanceof Enum<?>){
            preparedStatement.setString(position, object.toString());
        }else{
            throw new SQLException("substituteVariable: object is " + object.getClass().getName());
        }
    }

    @Nullable
    public static <T> T useSelectScriptAndGetOneObject(IConnectorGetter connectorGetter, final String script, Consumer<java.sql.PreparedStatement> extraSet, Function<ResultSet, T> getObject){
        try(java.sql.PreparedStatement stat = connectorGetter.getSqlPreparedStatement(script)) {
            extraSet.accept(stat);
            try(ResultSet resultSet = stat.executeQuery()){
                if(resultSet.next()){
                    return getObject.apply(resultSet);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static <T> T useSelectScriptAndGetOneObject(IConnectorGetter connectorGetter, final String script, Function<ResultSet, T> getObject, @NonNull Object ...array){
        try(java.sql.PreparedStatement stat = connectorGetter.getSqlPreparedStatement(script)) {
            int position = START_POSITION;
            for(Object obj : array){
                substituteVariable(stat,++position,obj);
            }
            try(ResultSet resultSet = stat.executeQuery()){
                if(resultSet.next()){
                    return getObject.apply(resultSet);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    // new 03.06.2022
    public static final int[] ERROR_UPDATE = new int[]{Integer.MIN_VALUE,Integer.MAX_VALUE};
    public static final boolean ERROR_BOOLEAN_ANSWER = false;
    public static final boolean HAVE_NO_ERROR = true;

    public static <T> int[] updateById(IConnectorGetter connectorGetter, final String script, Iterable<T> collection, BiFunction<PreparedStatement,T,Boolean> setField){

        try(PreparedStatement statement = connectorGetter.getSqlPreparedStatement(script)){

            for (T entity: collection) {
                if(setField.apply(statement,entity) == ERROR_BOOLEAN_ANSWER){
                    return ERROR_UPDATE;
                }
                statement.addBatch();
            }
            return statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ERROR_UPDATE;
    }

    // new 05.06.2022
    public static final int ERROR_DELETE = Integer.MIN_VALUE;

    public static <T>  int deleteByIdIn(IConnectorGetter connectorGetter, String script,Iterable<T> collection,Function<T,Long> entityToLong){

        if(script.contains(HandlerSqlDAO.REPLACE_SYMBOL)){
            script = setInInsideScript(script,collection);
        }

        try(PreparedStatement statement = connectorGetter.getSqlPreparedStatement(script)){

            HandlerSqlDAO.substituteIds(statement,collection,entityToLong);

            return statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ERROR_DELETE;
    }

    private static final Function<Long,Long> LONG_TO_LONG = (id) -> id;

    public static int deleteByIdIn(IConnectorGetter connectorGetter, String script,Iterable<Long> collection){
        return HandlerSqlDAO.deleteByIdIn(connectorGetter,script,collection,LONG_TO_LONG);
    }

}
