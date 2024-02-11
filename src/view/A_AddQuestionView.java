package view;

import java.sql.SQLException;

import listeners.I_UIListener;
import model.A_Question;

public interface A_AddQuestionView {

	void registerListener(I_UIListener listener);

	void scene();

	void gridPane();

	void buttons() throws SQLException;

	boolean addQuestionToController(A_Question quest) throws IndexOutOfBoundsException, SQLException;

	void openAddAnswerView();

	void OpenAllQuestionView();
}
