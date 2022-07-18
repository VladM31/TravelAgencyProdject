package nure.knt.database.idao.terms;

import nure.knt.entity.enums.HowSortSQL;

import javax.validation.constraints.NotNull;

public interface ITermCore<E extends Enum,IT extends ITermCore> {

    IT limitIs(Long ...limits);
    IT orderBy(@NotNull E orderByValue, @NotNull HowSortSQL sort);
    ITermInformation end();
}
