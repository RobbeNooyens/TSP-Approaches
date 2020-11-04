package me.robnoo02.travelingsalesmanproblem;

import javafx.application.Application;
import javafx.stage.Stage;
import me.robnoo02.travelingsalesmanproblem.scenes.Sheet;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setResizable(false);
		Sheet sheet = new Sheet();
		stage.setScene(sheet.getScene());
		stage.show();
	}

}
