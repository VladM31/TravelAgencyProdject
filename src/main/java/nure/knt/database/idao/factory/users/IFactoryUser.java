package nure.knt.database.idao.factory.users;

public interface IFactoryUser {
    public nure.knt.entity.important.User getUser(java.sql.ResultSet resultSet);
}
