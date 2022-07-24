package nure.knt.database.dao.mysql.entity;

import nure.knt.database.idao.entity.IDAOUserWithTerms;
import nure.knt.database.idao.terms.ITermInformation;
import nure.knt.database.idao.terms.fieldenum.IUserField;
import nure.knt.database.idao.terms.users.ITermTravelAgency;
import nure.knt.entity.important.TravelAgency;

import java.util.List;
import java.util.Optional;

public class RepositoryTravelAgencyMySQL extends MySQLUserCore<TravelAgency> implements IDAOUserWithTerms<TravelAgency, ITermTravelAgency> {



    @Override
    public boolean saveAll(Iterable<TravelAgency> entities) {
        return false;
    }

    @Override
    public boolean save(TravelAgency entity) {
        return false;
    }

    @Override
    public int[] updateAllById(Iterable<TravelAgency> entities, IUserField... fields) {
        return new int[0];
    }

    @Override
    public int updateOneById(TravelAgency entity, IUserField... fields) {
        return 0;
    }

    @Override
    public Optional<TravelAgency> findOneBy(ITermInformation information) {
        return Optional.empty();
    }

    @Override
    public List<TravelAgency> findBy(ITermInformation information) {
        return null;
    }

    @Override
    public ITermTravelAgency term() {
        return null;
    }
}

class HandlerRepositoryTravelAgencyMySQL{


}
