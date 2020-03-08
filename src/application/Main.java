package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("ZARNA Bank");  // set title of stage
			FXMLLoader loader = new FXMLLoader(getClass().getResource("loginScene.fxml"));  // loads the first scene
			Scene scene = loader.load();
			primaryStage.setScene(scene); // sets the scene
			primaryStage.show(); // shows the stage
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
