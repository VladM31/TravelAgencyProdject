package com.example.demo.database.dao.tools;

import org.springframework.lang.NonNull;

import java.sql.SQLException;
import java.util.function.Function;

public class Handler {
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
}
