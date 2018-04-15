package invisibleUniveristy;

import invisibleUniveristy.service.CreatorRepositoryImpl;
import invisibleUniveristy.service.ICreatorRepository;
import invisibleUniveristy.domain.Creator;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

    @Before
    public void setupDatabase() throws SQLException{
        Creator creator1 = new Creator(1L, "Gracjan", "Roztocki");
        Creator creator2 = new Creator(2L, "Pablo", "Escobar");

        List<Creator> creatorList = new ArrayList();
        creatorList.add(creator1);
        creatorList.add(creator2);

        when(connectionMock.prepareStatement("INSERT INTO Creator (name, surname) VALUES (?, ?)")).thenReturn(insertStatementMock);
        when(connectionMock.prepareStatement("UPDATE Creator SET name = ?, surname = ? WHERE id = ?")).thenReturn(updateStatementMock);
        when(connectionMock.prepareStatement("DELETE FROM Creator WHERE id = ?")).thenReturn(deleteStatementMock);
        when(connectionMock.prepareStatement("SELECT * FROM Creator")).thenReturn(getAllStatementMock);
        when(connectionMock.prepareStatement("SELECT * FROM Creator WHERE id = ?")).thenReturn(getByIdStatementMock);

        creatorRepository = new CreatorRepositoryImpl();
        creatorRepository.setConnection(connectionMock);

        verify(connectionMock).prepareStatement("INSERT INTO Creator (name, surname) VALUES (?, ?)");
        verify(connectionMock).prepareStatement("UPDATE Creator SET name = ?, surname = ? WHERE id = ?");
        verify(connectionMock).prepareStatement("DELETE FROM Creator WHERE id = ?");
        verify(connectionMock).prepareStatement("SELECT * FROM Creator");
        verify(connectionMock).prepareStatement("SELECT * FROM Creator WHERE id = ?");
    }

    @Test
    public void checkAdding() throws Exception{
        when(insertStatementMock.executeUpdate()).thenReturn(1);

        Creator creator = new Creator(3L, "Karolina", "Radziecka");
        assertEquals(1, creatorRepository.add(creator));
        verify(insertStatementMock, times(1)).setString(1, "Karolina");
        verify(insertStatementMock, times(1)).setString(2, "Radziecka");
        verify(insertStatementMock).executeUpdate();
    }

    @Test
    public void checkUpdate() throws Exception{
        when(updateStatementMock.executeUpdate()).thenReturn(1);

        Creator creatorToUpdate = new Creator(2L, "Wacław", "Parumski");
        assertEquals(1, creatorRepository.updateById(creatorToUpdate));

    }

    @Ignore
    @Test
    public void checkGetById() throws Exception{
        when(getByIdStatementMock.executeUpdate()).thenReturn(1);

        Creator creator = creatorRepository.getCreatorById(2L);
        assertNotNull(creator.getSurname());
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
    }
}
