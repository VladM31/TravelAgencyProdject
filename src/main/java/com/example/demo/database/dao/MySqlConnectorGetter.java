package com.example.demo.database.dao;

import com.example.demo.MyConstants;
import com.example.demo.database.idao.IConnectorGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.*;

@Component
@Scope("singleton")
public class MySqlConnectorGetter implements IConnectorGetter {

    private Connection conn;

    @Override
    public Statement getSqlStatement() throws SQLException{
        return conn.createStatement();
    }

    @Override
    public PreparedStatement getSqlPreparedStatement(String script) throws SQLException {
        return conn.prepareStatement(script);
    }


    @PostConstruct
    public void init(){
        try {
            this.conn = DriverManager.getConnection(MyConstants.URL, MyConstants.MYSQL_USER, MyConstants.MYSQL_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @PreDestroy
    public void destroy(){
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
