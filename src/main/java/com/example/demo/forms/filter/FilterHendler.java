package com.example.demo.forms.filter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Predicate;

public class FilterHendler {
    public static final DateTimeFormatter STRING_TO_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T> boolean predicateList(List<Predicate<T>> filters, T obj){
        for(Predicate<T> filter : filters){
            if(!filter.test(obj)){
                return false;
            }
        }
        return true;
    }
    public static List<LocalDateTime> getListThatIsSorted(LocalDateTime first,LocalDateTime second){
        if (first.isBefore(second))
        {
            return List.of(first,second);
        }
        return  List.of(second,first);
    }




}
