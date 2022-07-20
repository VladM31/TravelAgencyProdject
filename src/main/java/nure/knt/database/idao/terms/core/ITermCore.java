package nure.knt.database.idao.terms.core;

import nure.knt.database.idao.terms.ITermInformation;
import nure.knt.entity.enums.HowSortSQL;

import javax.validation.constraints.NotNull;

public interface ITermCore<E extends Enum<?>,IT extends ITermCore> extends ITermEnd,ITermLimit<IT>,ITermOrderBy<E,IT> {

}
