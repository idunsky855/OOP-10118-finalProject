package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import model.Answer;
import model.MultiChoiceQuestion;
import model.A_Question;

public class AddAnswerQuery implements I_AddAnswerQuery {
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_ANSWER = "INSERT INTO answer_table(aid,answerStr)" + "VALUES (?,?)";
	private static final String INSERT_OPEN_QUESTION_ANSWER = "INSERT INTO openQuestion_answer_table(qid,aid) VALUES (?,?)";
	private static final String INSERT_MULTICHOICE_QUESTION_ANSWER = "INSERT INTO multichoice_answer_table(qid,aid,isTrue) VALUES (?,?,?)";
	private static final String UPDATE_ANSWER_STRING = "UPDATE answer_table SET answerStr= ? WHERE aid=? ";
	private static final String UPDATE_ANSWER_ISTRUE = "UPDATE multichoice_answer_table SET isTrue= ? WHERE aid=? AND qid=?";
	private static final String DELETE_ANSWER = "DELETE FROM answer_table WHERE aid=?";
	
	public boolean insertAnswer(Connection con, Answer answer, A_Question quest) throws SQLException {
		I_QueryAnswerSql queryAnswer = new QueryAnswerSql();
		List<Integer> rs = queryAnswer.getDefaultAnswers(con);
		if (!((answer.getId() == 10 || answer.getId() == 11) && rs.size() == 2)) {

			try (PreparedStatement stmt = con.prepareStatement(INSERT_ANSWER)) {
				stmt.setInt(1, answer.getId());
				stmt.setString(2, answer.getAnswer());
				stmt.executeUpdate();
				System.out.println("--------------------------------------------------------");
				System.out.println("Inserted into answer_table:\n\tAnswer = [ aid - " + answer.getId() + " , answer - "
						+ answer.getAnswer() + " ]");
			}
		}
		if (quest instanceof MultiChoiceQuestion) {
			try (PreparedStatement stmt = con.prepareStatement(INSERT_MULTICHOICE_QUESTION_ANSWER)) {
				stmt.setInt(1, quest.getSerialNum());
				stmt.setInt(2, answer.getId());
				stmt.setBoolean(3, answer.getIsTrue());
				stmt.executeUpdate();
				System.out.println("Inserted into mulitchoice_answer_table:\n\t\tQuestion_Answer_Relation [ qid - "
						+ quest.getSerialNum() + " , aid - " + answer.getId() + " , isTrue - " + answer.getIsTrue()
						+ " ]");
			}
		} else {
			try (PreparedStatement stmt = con.prepareStatement(INSERT_OPEN_QUESTION_ANSWER)) {
				stmt.setInt(1, quest.getSerialNum());
				stmt.setInt(2, answer.getId());
				stmt.executeUpdate();
				System.out.println("Inserted into openQuestion_answer_table:\n\t\tQuestion_Answer_Relation  [ qid - "
						+ quest.getSerialNum() + " , aid - " + answer.getId() + " , isTrue - " + answer.getIsTrue()
						+ " ]");
			}
		}
		System.out.println("--------------------------------------------------------");
		return true;
	}

	public boolean updateAnswerString(Connection con, Answer answer) throws SQLException {
		try (PreparedStatement stmt = con.prepareStatement(UPDATE_ANSWER_STRING)) {
			stmt.setString(1, answer.getAnswer());
			stmt.setInt(2, answer.getId());
			stmt.executeUpdate();
			System.out.println("--------------------------------------------------------");
			System.out.println("UPDATED answer_table:\n\tAnswer = [ aid - " + answer.getId() + " , answer - "
					+ answer.getAnswer() + " ]");
		}
		System.out.println("--------------------------------------------------------");
		return true;
	}

	public boolean updateAnswerIsTrue(Connection con, Answer answer,A_Question  q) throws SQLException {
		try (PreparedStatement stmt = con.prepareStatement(UPDATE_ANSWER_ISTRUE)) {
			stmt.setBoolean(1, answer.getIsTrue());
			stmt.setInt(2, answer.getId());
			stmt.setInt(3, q.getSerialNum());
			stmt.executeUpdate();
			System.out.println("--------------------------------------------------------");
			System.out.println("UPDATED multichoice_answer_table:\n\tAnswer = [ aid - " + answer.getId()
					+ " , answer - " + answer.getAnswer() + " , isTrue-" + answer.getIsTrue() + " ]");
		}
		System.out.println("--------------------------------------------------------");
		return true;
	}

	public boolean deleteAnswer(Connection con, Answer answer) throws SQLException {
		try (PreparedStatement stmt = con.prepareStatement(DELETE_ANSWER)) {
			stmt.setInt(1, answer.getId());
			stmt.executeUpdate();
			System.out.println("--------------------------------------------------------");
			System.out.println("DELETED Answer:\n\tAnswer = [ aid - " + answer.getId()
					+ " , answer - " + answer.getAnswer() + " ]");
		}
		System.out.println("--------------------------------------------------------");
		return true;
	}
}
