package application.components;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class TransactionPane extends Pane {
	public TransactionPane(String ty, String nt, Double am, String ts) {
		Label type = new Label("Type: " + ty);
		type.getStyleClass().add("txt");
		type.getStyleClass().add("txt3");
		type.setLayoutY(10);
		type.setLayoutX(40);
		Label note = new Label("Note: " + nt);
		note.getStyleClass().add("txt");
		note.getStyleClass().add("txt3");
		note.setLayoutY(30);
		note.setLayoutX(40);
		Label amount = new Label("Amount: " + am);
		amount.getStyleClass().add("txt");
		amount.getStyleClass().add("txt3");
		amount.setLayoutY(50);
		amount.setLayoutX(40);
		Label timestamp = new Label("Timestamp: " + ts);
		timestamp.getStyleClass().add("txt");
		timestamp.getStyleClass().add("txt3");
		timestamp.setLayoutY(70);
		timestamp.setLayoutX(40);
		this.getChildren().add(type);
		this.getChildren().add(note);
		this.getChildren().add(amount);
		this.getChildren().add(timestamp);
	}
}

