package invisibleuniversity;

import invisibleuniversity.repository.CreatorDbUnitTests;
import invisibleuniversity.service.CreatorRepositoryImpl;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.sql.DriverManager;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CreatorDbUnitTests.class
})
public class CreatorTests {
    @BeforeClass
    public static void before() throws Exception {
        String url = "jdbc:hsqldb:hsql://localhost/workdb";

        new CreatorRepositoryImpl(DriverManager.getConnection(url));
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.hsqldb.jdbcDriver" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:hsqldb:hsql://localhost/workdb" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "sa" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "" );

        JdbcDatabaseTester databaseTester = new PropertiesBasedJdbcDatabaseTester();

        FlatXmlDataSet dataSet = new FlatXmlDataSetBuilder().build(
                CreatorTests.class.getClassLoader().
                        getResource("ds-0.xml").openStream()
        );

        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    @AfterClass
    public static void after() {
    }
}
