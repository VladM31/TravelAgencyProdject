package nure.knt.database.idao.terms.core;

public interface ITermLimit<IT> {
    IT limitIs(Long ...limits);
}
