package nure.knt.database.idao.terms.users;

public interface ITermCustomer extends  ITermUser<ITermCustomer>{
    public ITermCustomer customerIdIn(Long ...customerIds);
    public ITermCustomer maleIs(Boolean mela);
}
