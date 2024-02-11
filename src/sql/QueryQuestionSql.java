package sql;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.*;

public class QueryQuestionSql implements I_QueryQuestionSql {
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static final String QUESTION_QUERY = "SELECT * FROM question_table" + " LEFT OUTER JOIN multiChoice_table "
			+ "ON question_table.qid = multiChoice_table.qid ";
	private static final String QUESTION_QUERY_BY_TID = "SELECT * FROM (question_table LEFT OUTER JOIN multiChoice_table ON question_table.qid = multiChoice_table.qid) "
			+ " JOIN test_question_table ON question_table.qid = test_question_table.qid " + "WHERE tid = ";

	// gets all the question of a desired type from DB
	public List<A_Question> getQuestions(Connection con, E_QuestionType type) throws SQLException {
		List<A_Question> rlist = new ArrayList<>();
		try (Statement stmt = con.createStatement()) {
			try (ResultSet rs = stmt.executeQuery(QUESTION_QUERY)) {
				A_Question q = null;
				while (rs.next()) {
					if (type.equals(E_QuestionType.eMULTI_CHOICE)) {
						q = new MultiChoiceQuestion();
						((MultiChoiceQuestion) q).setNumOfAnswers(rs.getInt("numOfAnswers"));
						((MultiChoiceQuestion) q).setCorrectAnswers(rs.getInt("numOfCorrectAnswers"));
					} else {
						q = new OpenQuestion();
					}
					if (q != null) {
						if (type.equals(E_QuestionType.valueOf(rs.getString("qType")))) {
							// gets the answers for a specific question from DB
							I_QueryAnswerSql queryAnswer = new QueryAnswerSql();
							List<Answer> answers = queryAnswer.getAnswersFromDBtoQuestion(con,
									rs.getInt("question_table.qid"), type);
							if (answers.size() > 0) {
								if (q instanceof OpenQuestion) {
									((OpenQuestion) q).setAnswer(answers.get(0).getAnswer());
								} else {
									((MultiChoiceQuestion) q).setAnswers(answers);
								}
							}
							q.setSerialNum(rs.getInt("question_table.qid"));
							q.setQuest(rs.getString("questStr"));
							rlist.add(q);
						}
						Iterator<A_Question> it = rlist.iterator();
						int maxId = 1000;
						while (it.hasNext()) {
							int currentId = it.next().getSerialNum();
							if (currentId >= maxId)
								maxId = currentId;
						}
						q.setIdGen(maxId);
					}
				}
			}
		}
		return rlist;
	}

	// gets all questions for test by tid
	public List<A_Question> getTestQuestions(Connection con, int tid) throws SQLException {
		List<A_Question> rlist = new ArrayList<>();
		try (Statement stmt = con.createStatement()) {
			try (ResultSet rs = stmt.executeQuery(QUESTION_QUERY_BY_TID + tid)) {
				A_Question q = null;
				while (rs.next()) {
					if (E_QuestionType.eMULTI_CHOICE.equals(E_QuestionType.valueOf(rs.getString("qType")))) {
						q = new MultiChoiceQuestion();
						((MultiChoiceQuestion) q).setNumOfAnswers(rs.getInt("numOfAnswers"));
						((MultiChoiceQuestion) q).setCorrectAnswers(rs.getInt("numOfCorrectAnswers"));
					} else {
						q = new OpenQuestion();
					}
					if (q != null) {
						// gets the answers for a specific question from DB
						I_QueryAnswerSql queryAnswer = new QueryAnswerSql();
						List<Answer> answers = queryAnswer.getAnswersFromDBtoQuestion(con, rs.getInt("qid"),
								q.getType());
						if (answers.size() > 0) {
							if (q instanceof OpenQuestion) {
								((OpenQuestion) q).setAnswer(answers.get(0).getAnswer());
							} else {
								((MultiChoiceQuestion) q).setAnswers(answers);
							}
						}
						q.setSerialNum(rs.getInt("qid"));
						q.setQuest(rs.getString("questStr"));
						rlist.add(q);
					}
				}
			}
		}
		return rlist;
	}

}
