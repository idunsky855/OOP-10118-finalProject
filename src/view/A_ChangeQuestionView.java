package view;

import java.sql.SQLException;

import listeners.I_UIListener;

public interface A_ChangeQuestionView {

	void registerListener(I_UIListener listener);

	void scene();

	void gridPane();

	void buttons();

	void OpenAllQuestionView();

	int getNumOfQuestions();

	boolean changeQuestion(int index, String str) throws IndexOutOfBoundsException, SQLException;
}
