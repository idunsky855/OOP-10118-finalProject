package sql;

import java.sql.Connection;
import java.sql.SQLException;

import model.MultiChoiceQuestion;
import model.A_Question;

public interface I_AddQuestionQuery {

	public boolean insertQuestion(Connection con, A_Question q) throws SQLException;
	public boolean updateQuestionString(Connection con, A_Question q) throws SQLException;
	public boolean updateQuestionNumOfAnswers(Connection con, MultiChoiceQuestion q) throws SQLException;
	public boolean updateQuestionNumOfCorrectAnswers(Connection con, MultiChoiceQuestion q) throws SQLException;
	
}
