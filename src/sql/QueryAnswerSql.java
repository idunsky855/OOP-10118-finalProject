package sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.Answer;
import model.A_Question;
import model.E_QuestionType;

public class QueryAnswerSql implements I_QueryAnswerSql{
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static final String QUERY_ANSWER_MULTICHOICE = "SELECT * FROM answer_table"
			+ " JOIN multichoice_answer_table ON multichoice_answer_table.aid = answer_table.aid" + " WHERE qid = ";
	private static final String QUERY_ANSWER_OPENQUESTION = "SELECT * FROM answer_table"
			+ " JOIN openquestion_answer_table" + " ON answer_table.aid = openquestion_answer_table.aid"
			+ " WHERE qid = ";
	private static final String QUERY_DEFAULT_ANSWERS = "SELECT aid FROM answer_table WHERE aid=10 OR aid=11";

	public List<Answer> getAnswersFromDBtoQuestion(Connection con, int qid, E_QuestionType type) throws SQLException {
		List<Answer> rlist = new ArrayList<>();
		try (Statement stmt = con.createStatement()) {
			String s;
			if (type.equals(E_QuestionType.eOPEN_QUESTION)) {
				s = QUERY_ANSWER_OPENQUESTION;
			} else {
				s = QUERY_ANSWER_MULTICHOICE;
			}
			s = s + qid;
			try (ResultSet rs = stmt.executeQuery(s)) {
				Answer a = null;
				while (rs.next()) {
					a = new Answer();
					a.setAnswer(rs.getString("answerStr"));
					a.setId(rs.getInt("aid"));
					a.setIsTrue(rs.getBoolean("isTrue"));
					rlist.add(a);
				}
				Iterator<Answer> it = rlist.iterator();
				int maxId = 10;
				while (it.hasNext()) {
					int currentId = it.next().getId();
					if (currentId >= maxId)
						maxId = currentId;
				}
				if (a != null) {
					a.setIdGen(maxId);
				}

			}
		}
		return rlist;
	}
	
	public List<Integer> getDefaultAnswers(Connection con) throws SQLException{
		List<Integer> list = new ArrayList<>();
		try(Statement stmt = con.createStatement()){
			try(ResultSet rs = stmt.executeQuery(QUERY_DEFAULT_ANSWERS)){
				while(rs.next())
				{
					list.add(rs.getInt("aid"));
				}
			}
		}
		return list;
	}
}
