/**
 * 
 */
package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import domain.Entity;

/**
 * @author Detlef Tribius
 *
 */
public abstract class DBSelect
{
    /**
     * Class entityClass - Class der Entity
     */
    private final Class<? extends Entity> entityClass;
    
    /**
     * String select - SQL-Select-Anweisung
     */
    private final String select;
    
    /**
     * 
     * @param entityClass
     * @param select
     */
    public DBSelect(Class<? extends Entity> entityClass, String select)
    {
        this.entityClass = entityClass;
        this.select = (select != null)? select.trim() : "";
    }

    /**
     * 
     * @return
     */
    public final Class<? extends Entity> getEntityClass()
    {
        return this.entityClass;
    }

    /**
     * 
     * @return
     */
    public final String getSelect()
    {
        return this.select;
    }

    /**
     * 
     * @param preparedStatement
     * @throws SQLException 
     */
    protected abstract void fillPreparedStatement(PreparedStatement preparedStatement) throws SQLException;
    
    
}
