package sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import model.Test;

public interface I_QueryTestSql {
	public List<Test> getTests(Connection con) throws SQLException;
	
}
