package nure.knt.forms.filter;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collectors;

public class HandlerFilter {

    public static final LocalDateTime MAX_LOCAL_DATE_TIME = LocalDateTime.of(2038, 01, 01, 1, 1);
    public static final LocalDateTime MIN_LOCAL_DATE_TIME = LocalDateTime.of(1970, 01, 01, 1, 1);

    public static final LocalDate MAX_DATE_TIME = LocalDate.of(2038, 01, 01);
    public static final LocalDate MIN_DATE_TIME = LocalDate.of(1970, 01, 01);

    public static final DateTimeFormatter STRING_TO_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static List<?> LIST_IS_NOT_CREATED_FROM_DATABASE = null;
    public static boolean NOT_EMPTY = false;

    public static <T> boolean predicateList(List<Predicate<T>> filters, T obj) {
        for (Predicate<T> filter : filters) {
            if (!filter.test(obj)) {
                return false;
            }
        }
        return true;
    }

    @Nullable
    public static <T> List<T> checkString(String string, List<T> list, Function<String, List<T>> workWithDatabase, Consumer<String> elseDidNotWorkWithDatabase) {
        if (string == null) {
            return list;
        }

        if (string.isEmpty()) {
            return list;
        }

        string = string.trim();
        if (list == LIST_IS_NOT_CREATED_FROM_DATABASE) {
            return workWithDatabase.apply(string);
        }

        elseDidNotWorkWithDatabase.accept(string);
        return list;
    }


