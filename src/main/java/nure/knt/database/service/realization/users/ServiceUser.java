package nure.knt.database.service.realization.users;

import nure.knt.database.idao.entity.IDAOUserWithTerms;
import nure.knt.database.idao.terms.ITermInformation;
import nure.knt.database.idao.terms.fieldenum.IUserField;
import nure.knt.database.idao.terms.users.ITermUser;
import nure.knt.database.service.implement.users.IServiceUser;
import nure.knt.entity.important.User;

import java.util.List;
import java.util.Optional;

public class ServiceUser<U extends User, IT extends ITermUser> implements IServiceUser<U,IT> {

    protected IDAOUserWithTerms<U,IT> dao;

    public ServiceUser(IDAOUserWithTerms<U,IT> dao){
        this.dao = dao;
    }

    @Override
    public boolean saveAll(Iterable<U> entities) {
        return dao.saveAll(entities);
    }

    @Override
    public boolean save(U entity) {
        return dao.save(entity);
    }

    @Override
    public int deleteAllById(Iterable<Long> ids) {
        return dao.deleteAllById(ids);
    }

    @Override
    public int deleteById(Long id) {
        return dao.deleteById(id);
    }

    @Override
    public int[] updateAllById(Iterable<U> entities, IUserField... fields) {
        return dao.updateAllById(entities,fields);
    }

    @Override
    public int updateOneById(U entity, IUserField... fields) {
        return dao.updateOneById(entity,fields);
    }

    @Override
    public boolean isBooked(U user) {
        return dao.isBooked(user);
    }

    @Override
    public Optional<U> findOneBy(ITermInformation information) {
        return dao.findOneBy(information);
    }

    @Override
    public List<U> findBy(ITermInformation information) {
        return dao.findBy(information);
    }

    @Override
    public IT term() {
        return dao.term();
    }
}
