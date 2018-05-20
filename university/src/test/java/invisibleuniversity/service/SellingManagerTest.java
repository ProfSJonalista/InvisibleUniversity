package invisibleuniversity.service;

import invisibleuniversity.domain.Creator;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.assertEquals;
import java.util.List;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/resources/beans.xml"})
@Rollback
//@Commit
@Transactional(transactionManager = "txManager")
public class SellingManagerTest {
    @Autowired
    SellingManager sellingManager;

    private final String NAME_1 = "Krystian";
    private final String SURNAME_1 = "Dzięcielski";
    private final String NICKNAME_1 = "terminator";

    private final String NAME_2 = "Marek";
    private final String SURNAME_2 = "Horski";
    private final String NICKNAME_2 = "terminator";

    private final String NAME_3 = "Jan";
    private final String SURNAME_3 = "Mietki";
    private final String NICKNAME_3 = "ProfSJonalista";

    private final String INVENTION_NAME_1 = "Gamepad";
    private final String INVENTION_DESCRIPTION_1 = "Device to play games";
    private final boolean DEADLY_1 = false;

    private final String INVENTION_NAME_2 = "Chainsaw";
    private final String INVENTION_DESCRIPTION_2 = "Used to cutting trees and unliving the undeads";
    private final boolean DEADLY_2 = true;

    private final String INVENTION_NAME_3 = "Highly advanced AI";
    private final String INVENTION_DESCRIPTION_3 = "Cool computer to say sorry";
    private final boolean DEADLY_3 = false;

    @Test
    public void addCreatorCheck(){
        List<Creator> retrievedCreators = sellingManager.getAllCreators();
        for (Creator creator1 : retrievedCreators) {
            if (creator1.getNickname().equals(NICKNAME_1))
                sellingManager.deleteCreator(creator1);
        }

        Creator creator = new Creator(NAME_1, SURNAME_1, NICKNAME_1);

        sellingManager.addCreator(creator);

        Creator retrievedCreator = sellingManager.findCreatorByNickname(NICKNAME_1);

        assertEquals(NAME_1, retrievedCreator.getName());
        assertEquals(SURNAME_1, retrievedCreator.getSurname());
        assertEquals(NICKNAME_3, retrievedCreator.getNickname());
    }
}
