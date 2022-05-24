package nure.knt.database.idao.temporary;

import nure.knt.entity.subordinate.TravelAgencyTemporary;

public interface IDAOTravelAgencyTemporaryCode {
    public boolean emailIsBooked(String email);
    public TravelAgencyTemporary getTravelAgencyTemporaryByEmail(String email);
    public TravelAgencyTemporary getTravelAgencyTemporaryByCode(long code);
    public long getCodeByIdTempUser(TravelAgencyTemporary id);
    public boolean save(TravelAgencyTemporary ct);
    public boolean setCodeConfirmedCode(TravelAgencyTemporary ct);
}
