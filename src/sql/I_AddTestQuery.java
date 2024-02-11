package sql;

import java.sql.Connection;
import java.sql.SQLException;

import model.Test;

public interface I_AddTestQuery {
	
	public boolean inserTest(Connection con, Test test) throws SQLException;
	
}
