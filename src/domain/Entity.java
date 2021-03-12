package domain;

import java.sql.SQLException;

public interface Entity
{
    public String getInsertStatement();
    
    public void fillPrepStmtForInsert(java.sql.PreparedStatement preparedStatement) throws SQLException;
    
    public void setFromResult(java.sql.ResultSet resultSet) throws SQLException;
    
    public String[] getColums();
    
}
