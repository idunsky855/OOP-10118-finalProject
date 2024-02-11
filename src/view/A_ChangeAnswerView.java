package view;

import listeners.I_UIListener;

public interface A_ChangeAnswerView {
	void registerListener(I_UIListener listener);

	void buttons();

	void scene();

	void gridPane();

	void OpenAllQuestionView();

	boolean isOpenQuestion(int i);

	boolean changeAnswerToModel(int questIndex, int answerIndex, String answer, boolean isTrue) throws Exception;

	int getNumOfQuestions();

	int getNumOfAnswersForQuestion(int questIndex);

}
