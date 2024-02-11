package view;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import listeners.I_UIListener;

public interface A_CreateTestManuallyView {

	void registerListener(I_UIListener listener);

	void buttons();

	void scene();

	void gridPane();

	void OpenAllValidQuestionView();

	boolean isQuestionValid(int questIndex);

	void createTest(int size);

	void finish() throws FileNotFoundException, SQLException;

	int getNumOfValidQuestions();

	int getNumOfQuestions();

	 boolean addQuestionToTest(int questIndex);

}
