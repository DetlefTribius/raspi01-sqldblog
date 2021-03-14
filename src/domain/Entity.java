package domain;

import java.sql.SQLException;
/**
 * 
 * interface Entity beschreibt die notwendige Schnittstelle
 * fuer Datenobjekte zur Ablage in der Datenbank
 * 
 * @author Detlef Tribius
 *
 */
public interface Entity
{
    /**
     * 
     * @return String Insert-Statement
     */
    public String getInsertStatement();
    
    /**
     * fillPrepStmtForInsert() - befuellt ein vorbereitetes PreparedStatement,
     * welches dem Insert-Statement entspricht
     * @param preparedStatement
     * @throws SQLException
     */
    public void fillPrepStmtForInsert(java.sql.PreparedStatement preparedStatement) throws SQLException;
    
    /**
     * setFromResult() - fuellt ein Entity-Objekt mit den Daten des ResultSet
     * @param resultSet
     * @throws SQLException
     */
    public void setFromResult(java.sql.ResultSet resultSet) throws SQLException;
    
    /**
     * getColums() - liefert die Spalten-Bezeichnungen der Tabelle
     * @return String[]
     */
    public String[] getColums();
    
}
