package nure.knt.database.dao.mysql.tools;

import nure.knt.MyConstants;
import nure.knt.database.idao.IConnectorGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.sql.*;

@Component("MySQL Connector")
@Scope("singleton")
public class MySqlConnectorGetter implements IConnectorGetter{

    private Connection conn;

    @Autowired
    public MySqlConnectorGetter(@Value("${mysql.connector.url}")    String url,@Value("${mysql.connector.user}") String user,@Value("${mysql.connector.password}") String password){
        try {
            this.conn = DriverManager.getConnection(url, user,password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public Statement getSqlStatement() throws SQLException{
        synchronized (conn) {
             return conn.createStatement();
        }
    }

    @Override
    public PreparedStatement getSqlPreparedStatement(String script) throws SQLException {
        synchronized (conn) {
            return conn.prepareStatement(script);
        }
    }


    @PreDestroy
    public void destroy() throws IOException {
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
