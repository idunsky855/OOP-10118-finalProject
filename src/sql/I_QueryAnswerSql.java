package sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import model.Answer;
import model.E_QuestionType;

public interface I_QueryAnswerSql {
	public List<Answer> getAnswersFromDBtoQuestion(Connection con, int qid, E_QuestionType type) throws SQLException;
	public List<Integer> getDefaultAnswers(Connection con) throws SQLException;
	
}
