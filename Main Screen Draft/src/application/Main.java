package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("ZARNA"); //Sets Window Title
		
		//Main Root and Scene Setup
		BorderPane mainRoot = new BorderPane();
		mainRoot.setPrefSize(600, 600);
		Scene mainScreen = new Scene(mainRoot);

		//Welcome Message stores as an HBOX, added to the top section of the BorderPane Root
		HBox welcomeBox = new HBox();
		
		Label welcomeMessage = new Label("Hello, Welcome to Zarna Banking");
		welcomeMessage.setFont(Font.font("Arial Black", FontWeight.BLACK, 30));
		
		welcomeBox.getChildren().add(welcomeMessage);
		welcomeBox.setAlignment(Pos.CENTER);
		mainRoot.setTop(welcomeBox);

		
		//Initializing Center HBoxes
		HBox usernameInfo = new HBox(), passwordInfo = new HBox(), buttonPanel = new HBox();
		usernameInfo.setAlignment(Pos.CENTER);
		passwordInfo.setAlignment(Pos.CENTER);
		buttonPanel.setAlignment(Pos.CENTER);

		//VBox used to align all of the HBoxes of the center pane
		VBox centerArea = new VBox();
		centerArea.setAlignment(Pos.CENTER);

		// Username Info HBox
		Label userLabel = new Label("Username:		");
		TextField usernameInput = new TextField("Enter Your Username Here");
		usernameInput.setPrefSize(200, 10);
		usernameInfo.getChildren().addAll(userLabel, usernameInput);

		// Password Info HBox
		Label passLabel = new Label("Password:			");
		TextField passInput = new TextField("Enter Your Password Here");
		passInput.setPrefSize(200, 10);
		passwordInfo.getChildren().addAll(passLabel, passInput);

		// Bottom Buttons HBox
		Button login = new Button("LOGIN"), signUp = new Button("SIGN UP");
		buttonPanel.setSpacing(15);
		buttonPanel.getChildren().addAll(login, signUp);

		//Configures Center Area VBox with the HBoxes Above
		centerArea.getChildren().addAll(usernameInfo, passwordInfo, buttonPanel);
		mainRoot.setCenter(centerArea);

		//Displays mainScreen Scene
		primaryStage.setScene(mainScreen);
		primaryStage.show();

	}

}
