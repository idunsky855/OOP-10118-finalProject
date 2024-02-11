package view;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import listeners.I_UIListener;

public interface A_CreateTestRandomlyView {

	void registerListener(I_UIListener listener);

	void buttons();

	void scene();

	void gridPane();

	boolean isQuestionValid(int questIndex);

	void createTest(int size);

	void finish() throws FileNotFoundException, SQLException;

	int getNumOfValidQuestions();

	int getNumOfQuestions();

	int randomQuestIndex();

	boolean addQuestionToTest(int questIndex) throws Exception;

	void OpenAllValidQuestionView();

}
