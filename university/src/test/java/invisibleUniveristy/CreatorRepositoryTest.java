package invisibleUniveristy;

import invisibleUniveristy.domain.Creator;
import invisibleUniveristy.service.Creator.CreatorRepositoryImpl;
import invisibleUniveristy.service.Creator.ICreatorRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;
@Ignore
public class CreatorRepositoryTest {
    ICreatorRepository creatorRepository;

    private final static String NAME_1 = "Janek";
    private final static String SURNAME_1 = "Radomski";

    @Before
    public void setup() throws SQLException{
        creatorRepository = new CreatorRepositoryImpl();
    }

    @After
    public void cleanup() throws SQLException {
    }

    @Test
    public void checkConnection() {
        assertNotNull(creatorRepository.getConnection());
    }

    @Test
    public void checkAdding() throws SQLException{
        Creator creator = new Creator();
        creator.setName(NAME_1);
        creator.setSurname(SURNAME_1);

        assertEquals(1, creatorRepository.add(creator));

        List<Creator> creators = creatorRepository.getAllCreators();
        Creator creatorRetrieved = creators.get(0);

        assertEquals(NAME_1, creatorRetrieved.getName());
        assertEquals(SURNAME_1, creatorRetrieved.getSurname());
    }
}
