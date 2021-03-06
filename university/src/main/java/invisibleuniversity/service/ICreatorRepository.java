package invisibleuniversity.service;

import invisibleuniversity.domain.Creator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ICreatorRepository {

    public Connection getConnection();
    public void setConnection(Connection connection) throws SQLException;

    //CRUD
    List<Creator> getAllCreators();
    int add(Creator creator);
    Creator getCreatorById(Long id);
    int deleteById(Long id);
    int updateById(Creator creator);
    void dropTable();
    String introduceSelf();
}
