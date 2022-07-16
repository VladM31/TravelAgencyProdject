package nure.knt.forms.filter.terms;

import nure.knt.forms.filter.HandlerFilter;
import org.springframework.lang.Nullable;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class HandlerFilterTerms {

    public static void checkString(String string, Consumer<String> setStringParameter) {
        if (string == null) {
            return;
        }

        if (string.isEmpty()) {
            return;
        }

        string = string.trim();

        setStringParameter.accept(string);
    }

    public static void checkTwoBooleanForOneState(Boolean isState, Boolean isNotState, Consumer<Boolean> setStateParameter) {

        if(HandlerFilter.isAllNull(isState,isNotState)){
            return;
        }

        if (isState == isNotState) {
            return;
        }

        setStateParameter.accept(isState == null ? !isNotState : isState);
    }

    public static <FilteringBy extends Comparable<FilteringBy>> void filtering(
            FilteringBy start, FilteringBy end, final FilteringBy MIN, final FilteringBy MAX,
            BiConsumer<FilteringBy, FilteringBy> setStateParameters) {

        if (HandlerFilter.isAllNull(start, end)) {
            return;
        }

        start = HandlerFilter.setOtherValueIftThisIsNull(start, MIN);

        end = HandlerFilter.setOtherValueIftThisIsNull(end, MAX);

        if (start.compareTo(end) > -1) {
            FilteringBy temp = start;
            start = end;
            end = temp;
        }

        setStateParameters.accept(start,end);
    }

    public static <D extends ChronoLocalDate> void checkDate(D startDate, D endDate, BiConsumer<D, D> elseBetween,
                                                                     Consumer<D> elseStart, Consumer<D> elseEnd) {

        if (HandlerFilter.isAllNull(startDate, endDate)) {
            return;
        }

        if (HandlerFilter.isAllNonNull(startDate, endDate)) {
            if (startDate.compareTo(endDate) > -1) {
                D temp = startDate;
                startDate = endDate;
                endDate = temp;
            }
            elseBetween.accept(startDate, endDate);
            return;
        }

        if (startDate != null) {
            elseStart.accept(startDate);
            return;
        }

        elseEnd.accept(endDate);
    }

    public static <OBJECT> void checkArray(OBJECT[] objects, Consumer<OBJECT[]> setObjects) {

        if(objects == null ||  objects.length == 0){
            return;
        }

        setObjects.accept(objects);
    }

    public static < D extends ChronoLocalDateTime> void checkDateTime(D startDate, D endDate, final D MIN, final D MAX, BiConsumer<D, D> setDateTimes) {
        filtering(startDate, endDate, MIN, MAX, setDateTimes);
    }

    public static < N extends Number & Comparable<N>> void checkNumberBetween(N start, N end, final N MIN, final N MAX, BiConsumer<N, N> setNumbers) {
         filtering(start, end, MIN, MAX, setNumbers);
    }

    public static <D extends ChronoLocalDate> void checkDate(D startDate, D endDate, final D MIN, final D MAX, BiConsumer<D, D> setDates) {
        if (HandlerFilter.isAllNull(startDate, endDate)) {
            return;
        }

        startDate = HandlerFilter.setOtherValueIftThisIsNull(startDate, MIN);

        endDate = HandlerFilter.setOtherValueIftThisIsNull(endDate, MAX);

        if (startDate.compareTo(endDate) > -1) {
            D temp = startDate;
            startDate = endDate;
            endDate = temp;
        }

        setDates.accept(startDate,endDate);
    }
}
