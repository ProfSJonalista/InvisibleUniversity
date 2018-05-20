package invisibleuniversity.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import invisibleuniversity.domain.Creator;
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
    @ExpectedDatabase(value = "/addCreator.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void getCreatorCheck() throws Exception {
        assertEquals(2, sellingManager.getAllCreators().size());

        Creator c = new Creator("Krystian", "Dzięcielski", "terminator");

        sellingManager.addCreator(c);
        assertEquals(3, sellingManager.getAllCreators().size());
    }
}
