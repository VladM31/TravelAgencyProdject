package nure.knt.database.dao.mysql.tools;

import nure.knt.database.idao.IConnectorGetter;
import nure.knt.tools.WorkWithCountries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component("Countries_MySQL")
public class WorkWithCountriesMySQL implements WorkWithCountries {

    private String[] countries;
    private static final String COUNT_COUNTIES = "SELECT COUNT(*) AS size FROM country;";
    private static final String SELECT_COUNTIES = "SELECT name FROM country;";

    @Autowired
    public WorkWithCountriesMySQL(IConnectorGetter connector){
        try(java.sql.Statement statement = connector.getSqlStatement();) {

            try( java.sql.ResultSet getterSize = statement.executeQuery(COUNT_COUNTIES);){
                if (getterSize.next()){
                    countries = new String[getterSize.getInt("size")];
                }else{
                    throw new RuntimeException("Country table is empty");
                }
            }finally {
            }

            try(  java.sql.ResultSet resultSet = statement.executeQuery(SELECT_COUNTIES)){
                int index = 0;
                while(resultSet.next()){
                    countries[index++] = resultSet.getString("name");
                }
            }finally {
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }
    }

    @Override
    public String[] getCountry() {
        return this.countries;
    }

    @Override
    public int getIdByCountry(String country) {
        for (int id = 0; id < this.countries.length; id++) {
            if(this.countries[id].equals(country)){
                return id;
            }
        }
        return WorkWithCountries.NAME_NOT_FOUND;
    }

}
