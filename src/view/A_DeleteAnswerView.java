package view;

import listeners.I_UIListener;

public interface A_DeleteAnswerView {
	void registerListener(I_UIListener listener);

	void buttons();

	void scene();

	void gridPane();

	void OpenAllQuestionView();

	boolean isOpenQuestion(int i);

	int getNumOfQuestions();

	boolean deleteAnswerFromModel(int questIndex, int answerIndex) throws Exception;

	int getNumOfAnswersForQuestion(int questIndex);
}
