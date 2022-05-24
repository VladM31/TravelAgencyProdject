package nure.knt.tempClasses.dao.temporary;

import nure.knt.database.idao.temporary.IDAOCustomerTemporary;
import nure.knt.entity.subordinate.CustomerTemporary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class DAOCustomerTemporary implements IDAOCustomerTemporary {

    static Map<CustomerTemporary,Long> databases = new HashMap<>();
    static final long erorrCode = -1;
    static Random ran = new Random();

    static long minimumRangeNumber = 10000000;
    static long maximumRangeNumber = 99999999;

    @Override
    public boolean emailIsBooked(String email) {

        return this.getCustomerTemporaryByEmail(email) != null;
    }

    @Override
    public CustomerTemporary getCustomerTemporaryByEmail(String email) {
        for (var iter : databases.keySet()) {
            if(iter.getEmail().equals(email)){
                return iter;
            }
        }
        return null;
    }



    @Override
    public CustomerTemporary getCustomerTemporaryByCode(long code) {
        for (var iter : databases.entrySet()) {
            if(iter.getValue() == code){
                return iter.getKey();
            }
        }
        return null;
    }

    @Override
    public long getCodeByIdTempUser(CustomerTemporary custTemp) {
        for (var iter : databases.entrySet()) {
            if(iter.getKey().equals(custTemp)){
                return iter.getValue();
            }
        }
        return erorrCode;
    }

    @Override
    public boolean save(CustomerTemporary ct) {
        databases.put(ct,ran.nextLong(minimumRangeNumber,maximumRangeNumber));
        return true;
    }
}
