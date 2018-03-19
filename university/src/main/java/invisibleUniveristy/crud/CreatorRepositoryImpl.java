package invisibleUniveristy.crud;

import invisibleUniveristy.invention.Creator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CreatorRepositoryImpl implements ICreatorRepository {
    private Connection connection;

    private PreparedStatement addCreatorStatement;
    private PreparedStatement getAllCreatorsStatement;
    private PreparedStatement deleteCreatorStatement;
    private PreparedStatement updateCreatorStatement;
    private PreparedStatement getCreatorByIdStatement;
    private PreparedStatement dropTableStatement;

    public CreatorRepositoryImpl(Connection connection) throws SQLException {
        this.connection = connection;
        if (!isDatabaseReady()) {
            createTables();
        }

        setConnection(connection);
    }

    public void createTables() throws SQLException {
        connection.createStatement().executeUpdate(
                "CREATE TABLE " +
                        "Creator (id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL," +
                        "name VARCHAR(20) NOT NULL," +
                        "surname VARCHAR(20) NOT NULL" +
                        ")"
        );
    }

    public boolean isDatabaseReady() {
        try {
            ResultSet rs = connection.getMetaData().getTables(null, null, null, null);

            boolean tableExists = false;

            while (rs.next()) {
                if ("Creator".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
                    tableExists = true;
                    break;
                }
            }
            return tableExists;
        } catch (SQLException e) {
            System.out.println("-----------isDatabaseReady--------------");
            e.printStackTrace();
            System.out.println("-----------isDatabaseReady--------------");
            return false;
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) throws SQLException {
        this.connection = connection;

        addCreatorStatement = connection.prepareStatement("INSERT INTO Creator (name, surname) VALUES (?, ?)");
        getAllCreatorsStatement = connection.prepareStatement("SELECT * FROM Creator");
        deleteCreatorStatement = connection.prepareStatement("DELETE FROM Creator WHERE id = ?");
        updateCreatorStatement = connection.prepareStatement("UPDATE Creator SET name = ?, surname = ? WHERE id = ?");
        getCreatorByIdStatement = connection.prepareStatement("SELECT * FROM Creator WHERE id = ?");
        dropTableStatement = connection.prepareStatement("DROP TABLE Creator");
    }

    @Override
    public List<Creator> getAllCreators() {
        List<Creator> creators = new ArrayList<>();
        try{
            ResultSet rs = getAllCreatorsStatement.executeQuery();

            while (rs.next()){
                Creator creator = new Creator();

                creator.setId(rs.getLong("id"));
                creator.setName(rs.getString("name"));
                creator.setSurname(rs.getString("surname"));

                creators.add(creator);
            }
        } catch (SQLException e){
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }

        return creators;
    }

    @Override
    public int add(Creator creator) {
        int count = 0;
        try{
            addCreatorStatement.setString(1, creator.getName());
            addCreatorStatement.setString(2, creator.getSurname());
            count = addCreatorStatement.executeUpdate();
        } catch (SQLException e){
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        return count;
    }

    @Override
    public Creator getCreatorById(Long id) {
        Creator creator = new Creator();

        try{
            getCreatorByIdStatement.setLong(1, id);
            ResultSet rs = getCreatorByIdStatement.executeQuery();

            while (rs.next()){
                creator.setId(rs.getLong("id"));
                creator.setName(rs.getString("name"));
                creator.setSurname(rs.getString("surname"));
            }
        } catch (SQLException e){
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }

        return creator;
    }

    @Override
    public int deleteById(Long id) {
        int count = 0;
        try {
            deleteCreatorStatement.setLong(1, id);
            count = deleteCreatorStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        return count;
    }

    @Override
    public int updateById(Creator creator) {
        int count = 0;

        try {
            updateCreatorStatement.setLong(3, creator.getId());
            updateCreatorStatement.setString(1, creator.getName());
            updateCreatorStatement.setString(2, creator.getSurname());
            count = updateCreatorStatement.executeUpdate();
        } catch (SQLException e){
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        return count;
    }

    @Override
    public void dropTable(){
        try{
            dropTableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
    }
}