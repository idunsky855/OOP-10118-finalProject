package sql;

import java.sql.*;

import model.Answer;
import model.MultiChoiceQuestion;
import model.OpenQuestion;
import model.A_Question;

public class AddQuestionQuery implements I_AddQuestionQuery {
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_QUESTION = "INSERT INTO question_table(qid,questStr,qType)" + "VALUES (?,?,?)";
	private static final String INSERT_OPEN_QUESTION = "INSERT INTO openQuestion_table(qid)" + " VALUES (?)";
	private static final String INSERT_MULTICHOICE_QUESTION = "INSERT INTO multichoice_table(qid,numOfAnswers,numOfCorrectAnswers)"
			+ " VALUES (?,?,?)";
	private static final String UPDATE_QUESTION_STRING = "UPDATE question_table SET questStr= ? WHERE qid = ?";
	private static final String UPDATE_NUM_OF_ANSWERS = "UPDATE multiChoice_table SET numOfAnswers= ? WHERE qid = ?";
	private static final String UPDATE_NUM_OF_CORRECT_ANSWERS = "UPDATE multiChoice_table SET numOfCorrectAnswers= ? WHERE qid = ?";
	
	public boolean insertQuestion(Connection con, A_Question q) throws SQLException {
		try (PreparedStatement stmt = con.prepareStatement(INSERT_QUESTION)) {
			stmt.setInt(1, q.getSerialNum());
			stmt.setString(2, q.getQuest());
			stmt.setString(3, (q.getType()).name());
			stmt.executeUpdate();
			System.out.println("--------------------------------------------------------");
			System.out.println("Inserted into question_table:\n\tQuestion = [ qid - " + q.getSerialNum() + " , questStr - "+ q.getQuest() + " , qType - "+(q.getType()).name()+" ]");
		}
		I_AddAnswerQuery jdbc = new AddAnswerQuery();
		if (q instanceof MultiChoiceQuestion) {
			MultiChoiceQuestion mcq = (MultiChoiceQuestion) q;
			try (PreparedStatement stmt = con.prepareStatement(INSERT_MULTICHOICE_QUESTION)) {
				stmt.setInt(1, mcq.getSerialNum());
				stmt.setInt(2, mcq.getNumOfAnswers());
				stmt.setInt(3, mcq.getCorrectAnswers());
				stmt.executeUpdate();
				for(int i = 0 ; i < mcq.getAnswers().size() ; i++ ) {
					jdbc.insertAnswer(con, mcq.getAnswers().get(i), mcq);
				}
				System.out.println("Inserted into multiChoice_table:\n\tQuestion = [ qid - " + q.getSerialNum() + " , numOfAnswers - "+ mcq.getNumOfAnswers() + " , numOfCorrectAnswers - " + mcq.getCorrectAnswers()+" ]");
			}
		} else {
			OpenQuestion oq = (OpenQuestion) q;
			try (PreparedStatement stmt = con.prepareStatement(INSERT_OPEN_QUESTION)) {
				stmt.setInt(1, oq.getSerialNum());
				stmt.executeUpdate();
				jdbc.insertAnswer(con, oq.getAnswer(), oq);
				System.out.println("Inserted into openQuestion_table:\n\tQuestion = [ qid - " + q.getSerialNum() + " ]");
				System.out.println("--------------------------------------------------------");
			}
		}
		return true;
	}
	
	public boolean updateQuestionString(Connection con, A_Question q) throws SQLException {
		try (PreparedStatement stmt = con.prepareStatement(UPDATE_QUESTION_STRING)) {
			stmt.setString(1, q.getQuest());
			stmt.setInt(2,q.getSerialNum());
			stmt.executeUpdate();
			System.out.println("--------------------------------------------------------");
			System.out.println("UPDATED question_table:\n\tQuestion = [ qid - " +q.getSerialNum() + " , question - "+q.getQuest() + " ]");
		}
		System.out.println("--------------------------------------------------------");
		return true;
	}
	
	public boolean updateQuestionNumOfAnswers(Connection con, MultiChoiceQuestion q) throws SQLException {
		try (PreparedStatement stmt = con.prepareStatement(UPDATE_NUM_OF_ANSWERS)) {
			stmt.setInt(1, q.getNumOfAnswers());
			stmt.setInt(2,q.getSerialNum());
			stmt.executeUpdate();
			System.out.println("--------------------------------------------------------");
			System.out.println("UPDATED multichoice_table:\n\tQuestion = [ qid - " +q.getSerialNum() + " , question - "+q.getQuest() + " , numOfAnswers - "+ q.getNumOfAnswers()+ "]");
		}
		System.out.println("--------------------------------------------------------");
		return true;
	}
	
	public boolean updateQuestionNumOfCorrectAnswers(Connection con, MultiChoiceQuestion q) throws SQLException {
		try (PreparedStatement stmt = con.prepareStatement(UPDATE_NUM_OF_CORRECT_ANSWERS)) {
			stmt.setInt(1, q.getCorrectAnswers());
			stmt.setInt(2,q.getSerialNum());
			stmt.executeUpdate();
			System.out.println("--------------------------------------------------------");
			System.out.println("UPDATED multichoice_table:\n\tQuestion = [ qid - " +q.getSerialNum() + " , question - "+q.getQuest() + " , numOfCorrectAnswers - "+ q.getCorrectAnswers()+ "]");
		}
		System.out.println("--------------------------------------------------------");
		return true;
	}
	

}
