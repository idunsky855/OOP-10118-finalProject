package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

import model.A_Question;
import model.Test;

public class AddTestQuery implements I_AddTestQuery {
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_TEST = "INSERT INTO test_table(tid,numOfQuestions,desiredNumOfQuestions) VALUES (?,?,?)";
	private static final String INSERT_TEST_QUESTION = "INSERT INTO test_question_table(tid,qid) VALUES (?,?)";

	public boolean inserTest(Connection con, Test test) throws SQLException {
		try (PreparedStatement stmt = con.prepareStatement(INSERT_TEST)) {
			stmt.setInt(1, test.getId());
			stmt.setInt(2, test.getNumOfQuestions());
			stmt.setInt(3, test.getWantedNumOfQuestions());
			stmt.executeUpdate();
			System.out.println("--------------------------------------------------------");
			System.out.println("Inserted into test_table:\n\tTest = [ tid - " + test.getId() + " , numOfQuestions - "+ test.getNumOfQuestions() + " , desiredNumOfQuestions - "+ test.getWantedNumOfQuestions()+" ]");
		}
		try (PreparedStatement stmt = con.prepareStatement(INSERT_TEST_QUESTION)) {
			Iterator<A_Question> it = test.getTestQuestions().iterator();
			while (it.hasNext()) {
			A_Question q = it.next();
				stmt.setInt(1, test.getId());
				stmt.setInt(2, q.getSerialNum());
				stmt.executeUpdate();
				System.out.println("Inserted into test_question_table:\n\tTest_Question_Relation = [ tid - " + test.getId() + " , qid - "+ q.getSerialNum()+" ]");
			}
		}
		System.out.println("--------------------------------------------------------");
		return true;
	}
}
