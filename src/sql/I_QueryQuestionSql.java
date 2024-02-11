package sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import model.A_Question;
import model.E_QuestionType;

public interface I_QueryQuestionSql {
	public List<A_Question> getQuestions(Connection con, E_QuestionType type) throws SQLException;
	public List<A_Question> getTestQuestions(Connection con, int tid) throws SQLException;
	
}
