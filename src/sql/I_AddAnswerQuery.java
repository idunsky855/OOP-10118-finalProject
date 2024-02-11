package sql;

import java.sql.Connection;
import java.sql.SQLException;

import model.Answer;
import model.A_Question;

public interface I_AddAnswerQuery {
	public boolean insertAnswer(Connection con, Answer answer, A_Question quest) throws SQLException;
	public boolean updateAnswerString(Connection con, Answer answer) throws SQLException;
	public boolean updateAnswerIsTrue(Connection con, Answer answer,A_Question  q) throws SQLException;
	public boolean deleteAnswer(Connection con, Answer answer) throws SQLException;
	
}
