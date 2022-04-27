package com.example.demo.tempClasses.dao.temporary;

import com.example.demo.database.idao.temporary.IDAOTravelAgencyTemporaryCode;
import com.example.demo.entity.subordinate.TravelAgencyTemporary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class DAOTravelAgencyTemporaryCode implements IDAOTravelAgencyTemporaryCode {

    static Map<TravelAgencyTemporary,Long> databases = new HashMap<>();
    static final long erorrCode = -1;
    static Random ran = new Random();

    static long minimumRangeNumber = 10000000;
    static long maximumRangeNumber = 99999999;

    @Override
    public boolean emailIsBooked(String email) {

        return this.getTravelAgencyTemporaryByEmail(email) != null;
    }

    @Override
    public TravelAgencyTemporary getTravelAgencyTemporaryByEmail(String email) {
        for (var iter : databases.keySet()) {
            if(iter.getEmail().equals(email)){
                return iter;
            }
        }
        return null;
    }

    @Override
    public TravelAgencyTemporary getTravelAgencyTemporaryByCode(long code) {
        for (var iter : databases.entrySet()) {
            if(iter.getValue() == code){
                return iter.getKey();
            }
        }
        return null;
    }

    @Override
    public long getCodeByIdTempUser(TravelAgencyTemporary TATemp) {
        for (var iter : databases.entrySet()) {
            if(iter.getKey().equals(TATemp)){
                return iter.getValue();
            }
        }
        return erorrCode;
    }

    @Override
    public boolean save(TravelAgencyTemporary tat) {
        databases.put(tat,ran.nextLong(minimumRangeNumber,maximumRangeNumber));
        return true;
    }
}
