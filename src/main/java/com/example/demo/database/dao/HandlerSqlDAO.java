package com.example.demo.database.dao;

import com.example.demo.database.idao.IConnectorGetter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class HandlerSqlDAO {

    public static final String SORT_TO_DATE_REGISTRATION = " ORDER BY date_registration ASC;";
    public static final int EMPTY_CAPACITY = 0;
    public static final String REPLACE_SYMBOL = "?#!@#@_REPLACE_ME_@#@!#?";
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

    public static <T> String symbolsInDependsFromSize(Iterable<T> ids){
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

    public static final int START_POSITION = 0;
    public static <T> List<T> useSelectScript(IConnectorGetter connectorGetter, final String script,
                                              Function<ResultSet,T> getObject, @NonNull Object ...array){
        try(java.sql.PreparedStatement stat = connectorGetter.getSqlPreparedStatement(script)) {
            int position = START_POSITION;
            for(Object obj : array){
                substituteVariable(stat,++position,obj);
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
            }finally {
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
            }finally {
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


}
