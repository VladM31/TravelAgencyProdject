package nure.knt.database.idao.factory.fiction;

import nure.knt.entity.subordinate.MessageShortData;

public interface IFactoryInsideMessageShortData {
    public MessageShortData getMessageShortData(java.sql.ResultSet resultSet);
}
