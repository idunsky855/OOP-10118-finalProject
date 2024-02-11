package view;

import listeners.I_UIListener;

public interface A_QuestionView {

	void registerListener(I_UIListener listener);

	void scene();

	void textArea();

	void insertAllQuestionsToUI(String string);

	void closeWindow();

}
