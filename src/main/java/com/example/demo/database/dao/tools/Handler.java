package com.example.demo.database.dao.tools;

import com.example.demo.database.idao.IConnectorGetter;
import com.example.demo.entity.important.Customer;
import com.example.demo.entity.subordinate.MessageShortData;
import org.springframework.lang.NonNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class Handler {

    public static final int EMPTY_CAPACITY = 0;
    public static final String REPLACE_SYMBOL = "?#!@#@_REPLACE_ME_@#@!#?";
    public static final Consumer<PreparedStatement> DEFAULT_PARAMETER = (PreparedStatement p) -> {};


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
            return new ArrayList<>(Handler.EMPTY_CAPACITY);
        }
    }

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
}
