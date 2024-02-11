package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javafx.stage.Stage;
import listeners.*;
import model.*;
import view.A_AddAnswerView;
import view.A_AddQuestionView;
import view.A_ChangeAnswerView;
import view.A_ChangeQuestionView;
import view.A_CloneTestView;
import view.A_CreateTestManuallyView;
import view.A_CreateTestRandomlyView;
import view.A_DeleteAnswerView;
import view.A_QuestionView;
import view.A_TestView;
import view.A_View;
import view.AddAnswerView;
import view.AddQuestionView;
import view.AllQuestionsView;
import view.AllValidQuestionsView;
import view.ChangeAnswerView;
import view.ChangeQuestionView;
import view.CloneTestView;
import view.CreateTestManuallyView;
import view.CreateTestRandomlyView;
import view.DeleteAnswerView;
import view.TestView;

public class Controller implements I_BLListener, I_UIListener {

	private Management model;
	private A_View primaryView;
	private A_QuestionView questView;
	private A_AddQuestionView addQuestView;
	private A_AddAnswerView addAnswerView;
	private A_ChangeQuestionView changeQuestionView;
	private A_ChangeAnswerView changeAnswerView;
	private A_DeleteAnswerView deleteAnswerView;
	private A_QuestionView allValidQuetionView;
	private A_CreateTestManuallyView createTestManuallyView;
	private A_CreateTestRandomlyView createTestRandomlyView;
	private A_TestView testWithAnswersView, testWithoutAnswersView, allTestsView;
	private A_CloneTestView cloneTestView;

	public Controller(Management model, A_View primaryView) {
		this.model = model;
		this.primaryView = primaryView;

		model.registerListener(this);
		primaryView.registerListener(this);
	}

	@Override
	public void OpenAllQuestionView(Stage window) {
		questView = new AllQuestionsView(window);
		questView.registerListener(this);
		model.printAllQuestions();

	}

	@Override
	public void gotAllQuestions(String string) {
		questView.insertAllQuestionsToUI(string);
	}

	@Override
	public void OpenAddQuestionView(Stage window) {
		addQuestView = new AddQuestionView(window);
		addQuestView.registerListener(this);

	}

	@Override
	public boolean addQuestionToModel(A_Question question) throws IndexOutOfBoundsException, SQLException {
		return model.addQuestion(question);

	}

	@Override
	public void QuestionAdded(A_Question question) {
		if(questView != null) {
			this.questView.closeWindow();
		}
		OpenAllQuestionView(new Stage());
	}

	@Override
	public void openAddAnswerView(Stage stage) {
		addAnswerView = new AddAnswerView(stage);
		addAnswerView.registerListener(this);
	}

	@Override
	public int getNumOfQuestions() {
		return model.getNumOfQuestions();
	}

	public boolean isOpenQuestion(int i) {
		return model.isOpenQuestion(i);
	}

	@Override
	public boolean addAnswerToModel(int index, String text, boolean b) throws Exception {
		return model.addAnswer(index, text, b);

	}

	@Override
	public void AnswerAdded() {
		questView.closeWindow();
		OpenAllQuestionView(new Stage());
	}

	@Override
	public boolean changeQuestion(int index, String str) throws IndexOutOfBoundsException, SQLException {
		return model.changeQuestion(index, str);

	}

	@Override
	public void changedQuestion() {
		questView.closeWindow();
		OpenAllQuestionView(new Stage());
	}

	@Override
	public void OpenChangeQuestionView(Stage window) {
		changeQuestionView = new ChangeQuestionView(window);
		changeQuestionView.registerListener(this);

	}

	@Override
	public void openChanegeAnswerView(Stage stage) {
		changeAnswerView = new ChangeAnswerView(stage);
		changeAnswerView.registerListener(this);
	}

	@Override
	public boolean changeAnswerToModel(int questIndex, int answerIndex, String answer, boolean isTrue) throws IndexOutOfBoundsException, SQLException {
		return model.changeAnswer(questIndex, answerIndex, answer, isTrue);
	}

	@Override
	public int getNumOfAnswersForQuestion(int questIndex) {
		return model.getNumOfAnswersForQuestion(questIndex);
	}

	@Override
	public void ChangedAnswer() {
		questView.closeWindow();
		OpenAllQuestionView(new Stage());

	}

	@Override
	public boolean deleteAnswer(int questIndex, int answerIndex) throws IndexOutOfBoundsException, SQLException {
		return model.removeAnswer(questIndex, answerIndex);
	}

	@Override
	public void openDeleteAnswerView(Stage stage) {
		deleteAnswerView = new DeleteAnswerView(stage);
		deleteAnswerView.registerListener(this);
	}

	@Override
	public void deletedAnswer() {
		questView.closeWindow();
		OpenAllQuestionView(new Stage());
	}

	@Override
	public void OpenAllValidQuestionView(Stage window) {
		allValidQuetionView = new AllValidQuestionsView(window);
		allValidQuetionView.registerListener(this);
		model.printAllValidQuestions();

	}

	@Override
	public void gotAllValidQuestions(String string) {
		allValidQuetionView.insertAllQuestionsToUI(string);
	}

	@Override
	public boolean isValidQuestion(int questIndex) {
		return model.validQuestion(questIndex);
	}

	@Override
	public void finishTest() throws FileNotFoundException, SQLException {
		model.finishTest();
	}

	@Override
	public void createTest(int size) {
		model.createTest(size);

	}

	@Override
	public int getNumOfValidQuestions() {
		return model.numOfValidQuestions();
	}

	@Override
	public void openCreateTestManuallyView(Stage stage) {
		createTestManuallyView = new CreateTestManuallyView(stage);
		createTestManuallyView.registerListener(this);
	}

	@Override
	public boolean addQuestionToTest(int questIndex) {
		return model.addQuestionToTestByIndex(questIndex);
	}

	@Override
	public void printTestWithAnswers(String stringWithAnswer) {
		testWithAnswersView = new TestView(new Stage());
		testWithAnswersView.insertAllQuestionsToUI(stringWithAnswer);

	}

	@Override
	public void printTestWithoutAnswers(String string) {
		testWithoutAnswersView = new TestView(new Stage());
		testWithoutAnswersView.insertAllQuestionsToUI(string);

	}

	@Override
	public boolean addQuestionToTestRandomly(int questIndex) throws Exception {
		return model.addQuestionToTestRandomly(questIndex);

	}

	@Override
	public void openCreateTestRandomlyView(Stage stage) {
		createTestRandomlyView = new CreateTestRandomlyView(stage);
		createTestRandomlyView.registerListener(this);
	}

	@Override
	public void OpenAllTestsView(Stage stage) {
		allTestsView = new TestView(stage);
		allTestsView.registerListener(this);
		model.printAllTests();

	}

	@Override
	public void printAllTests(String string) {
		allTestsView.insertAllQuestionsToUI(string);
	}

	@Override
	public int getNumOfTests() {
		return model.getAllTests().size();
	}

	@Override
	public void openCloneTestView(Stage stage) {
		cloneTestView = new CloneTestView(stage);
		cloneTestView.registerListener(this);
	}

	@Override
	public void cloneTest(int i) {
		model.cloneATest(i);

	}

	@Override
	public void saveAndExit() throws FileNotFoundException, IOException, SQLException {
		model.closeConnection();
		primaryView.closeWindow();

	}

	@Override
	public void finishClonedTest() {
		model.finishClonedTest();
		
	}

}