    public static <S extends CharSequence> boolean isEmptyOneOfThem(@NonNull S... strings) {
        for (S string : strings) {
            if (string.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static <T> List<T> checkTwoBooleanForOneState(boolean isState, boolean isNotState, List<T> list, Function<Boolean, List<T>> workWithDatabase, Consumer<Boolean> elseDidNotWorkWithDatabase) {

        if (isState == isNotState) {
            return list;
        }

        if (list == LIST_IS_NOT_CREATED_FROM_DATABASE) {
            return workWithDatabase.apply(isState);
        }

        elseDidNotWorkWithDatabase.accept(isState);
        return list;
    }

    public static <T, N extends Number & Comparable<N>> List<T> checkNumberBetween(N start, N end, final N MIN, final N MAX, List<T> list, BiFunction<N, N, List<T>> workWithDatabase, BiConsumer<N, N> elseDidNotWorkWithDatabase) {
        return filtering(start, end, MIN, MAX, list, workWithDatabase, elseDidNotWorkWithDatabase);
    }

    public static <T> List<T> endFiltering(List<T> list, List<Predicate<T>> filterList) {
        if (list.isEmpty() || filterList.isEmpty()) {
            return list;
        }

        return list.stream()
                .collect(
                        Collectors.filtering((order) -> HandlerFilter.predicateList(filterList, order)
                                , Collectors.toList()));
    }

    private static <T> T setOtherValueIftThisIsNull(T canNull, T otherValue) {
        return (canNull == null) ? otherValue : canNull;
    }

    private static boolean isAllNull(Object... array) {
        for (Object obj : array) {
            if (obj != null) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAllNonNull(Object... array) {
        for (Object obj : array) {
            if (obj == null) {
                return false;
            }
        }
        return true;
    }

    private static <Entity, FilteringBy> List<Entity> lastWorkWith(FilteringBy startDate, FilteringBy endDate, List<Entity> list,
                                                                   BiFunction<FilteringBy, FilteringBy, List<Entity>> workWithDatabase,
                                                                   BiConsumer<FilteringBy, FilteringBy> elseDidNotWorkWithDatabase) {
        if (list == LIST_IS_NOT_CREATED_FROM_DATABASE) {
            return workWithDatabase.apply(startDate, endDate);
        }

        elseDidNotWorkWithDatabase.accept(startDate, endDate);
        return list;
    }

    private static <Entity, FilteringBy extends Comparable<FilteringBy>> List<Entity> filtering(FilteringBy start, FilteringBy end, final FilteringBy MIN, final FilteringBy MAX,
                                                                                                List<Entity> list, BiFunction<FilteringBy, FilteringBy, List<Entity>> workWithDatabase,
                                                                                                BiConsumer<FilteringBy, FilteringBy> elseDidNotWorkWithDatabase) {
        if (isAllNull(start, end)) {
            return list;
        }

        start = setOtherValueIftThisIsNull(start, MIN);

        end = setOtherValueIftThisIsNull(end, MAX);

        if (start.compareTo(end) > -1) {
            FilteringBy temp = start;
            start = end;
            end = temp;
        }

        return lastWorkWith(start, end, list, workWithDatabase, elseDidNotWorkWithDatabase);

    }

    public static <T, D extends ChronoLocalDateTime> List<T> checkDateTime(D startDate, D endDate, final D MIN, final D MAX, List<T> list, BiFunction<D, D, List<T>> workWithDatabase, BiConsumer<D, D> elseDidNotWorkWithDatabase) {
        return filtering(startDate, endDate, MIN, MAX, list, workWithDatabase, elseDidNotWorkWithDatabase);
    }


    public static <T, D extends ChronoLocalDate> List<T> checkDate(D startDate, D endDate, final D MIN, final D MAX, List<T> list, BiFunction<D, D, List<T>> workWithDatabase, BiConsumer<D, D> elseDidNotWorkWithDatabase) {
        if (isAllNull(startDate, endDate)) {
            return list;
        }

        startDate = setOtherValueIftThisIsNull(startDate, MIN);

        endDate = setOtherValueIftThisIsNull(endDate, MAX);

        if (startDate.compareTo(endDate) > -1) {
            D temp = startDate;
            startDate = endDate;
            endDate = temp;
        }

        return lastWorkWith(startDate, endDate, list, workWithDatabase, elseDidNotWorkWithDatabase);
    }

    public static <Entity, D extends ChronoLocalDate> List<Entity> checkDate(D startDate, D endDate, List<Entity> list,
                                                                             BiFunction<D, D, List<Entity>> dateBetween,
                                                                             BiConsumer<D, D> elseDidNotBetween,
                                                                             Function<D, List<Entity>> dateStart,
                                                                             Consumer<D> elseDidNotStart,
                                                                             Function<D, List<Entity>> dateEnd,
                                                                             Consumer<D> elseDidNotEnd) {

        if (isAllNull(startDate, endDate)) {
            return list;
        }

        if (HandlerFilter.isAllNonNull(startDate, endDate)) {
            if (startDate.compareTo(endDate) > -1) {
                D temp = startDate;
                startDate = endDate;
                endDate = temp;
            }
            if (list == LIST_IS_NOT_CREATED_FROM_DATABASE) {
                return dateBetween.apply(startDate, endDate);
            }
            elseDidNotBetween.accept(startDate, endDate);
            return list;
        }

        if (startDate != null) {
            if (list == LIST_IS_NOT_CREATED_FROM_DATABASE) {
                return dateStart.apply(startDate);
            }
            elseDidNotStart.accept(startDate);
            return list;
        }

        if (list == LIST_IS_NOT_CREATED_FROM_DATABASE) {
            return dateEnd.apply(endDate);
        }
        elseDidNotEnd.accept(endDate);
        return list;
    }

    public static <T, E extends Enum<?>> List<T> checkEnums(Set<E> enums, List<T> list, Function<Set<E>, List<T>> workWithDatabase, Consumer<Set<E>> elseDidNotWorkWithDatabase) {

        if(enums.isEmpty()){
            return list;
        }

        if (list == LIST_IS_NOT_CREATED_FROM_DATABASE) {
            return workWithDatabase.apply(enums);
        }
        elseDidNotWorkWithDatabase.accept(enums);
        return list;

    }

    public static <E extends Enum<?>> boolean isEnumFromCollection(Set<? extends E> collection,E filedEnum){
        for(E oneEnum : collection){
            if(oneEnum.equals(filedEnum)){
                return true;
            }
        }

        return false;
    }
}
