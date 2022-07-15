package nure.knt.database.idao.terms;

public interface ITermCore {
    public String getLimit();
    public String getWhere();
    public String getJoin();
    public String getGroupBy();
    public String getHaving();
    public String getSelectField();
    public Object[] getParameters();

}
