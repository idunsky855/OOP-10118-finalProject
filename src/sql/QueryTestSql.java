package sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.A_Question;
import model.Test;

public class QueryTestSql implements I_QueryTestSql{
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static final String TEST_QUERY = "SELECT * FROM test_table";


	// gets all the tests from DB
	public List<Test> getTests(Connection con) throws SQLException {
		List<Test> rlist = new ArrayList<>();
		try (Statement stmt = con.createStatement()) {
			I_QueryQuestionSql questQuery = new QueryQuestionSql();
			try (ResultSet rs = stmt.executeQuery(TEST_QUERY)) {
				Test t = null;
				while (rs.next()) {
					// gets the questions for a test from DB
					t = new Test();
					t.setId(rs.getInt("tid"));
					t.setNumOfQuestions(rs.getInt("numOfQuestions"));
					t.setWantedNumOfQuestion(rs.getInt("desiredNumOfQuestions"));
					List<A_Question> questions = questQuery.getTestQuestions(con, t.getId());
					t.setQuestions(questions);
					rlist.add(t);
				}
				Iterator<Test> it = rlist.iterator();
				int maxId = 10;
				int currentId;
				while (it.hasNext()) {
					currentId = it.next().getId();
					if (currentId >= maxId)
						maxId = currentId;
				}
				if (t != null) {
					t.setIdGen(maxId);
				}
			}
		}
		return rlist;
	}
}
