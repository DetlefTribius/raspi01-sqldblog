/**
 * 
 */
package domain;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DBSelect;

/**
 * @author Detlef Tribius
 *
 */
public class DataSelect extends DBSelect
{
    /**
     * 
     */
    private final DataSelectType dataSelectType;
    
    /**
     * java.sql.Timestamp sys_tsp - Parameter fuer das SELECT_STATEMENT, wird im Konstruktor gesetzt 
     */
    private final java.sql.Timestamp sys_tsp;
    /**
     * java.sql.Timestamp sys_tsp_from, in Verbindung mit sys_tsp_to
     */
    private final java.sql.Timestamp sys_tsp_from;
    /**
     * java.sql.Timestamp sys_tsp_to, in Verbindung mit sys_tsp_from 
     */
    private final java.sql.Timestamp sys_tsp_to;
    /**
     * 
     */
    private final Integer id;
    
    /**
     * 
     * @param sys_tsp
     */
    public DataSelect(java.sql.Timestamp sys_tsp)
    {
        super(domain.Data.class, DataSelectType.SELECT_WHERE_SYS_TSP.getSelect());
        this.sys_tsp = sys_tsp;
        this.id = null;
        this.sys_tsp_from = null;
        this.sys_tsp_to = null;
        this.dataSelectType = DataSelectType.SELECT_WHERE_SYS_TSP;
    }

    /**
     * 
     * @param id
     */
    public DataSelect(Integer id)
    {
        super(domain.Data.class, DataSelectType.SELECT_WHERE_ID.getSelect());
        this.id = (id != null)? id : Integer.valueOf(0);
        this.sys_tsp = null;
        this.sys_tsp_from = null;
        this.sys_tsp_to = null;
        this.dataSelectType = DataSelectType.SELECT_WHERE_ID;
    }
    
    public DataSelect(java.sql.Timestamp sys_tsp_from, java.sql.Timestamp sys_tsp_to)
    {
        super(domain.Data.class, DataSelectType.SELECT_WHERE_FROM_TO.getSelect());
        this.sys_tsp_from = sys_tsp_from;
        this.sys_tsp_to = sys_tsp_to;
        this.sys_tsp = null;
        this.id = null;
        this.dataSelectType = DataSelectType.SELECT_WHERE_FROM_TO;
    }
    
    /**
     * @throws SQLException 
     * 
     */
    @Override
    protected void fillPreparedStatement(PreparedStatement preparedStatement) throws SQLException
    {
        if (DataSelectType.SELECT_WHERE_SYS_TSP == this.dataSelectType)
        {
            preparedStatement.setTimestamp(1, this.sys_tsp);
            return;
        }
        if (DataSelectType.SELECT_WHERE_ID == this.dataSelectType)
        {
            preparedStatement.setInt(1, this.id);
            return;
        }
        if (DataSelectType.SELECT_WHERE_FROM_TO == this.dataSelectType)
        {
            preparedStatement.setTimestamp(1, this.sys_tsp_from);
            preparedStatement.setTimestamp(2, this.sys_tsp_to);
            return;
        }
    }
    
    /**
     * DataSelectType - Type des SQL-Select... 
     *   
     * @author Detlef Tribius
     *
     */
    enum DataSelectType
    {
        /**
         * "SELECT ID, SYS_TSP, DATA_A, DATA_B FROM DATA_TBL WHERE SYS_TSP = ?"
         */
        SELECT_WHERE_SYS_TSP("SELECT ID, SYS_TSP, DATA_A, DATA_B, DATA_C, DATA_D FROM DATA_TBL WHERE SYS_TSP = ?"),
        /**
         * "SELECT ID, SYS_TSP, DATA_A, DATA_B FROM DATA_TBL WHERE ID = ?"
         */
        SELECT_WHERE_ID("SELECT ID, SYS_TSP, DATA_A, DATA_B, DATA_C, DATA_D FROM DATA_TBL WHERE ID = ?"),
        /**
         * "SELECT ID, SYS_TSP, DATA_A, DATA_B FROM DATA_TBL WHERE SYS_TSP >= ? AND SYS_TSP <= ?"
         */
        SELECT_WHERE_FROM_TO("SELECT ID, SYS_TSP, DATA_A, DATA_B, DATA_C, DATA_D FROM DATA_TBL WHERE SYS_TSP >= ? AND SYS_TSP <= ?");
        /**
         * String sql-select mit where-Klausel...
         */
        private final String select;
        /**
         * DataSelectType(String select) - Konstruktor...
         * @param select
         */
        private DataSelectType(String select)
        {
            this.select = select;
        }
        /**
         * getSelect()
         * @return Select-Anweisung mit where Bedingung...
         */
        public String getSelect()
        {
            return this.select;
        }
    }
    
}
