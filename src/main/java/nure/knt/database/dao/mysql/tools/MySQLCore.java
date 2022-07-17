package nure.knt.database.dao.mysql.tools;

import nure.knt.database.idao.tools.IConnectorGetter;
import org.springframework.beans.factory.annotation.Autowired;

public class MySQLCore {
    protected IConnectorGetter conn;

    @Autowired
    public void setConn(IConnectorGetter conn) {
        this.conn = conn;
    }
}
