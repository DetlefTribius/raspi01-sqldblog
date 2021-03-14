/**
 * 
 */
package db;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Detlef Tribius
 *
 * <p>
 * Die Testklasse muss im Namen auf 'Test' enden!
 * </p>
 */
class DBTest
{

    /**
     * logger
     */
    private final static Logger logger = LoggerFactory.getLogger(DBTest.class);    
    
    /**
     * DBManager dbManager - alle DB-Aktionen laufen ueber den DBManager
     */
    private DBManager dbManager;    
    
    /**
     * @throws java.lang.Exception
     */
    @BeforeAll
    static void setUpBeforeClass() throws Exception
    {
        logger.debug("setUpBeforeClass()...");
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterAll
    static void tearDownAfterClass() throws Exception
    {
        logger.debug("tearDownAfterClass()...");
    }

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception
    {
        this.dbManager = new DBManager();
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterEach
    void tearDown() throws Exception
    {
    }

    /**
     * testInsertData() - Ablage eines Datensatzes
     */
    @Test
    void testInsertData()
    {
        logger.debug("testInsertData()...");
        domain.Data data = new domain.Data(0.25, 0.35, 0.45, 0.55, 0.65, 0.75);
        try
        {
            this.dbManager.insert(data);
            logger.info(data.toString() + " angelegt.");
        }
        catch (SQLException exception)
        {
            fail("testInsertData()", exception);
        }        
    }
}
