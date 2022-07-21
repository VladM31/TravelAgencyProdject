package nure.knt.database.idao.terms.users;

public interface ITermCustomer extends  ITermUser<ITermCustomer>{
    public ITermCustomer customerIdIn(Long ...customerIds);
    public ITermCustomer maleIs(Boolean mela);
    public ITermCustomer firstNameContaining(String firstname);
    public ITermCustomer lastNameContaining(String firstname);
}
