package nure.knt.database.dao.mysql.tools;

import nure.knt.database.idao.IConnectorGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("My_SQL_Core")
public class MySQLCore {
    protected IConnectorGetter conn;

    @Autowired
    public void setConn(IConnectorGetter conn) {
        this.conn = conn;
    }
}
