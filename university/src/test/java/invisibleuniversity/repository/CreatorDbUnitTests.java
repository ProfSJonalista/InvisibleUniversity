package invisibleuniversity.repository;

import invisibleuniversity.domain.Creator;
import invisibleuniversity.service.CreatorRepositoryImpl;
import invisibleuniversity.service.ICreatorRepository;
import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.URL;
import java.sql.DriverManager;

@RunWith(JUnit4.class)
public class CreatorDbUnitTests extends DBTestCase {
    public static String url = "jdbc:hsqldb:hsql://localhost/workdb";

    ICreatorRepository creatorRepository;

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        creatorRepository = new CreatorRepositoryImpl(DriverManager.getConnection(url));
    }

    @Test
    public void doNothing() {
        assertEquals(4, creatorRepository.getAllCreators().size());
    }

    @Test
    public void checkAdding() throws Exception {
        Creator creator = new Creator("Janek", "Jakubowski");

        assertEquals(1, creatorRepository.add(creator));

        IDataSet dbDataSet = this.getConnection().createDataSet();
        ITable actualTable = dbDataSet.getTable("CREATOR");
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable(actualTable, new String[]{"ID"});
        IDataSet expectedDataSet = getDataSet("ds-2.xml");
        ITable expectedTable = expectedDataSet.getTable("CREATOR");
        Assertion.assertEquals(expectedTable, filteredTable);
        creatorRepository.deleteById(creator.getId());
    }

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.INSERT;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.DELETE;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return this.getDataSet("ds-1.xml");
    }

    protected IDataSet getDataSet(String datasetName) throws Exception {
        URL url = getClass().getClassLoader().getResource(datasetName);
        FlatXmlDataSet ret = new FlatXmlDataSetBuilder().build(url.openStream());
        return ret;
    }
}
