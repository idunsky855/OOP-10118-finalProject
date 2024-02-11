package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Management;
import view.PrimaryView;
import view.A_View;

public class Main extends Application {
	private final String USER = "root";
	private final String PW = "!LGeUZkHwL79zbh*";
	private final String HOST = "jdbc:mysql://localhost:3306/test_generator";

	@Override
	public void start(Stage primaryStage) throws FileNotFoundException, ClassNotFoundException, IOException {
		
		try {
			Connection con = DriverManager.getConnection(HOST, USER, PW);
			System.out.println("---------Connection secured - DataBase: test_generator---------\n");
			Management model = new Management(con);
			A_View view = new PrimaryView(primaryStage);
			Controller control = new Controller(model, view);
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	
	}

	public static void main(String[] args) {
		launch(args);
	}
}
