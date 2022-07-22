package nure.knt.database.idao.factory;

import nure.knt.entity.subordinate.MessageShortData;

public interface IFactoryEntity<Entity> {
    public Entity getEntity(java.sql.ResultSet resultSet);
}
