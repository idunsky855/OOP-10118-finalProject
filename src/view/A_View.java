package view;

import listeners.*;
import model.A_Question;

public interface A_View {
	void registerListener(I_UIListener listener);

	void scene();

	void gridPane();

	void buttons();

	void openAddQuestionView();

	void openAllQuestionView();

	void openAddAnswerView();

	void openChangeQuestionView();

	void openChangeAnswerView();

	void closeWindow();

}
