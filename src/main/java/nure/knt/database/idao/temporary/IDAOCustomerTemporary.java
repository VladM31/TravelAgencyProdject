package nure.knt.database.idao.temporary;

import nure.knt.entity.subordinate.CustomerTemporary;

public interface IDAOCustomerTemporary{
    public boolean emailIsBooked(String email);
    public CustomerTemporary getCustomerTemporaryByEmail(String email);
    public CustomerTemporary getCustomerTemporaryByCode(long code);
    public long getCodeByIdTempUser(CustomerTemporary id);
    public boolean save(CustomerTemporary ct);
}
