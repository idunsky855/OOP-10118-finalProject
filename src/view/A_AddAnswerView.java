package view;

import listeners.I_UIListener;

public interface A_AddAnswerView {

	void registerListener(I_UIListener listener);

	void buttons();

	void scene();

	void gridPane();

	void OpenAllQuestionView();

	boolean isOpenQuestion(int i);

	boolean addAnswerToModel(int index, String text, boolean b) throws Exception;

	int getNumOfQuestions();

	
}
