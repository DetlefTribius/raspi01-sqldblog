/*
 * Created on 10.09.2005
 * 
 */
package db;

import java.lang.reflect.InvocationTargetException;

import domain.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBManager
{
    /**
     * Logger...
     */
    private final static Logger logger = LoggerFactory.getLogger(DBManager.class);    
    
    /** Name der Property-Datei, beinhaltet alle Systemeinstellungen */
    private final static String propertiesFile = "system.properties";
    /** jdbcDriverKey - Parametrisierung aus der properties-Datei */
    private final static String jdbcDriverKey = "jdbcDriver";
    /** jdbcUrlKey - Parametrisierung aus der properties-Datei */
    private final static String jdbcUrlKey = "jdbcUrl";
    /** jdbcUserKey - Parametrisierung aus der properties-Datei */
    private final static String jdbcUserKey = "jdbcUser";
    /** jdbcPasswordKey - Parametrisierung aus der properties-Datei */
    private final static String jdbcPasswordKey = "jdbcPassword";
    /** jdbcDriver - Name des JDBC-Treibers (HSQLDB: "org.hsqldb.jdbcDriver") */
    private static String jdbcDriver = null;
    /** jdbcUrl - Anmelde-URL (z.B. bei HSQLDB: "jdbc:hsqldb:hsql://localhost") */
    private static String jdbcUrl = null;
    /** jdbcUser - UserID zum DB-Zugriff (z.B. bei HSQLDB: "sa") */
    private static String jdbcUser = null;
    /** jdbcPassword - Passwort zum DB-Zugriff (HSQLDB: "") */
    private static String jdbcPassword = null;
    /** con - Connection-Objekt, haelt die Verbindung zu Datenbank */ 
    private static java.sql.Connection connection = null;
    static
    {
        new DBManager().new PropertiesLoader();
        if (DBManager.jdbcDriver == null)
        {
            System.err.println( "Can't read the jdbcDriver-Key in the properties file '" + DBManager.propertiesFile + "'! " );
        }
        if (DBManager.jdbcUrl == null)
        {
            System.err.println( "Can't read the jdbcUrl-Key in the properties file '" + DBManager.propertiesFile + "'! " );
        }
        if (DBManager.jdbcUser == null)
        {
            System.err.println( "Can't read the jdbcUser-Key in the properties file '" + DBManager.propertiesFile + "'! " );
        }
        if (DBManager.jdbcPassword == null)
        {
            System.err.println( "Can't read the jdbcPassword-Key in the properties file '" + DBManager.propertiesFile + "'! " );
        }
        if ((DBManager.jdbcDriver == null)
         || (DBManager.jdbcUrl == null)   
         || (DBManager.jdbcUser == null)   
         || (DBManager.jdbcPassword == null))   
        {
            System.exit(0);
        }
        try
        {
            Class.forName(jdbcDriver);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            System.exit( 0 );
        }
    }

    /**
     * Defaultkonstruktor des DBManagers, Erzeugen der notwendigen Datenstrukturen...
     *
     */
    public DBManager()
    {
    }

    /**
     * insert(Entity entity) - die Entity wird in der DB abgelegt.
     * @param entity
     * @return
     * @throws java.sql.SQLException
     */
    public int insert(Entity entity) throws java.sql.SQLException
    {
        int rowsAffected = 0;
        
        final java.sql.Connection conn = getConnection();
        
        java.sql.PreparedStatement preparedStatement = null;
        
        try
        {
            preparedStatement = conn.prepareStatement(entity.getInsertStatement()); 
            
            entity.fillPrepStmtForInsert(preparedStatement);
        
            rowsAffected = preparedStatement.executeUpdate();
                
        }
        finally
        {
            preparedStatement.close();
            logger.info("DBManager.insert(Entity)... finally{}");
        }

        {
            logger.info("DBManager insert(Entity): " + rowsAffected + " row...");
        }
        
        return rowsAffected;
    }
    
    /**
     * 
     * @param entityClass
     * @param select
     * @return
     * @throws java.sql.SQLException
     */
    public java.util.List<Entity> select(Class<? extends Entity> entityClass, String select) throws java.sql.SQLException 
    {
        final java.util.ArrayList<Entity> list = new java.util.ArrayList<>();
        
        final java.sql.Connection conn = getConnection();
       
        java.sql.PreparedStatement preparedStatement = null;
        
        java.sql.ResultSet resultSet = null;

        try
        {
            preparedStatement = conn.prepareStatement(select);
            
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next())
            {

                try
                {
                    final Entity entity = entityClass.getDeclaredConstructor().newInstance();
                    entity.setFromResult(resultSet);
                    list.add(entity);
                }
                catch (InstantiationException 
                     | IllegalAccessException 
                     | IllegalArgumentException
                     | InvocationTargetException 
                     | NoSuchMethodException 
                     | SecurityException exception)
                {
                    exception.printStackTrace();
                    break;
                }
            }
        }
        finally
        {
            resultSet.close(); 
            preparedStatement.close();
            logger.info("DBManager.select(Class, String)... finally{}");
        }
        {
            final int size = list.size();
            logger.info("DBManager select(Class, String): " + size + " rows...");
        }
        
        return list;
    }
    
    /**
     * select(DBSelect selectParam) - liefert eine Liste List<Entity>, die dem
     * selectParam entspricht
     * @param selectParam
     * @return
     */
    public java.util.List<Entity> select(DBSelect selectParam) throws java.sql.SQLException 
    {
        
        final java.util.ArrayList<Entity> list = new java.util.ArrayList<>();
        
        final java.sql.Connection conn = getConnection();
       
        final String select = selectParam.getSelect();
        
        final Class<? extends Entity> entityClass = selectParam.getEntityClass();
        
        java.sql.PreparedStatement preparedStatement = null;
        
        java.sql.ResultSet resultSet = null;
        
        try
        {
            
            preparedStatement = conn.prepareStatement(select); 
            
            selectParam.fillPreparedStatement(preparedStatement);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {

                try
                {
                    final Entity entity = entityClass.getDeclaredConstructor().newInstance();
                    entity.setFromResult(resultSet);
                    list.add(entity);
                }
                catch (InstantiationException 
                     | IllegalAccessException 
                     | IllegalArgumentException
                     | InvocationTargetException 
                     | NoSuchMethodException 
                     | SecurityException exception)
                {
                    exception.printStackTrace();
                    break;
                }
            }
        }
        finally
        {
            resultSet.close(); 
            preparedStatement.close();
            logger.info("DBManager.select(DBSelect)... finally{}");
        }
        
        {
            final int size = list.size();
            logger.info("DBManager select(DBSelect): " + size + " rows...");
        }
        
        return list;
    }
    
    /**
     * getConnection() throws SQLException - liefert eine Connection 
     * @return
     * @throws SQLException
     */
    private java.sql.Connection getConnection() throws java.sql.SQLException 
    {
        if ((DBManager.connection == null) || DBManager.connection.isClosed())
        {
            DBManager.connection = java.sql.DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
        }
        return DBManager.connection;
    }

    /**
     * Eine innere Klasse gewaehlt, damit das Lesen der 
     * properties-Datei ueber getClass.getResourceAsStream() moeglich ist.
     * Damit kann die Datei auch in einer jar-Datei gefunden und gelesen 
     * werden! 
     */ 
    class PropertiesLoader
    {
        PropertiesLoader()
        {
            // *** Laden der Systemeinstellungen... ***        
            java.io.InputStream inputStream = getClass().getResourceAsStream(DBManager.propertiesFile);
            java.util.Properties properties = new java.util.Properties();
            try
            {
                properties.load(inputStream);
            }
            catch (Exception exception)
            {
                // Wenn kein Zugriff auf die Property-Datei, dann Applikation beenden!
                System.err.println("Can't read the properties file '" + DBManager.propertiesFile + "'! ");
                System.exit( 0 );
            }
            DBManager.jdbcDriver = properties.getProperty(DBManager.jdbcDriverKey);
            DBManager.jdbcUrl = properties.getProperty(DBManager.jdbcUrlKey);
            DBManager.jdbcUser = properties.getProperty(DBManager.jdbcUserKey);
            DBManager.jdbcPassword = properties.getProperty(DBManager.jdbcPasswordKey);
        }
    }

}

