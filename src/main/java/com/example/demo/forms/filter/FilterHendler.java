package com.example.demo.forms.filter;

import java.util.List;
import java.util.function.Predicate;

public class FilterHendler {
    public static <T> boolean predicateList(List<Predicate<T>> filters, T obj){
        for(Predicate<T> filter : filters){
            if(!filter.test(obj)){
                return false;
            }
        }
        return true;
    }
}
