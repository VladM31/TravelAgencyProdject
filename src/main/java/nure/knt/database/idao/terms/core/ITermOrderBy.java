package nure.knt.database.idao.terms.core;

import nure.knt.entity.enums.HowSortSQL;

import javax.validation.constraints.NotNull;

public interface ITermOrderBy<E,IT extends ITermOrderBy> {
    IT orderBy(@NotNull E orderByValue, @NotNull HowSortSQL sort);
}
