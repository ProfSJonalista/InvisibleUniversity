package invisibleuniversity.service;

import java.sql.*;

public class CreatorRepositoryFactory{
    public static ICreatorRepository getInstance(){
        try {
            String url = "jdbc:hsqldb:hsql://localhost/workdb";
            return new CreatorRepositoryImpl(DriverManager.getConnection(url));
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
