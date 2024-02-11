package listeners;

import model.A_Question;

public interface I_BLListener {

	void gotAllQuestions(String string);

	void QuestionAdded(A_Question question);

	void AnswerAdded();

	void changedQuestion();

	void ChangedAnswer();

	void deletedAnswer();

	void gotAllValidQuestions(String string);

	void printTestWithAnswers(String stringWithAnswer);

	void printTestWithoutAnswers(String string);

	void printAllTests(String string);

}
