package nure.knt.forms.filter;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.*;

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


    public static List<?> LIST_IS_NOT_CREATED_FROM_DATABASE = null;
    @Nullable
    public static <T> List<T> checkString(String string, List<T> list, Function<String,List<T>> workWithDatabase,Consumer<String> elseDidNotWorkWithDatabase){
        if (string.isEmpty()){
            return list;
        }

        string = string.trim();
        if(list == LIST_IS_NOT_CREATED_FROM_DATABASE){
            return workWithDatabase.apply(string);
        }

        elseDidNotWorkWithDatabase.accept(string);
        return list;
    }

    public static boolean isItNotNeedSwap(String first, String second){
        return first.compareTo(second) < 1;
    }

    public static <S extends CharSequence> boolean isEmptyOneOfThem(@NonNull S ...strings){
        for (S string:strings) {
            if(string.isEmpty()){
                return true;
            }
        }
        return false;
    }


    public static boolean NOT_EMPTY = false;
    public static int START_DATE = 0;
    public static int END_DATE = 1;

    public static <T> List<T> checkDate(String startDate, String endDate, List<T> list,
                                         BiFunction<LocalDateTime,LocalDateTime,List<T>> dateBetween,
                                         BiConsumer<LocalDateTime,LocalDateTime> elseDidNotBetween,
                                         Function<LocalDateTime,List<T>> dateStart,
                                         Consumer<LocalDateTime> elseDidNotStart,
                                         Function<LocalDateTime,List<T>> dateEnd,
                                         Consumer<LocalDateTime> elseDidNotEnd){

        if(startDate.isEmpty() && endDate.isEmpty()){
            return list;
        }

        if(FilterHendler.isEmptyOneOfThem(startDate,endDate) == NOT_EMPTY){
            List<LocalDateTime> dateList = FilterHendler.twoStringsToSortListLocalDateTime(startDate,endDate,FilterHendler::stringToLDT);
            if(list == LIST_IS_NOT_CREATED_FROM_DATABASE){
               return dateBetween.apply(dateList.get(START_DATE),dateList.get(END_DATE));
            }
            elseDidNotBetween.accept(dateList.get(START_DATE),dateList.get(END_DATE));
            return list;
        }

        if(startDate.isEmpty() == NOT_EMPTY){
            if(list == LIST_IS_NOT_CREATED_FROM_DATABASE){
                return dateStart.apply(FilterHendler.stringToLDT(startDate));
            }
            elseDidNotStart.accept(FilterHendler.stringToLDT(startDate));
            return list;
        }

        if(list == LIST_IS_NOT_CREATED_FROM_DATABASE){
            return dateEnd.apply(FilterHendler.stringToLDT(endDate));
        }
        elseDidNotEnd.accept(FilterHendler.stringToLDT(endDate));
        return list;
    }

    public static <T extends ChronoLocalDateTime> List<T> twoStringsToSortListLocalDateTime(String dateFirst,String dateSecond,Function<String,T> parseString){
        if (FilterHendler.isItNotNeedSwap(dateFirst,dateSecond)) {
            return List.of(parseString.apply(dateFirst),parseString.apply(dateSecond));
        }
        return List.of(parseString.apply(dateSecond),parseString.apply(dateFirst));
    }


    public static LocalDateTime stringToLDT(String dateString){
        return LocalDateTime.parse(dateString,STRING_TO_DATE_TIME_FORMAT);
    }


    public static <T> List<T> checkTwoBooleanForOneState(boolean isState, boolean isNotState, List<T> list,Function<Boolean,List<T>> workWithDatabase,Consumer<Boolean> elseDidNotWorkWithDatabase){

        if (isState == isNotState ){
            return list;
        }

        if(list == LIST_IS_NOT_CREATED_FROM_DATABASE){
            return workWithDatabase.apply(isState);
        }

        elseDidNotWorkWithDatabase.accept(isState);
        return list;
    }
}

