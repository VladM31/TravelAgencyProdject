package nure.knt.database.idao.terms;

public interface ITermCore {
    public String getLimit();
    public String getWhere();
    public String getJoin();
    public String getOrderBy();
    public String getSelectField();
    public ITermCore concatTerm(ITermCore termCore);
}
