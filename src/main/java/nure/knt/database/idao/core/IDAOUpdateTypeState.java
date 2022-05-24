package nure.knt.database.idao.core;

import nure.knt.entity.enums.TypeState;

public interface IDAOUpdateTypeState {
    public boolean updateTypeStateById(Long id, TypeState typeState);
}
