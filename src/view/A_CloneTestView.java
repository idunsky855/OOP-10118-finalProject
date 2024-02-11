package view;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import listeners.I_UIListener;

public interface A_CloneTestView {
	void registerListener(I_UIListener listener);

	void buttons();

	void scene();

	void gridPane();

	void cloneTest(int i);

	void finish() throws FileNotFoundException, SQLException;

	void OpenAllTestsView();

}
