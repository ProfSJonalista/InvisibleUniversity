package invisibleuniversity;

import invisibleuniversity.service.CreatorRepositoryImpl;
import invisibleuniversity.service.ICreatorRepository;
import invisibleuniversity.domain.Creator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.hamcrest.CoreMatchers.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MockedTests {

    ICreatorRepository creatorRepository;

    @Mock
    Connection connectionMock;

    @Mock
    PreparedStatement insertStatementMock;

    @Mock
    PreparedStatement updateStatementMock;

    @Mock
    PreparedStatement deleteStatementMock;

    @Mock
    PreparedStatement getAllStatementMock;

    @Mock
    PreparedStatement getByIdStatementMock;

    @Mock
    ICreatorRepository creatorRepositoryMock;

    @Before
    public void setupDatabase() throws SQLException {
        when(connectionMock.prepareStatement("INSERT INTO Creator (name, surname) VALUES (?, ?)")).thenReturn(insertStatementMock);
        when(connectionMock.prepareStatement("UPDATE Creator SET name = ?, surname = ? WHERE id = ?")).thenReturn(updateStatementMock);
        when(connectionMock.prepareStatement("DELETE FROM Creator WHERE id = ?")).thenReturn(deleteStatementMock);
        when(connectionMock.prepareStatement("SELECT * FROM Creator")).thenReturn(getAllStatementMock);
        when(connectionMock.prepareStatement("SELECT * FROM Creator WHERE id = ?")).thenReturn(getByIdStatementMock);

        creatorRepository = new CreatorRepositoryImpl();
        creatorRepositoryMock = mock(CreatorRepositoryImpl.class);
        creatorRepository.setConnection(connectionMock);

        verify(connectionMock).prepareStatement("INSERT INTO Creator (name, surname) VALUES (?, ?)");
        verify(connectionMock).prepareStatement("UPDATE Creator SET name = ?, surname = ? WHERE id = ?");
        verify(connectionMock).prepareStatement("DELETE FROM Creator WHERE id = ?");
        verify(connectionMock).prepareStatement("SELECT * FROM Creator");
        verify(connectionMock).prepareStatement("SELECT * FROM Creator WHERE id = ?");
    }

    @Test
    public void checkUpdate() throws Exception {
        Creator creator = new Creator(1L, "Jan", "Radziecki");
        doReturn(creator).when(creatorRepositoryMock).getCreatorById(1L);

        Creator creator1 = new Creator(2L, "Michał", "Targosiński");
        doReturn(creator1).when(creatorRepositoryMock).getCreatorById(2L);

        Creator creatorToUpdate = creatorRepositoryMock.getCreatorById(1L);
        creatorToUpdate.setName("Jakub");
        creatorRepository.updateById(creatorToUpdate);

        assertThat(creatorRepositoryMock.getCreatorById(1L).getName(), is(creatorToUpdate.getName()));
        assertEquals(creatorRepositoryMock.getCreatorById(1L).getName(), creatorToUpdate.getName());
        verify(updateStatementMock, times(1)).setString(1, "Jakub");
        verify(updateStatementMock, times(1)).setString(2, "Radziecki");
        assertThat(creatorRepositoryMock.getCreatorById(2L).getName(), not(creatorRepositoryMock.getCreatorById(1L).getName()));
        verify(updateStatementMock).executeUpdate();
    }

    @Test
    public void checkAdding() throws Exception {
        when(insertStatementMock.executeUpdate()).thenReturn(1);

        Creator creator = new Creator(3L, "Karolina", "Radziecka");
        assertEquals(1, creatorRepository.add(creator));
        verify(insertStatementMock, times(1)).setString(1, "Karolina");
        verify(insertStatementMock, times(1)).setString(2, "Radziecka");
        verify(insertStatementMock).executeUpdate();
    }

    abstract class AbstractResultSet implements ResultSet {
        int i = 0;

        @Override
        public long getLong(String s) throws SQLException {
            return 1L;
        }

        @Override
        public String getString(String columnLabel) throws SQLException {
            switch (columnLabel) {
                case "name":
                    return "Jan";
                case "surname":
                    return "Miętki";
                default:
                    return "";
            }
        }

        @Override
        public boolean next() throws SQLException {
            if (i == 1)
                return false;
            i++;
            return true;
        }
    }

    abstract class AbstractResultSetById implements ResultSet {
        int i = 0;

        @Override
        public long getLong(String s) throws SQLException {
            return 1L;
        }

        @Override
        public String getString(String columnLabel) throws SQLException {
            switch (columnLabel) {
                case "name":
                    return "Jan";
                case "surname":
                    return "Miętki";
                default:
                    return "";
            }
        }

        @Override
        public boolean next() throws SQLException {
            if (i == 1)
                return false;
            i++;
            return true;
        }
    }

    @Test
    public void checkGetting() throws Exception {
        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getLong("id")).thenCallRealMethod();
        when(mockedResultSet.getString("name")).thenCallRealMethod();
        when(mockedResultSet.getString("surname")).thenCallRealMethod();
        when(getAllStatementMock.executeQuery()).thenReturn(mockedResultSet);

        assertEquals(1, creatorRepository.getAllCreators().size());

        verify(getAllStatementMock, times(1)).executeQuery();
        verify(mockedResultSet, times(1)).getLong("id");
        verify(mockedResultSet, times(1)).getString("name");
        verify(mockedResultSet, times(1)).getString("surname");
        verify(mockedResultSet, times(2)).next();
    }

    @Test
    public void checkGettingById() throws Exception {
        AbstractResultSetById mockedResultSet = mock(AbstractResultSetById.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getLong("id")).thenCallRealMethod();
        when(mockedResultSet.getString("name")).thenCallRealMethod();
        when(mockedResultSet.getString("surname")).thenCallRealMethod();
        when(getByIdStatementMock.executeQuery()).thenReturn(mockedResultSet);

        assertNotNull(creatorRepository.getCreatorById(1L));

        verify(getByIdStatementMock, times(1)).executeQuery();
        verify(mockedResultSet, times(1)).getLong("id");
        verify(mockedResultSet, times(1)).getString("name");
        verify(mockedResultSet, times(1)).getString("surname");
        verify(mockedResultSet, times(2)).next();
    }

    @Test
    public void checkAddingInOrder() throws Exception {
        InOrder inorder = inOrder(insertStatementMock);
        when(insertStatementMock.executeUpdate()).thenReturn(1);

        Creator creator = new Creator(1L, "Karolina", "Radziecka");
        assertEquals(1, creatorRepository.add(creator));

        inorder.verify(insertStatementMock, times(1)).setString(1, "Karolina");
        inorder.verify(insertStatementMock, times(1)).setString(2, "Radziecka");
        inorder.verify(insertStatementMock).executeUpdate();
    }

    @Test(expected = IllegalStateException.class)
    public void checkExceptionWhenAddingNullAdding() throws Exception {
        when(insertStatementMock.executeUpdate()).thenThrow(new SQLException());

        Creator creator = new Creator(1L, null, "Radziecka");
        assertEquals(1, creatorRepository.add(creator));
    }

    @Test
    public void checkDelete() throws SQLException {
        when(deleteStatementMock.executeUpdate()).thenReturn(1);
        assertEquals(1, creatorRepository.deleteById(1L));
        verify(deleteStatementMock, times(1)).executeUpdate();
    }
}
