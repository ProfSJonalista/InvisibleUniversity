package invisibleuniversity.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import invisibleuniversity.domain.Creator;
import invisibleuniversity.domain.Invention;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/beans.xml"})
@Rollback
//@Commit
@Transactional(transactionManager = "txManager")
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class SellingManagerDBUnitTest {
    @Autowired
    SellingManager sellingManager;

    @Test
    @DatabaseSetup("/fullData.xml")
    @ExpectedDatabase(value = "/getCreators.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void getCreatorCheck() throws Exception {
        assertEquals(3, sellingManager.getAllCreators().size());
    }

    @Test
    @DatabaseSetup("/fullData.xml")
    @ExpectedDatabase(value = "/addCreator.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void addCreatorCheck() throws Exception {
        assertEquals(3, sellingManager.getAllCreators().size());

        Creator c = new Creator("Krystian", "Escobar", "Rzabka");
        sellingManager.addCreator(c);

        assertEquals(4, sellingManager.getAllCreators().size());
    }

    @Test
    @DatabaseSetup("/fullData.xml")
    @ExpectedDatabase(value = "/deleteCreator.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void deleteCreator() throws Exception {
        Creator creatorToDelete = sellingManager.findCreatorByNickname("terminator");

        sellingManager.deleteCreator(creatorToDelete);

        assertEquals(2, sellingManager.getAllCreators().size());
    }

    @Test
    @DatabaseSetup("/fullData.xml")
    @ExpectedDatabase(value = "/updateCreator.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void updateCreator() throws Exception {
        Creator creatorToUpdate = sellingManager.findCreatorByNickname("terminator");

        creatorToUpdate.setSurname("Escobar");

        sellingManager.updateCreator(creatorToUpdate);

        Creator c = sellingManager.findCreatorByNickname("ProfSJonalista");

        assertThat(c.getSurname(), not(creatorToUpdate.getSurname()));
    }

    @Test
    @DatabaseSetup("/fullData.xml")
    @ExpectedDatabase(value = "/getSingleCreator.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void getSingleCreator() throws Exception {
        Creator creator = sellingManager.findCreatorByNickname("ProfSJonalista");

        assertNotNull(creator);
        assertEquals(creator.getName(), "Jan");
    }

    @Test
    @DatabaseSetup("/fullData.xml")
    @ExpectedDatabase(value = "/getInvention.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void getInvention() throws Exception {
        Invention invention = sellingManager.findInventionById(6L);

        assertNotNull(invention);
        assertEquals(invention.getName(), "Highly advanced AI");
    }

    @Test
    @DatabaseSetup("/fullData.xml")
    @ExpectedDatabase(value = "/inventions.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void getUsersInventions() throws Exception {
        Creator creator = sellingManager.findCreatorByNickname("ProfSJonalista");

        assertNotNull(creator);
        assertNotNull(creator.getInventions());
        assertEquals(2, creator.getInventions().size());
    }

    @Test
    @DatabaseSetup("/fullData.xml")
    @ExpectedDatabase(value = "/disposeInvention.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void disposeInvention() throws Exception{
        Creator creator = sellingManager.findCreatorByNickname("ProfSJonalista");

        assertEquals(2, creator.getInventions().size());

        Invention invention = creator.getInventions().get(0);
        sellingManager.disposeInvention(creator, invention);

        assertEquals(1, creator.getInventions().size());
    }
}
