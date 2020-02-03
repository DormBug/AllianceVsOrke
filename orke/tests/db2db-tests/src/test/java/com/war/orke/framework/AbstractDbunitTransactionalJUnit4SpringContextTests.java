package com.war.orke.framework;

import org.dbunit.Assertion;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.test.context.transaction.AfterTransaction;

import javax.sql.DataSource;
import java.lang.reflect.Method;

/**
 * Main class, that must be extended by test class, which want to use DBUnit for db2db testing.
 *
 * Test method must have annotating {@link DbunitDataSets} with 'before' and 'after' properties:
 *
 * 1) 'before' - path to xml file, which contains tables and data in it, that will be initialized before test running.
 *      Method 'beforeTestMethod' in {@link DbunitTestExecutionListener} process that.
 * 2) 'after' = path to xml file, which contains tables and data in it, that expected after test running
 *      Method 'assertAfterTransaction' in {@link AbstractDbunitTransactionalJUnit4SpringContextTests}
 *      compares actual data after test running and expected data, which is in xml file by path by property 'after'.
 * Also, Test class must be annotated by @Rollback(false), or test running will be ignored.
 *
 * @see DbunitDataSets
 * @see DbunitTestExecutionListener
 * @see AbstractDbunitTransactionalJUnit4SpringContextTests
 * */
@TestExecutionListeners(AbstractDbunitTransactionalJUnit4SpringContextTests.DbunitTestExecutionListener.class)
public abstract class AbstractDbunitTransactionalJUnit4SpringContextTests
        extends AbstractTransactionalJUnit4SpringContextTests {

    private IDatabaseTester databaseTester;
    private String afterDatasetFileName;

    @AfterTransaction
    public void assertAfterTransaction() throws Exception {
        if (databaseTester == null || afterDatasetFileName == null) {
            return;
        }
        IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
        IDataSet expectedDataSet =
                new XmlDataSet(ClassLoader.getSystemResourceAsStream(afterDatasetFileName));
        Assertion.assertEquals(expectedDataSet, databaseDataSet);
        databaseTester.onTearDown();
    }

    public static class DbunitTestExecutionListener extends AbstractTestExecutionListener {

        public void beforeTestMethod(TestContext testContext) throws Exception {
            AbstractDbunitTransactionalJUnit4SpringContextTests testInstance =
                    (AbstractDbunitTransactionalJUnit4SpringContextTests) testContext.getTestInstance();
            Method method = testContext.getTestMethod();

            DbunitDataSets annotation = method.getAnnotation(DbunitDataSets.class);
            if (annotation == null) {
                return;
            }

            DataSource dataSource = testContext.getApplicationContext().getBean(DataSource.class);
            IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
            databaseTester.setDataSet(
                    new XmlDataSet(ClassLoader.getSystemResourceAsStream(annotation.before())));
            databaseTester.onSetup();
            testInstance.databaseTester = databaseTester;
            testInstance.afterDatasetFileName = annotation.after();
        }
    }
}