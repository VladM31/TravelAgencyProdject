package nure.knt.database.idao.registration;

import nure.knt.entity.important.TravelAgency;

import java.util.List;

public interface IDAOFindAllTravelAgencyForRegistration {

    public List<TravelAgency> findByTypeStateRegistrationAndCodeConfirmedTrue();
    public boolean updateCodeConfirmed(boolean state,Long idTravelAgecny);
}
