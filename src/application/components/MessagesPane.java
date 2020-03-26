package application.components;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class MessagesPane extends Pane{
	public MessagesPane(String fr, String sub, String con, String ts) {
		Label sender = new Label("From: " + fr);
		sender.getStyleClass().add("txt");
		sender.getStyleClass().add("txt3");
		sender.setLayoutY(10);
		sender.setLayoutX(40);
		Label subject = new Label("Subject: " + sub);
		subject.getStyleClass().add("txt");
		subject.getStyleClass().add("txt3");
		subject.setLayoutY(30);
		subject.setLayoutX(40);
		Label content = new Label("Content: " + con);
		content.getStyleClass().add("txt");
		content.getStyleClass().add("txt3");
		content.setLayoutY(50);
		content.setLayoutX(40);
		Label timestamp = new Label("Timestamp: " + ts);
		timestamp.getStyleClass().add("txt");
		timestamp.getStyleClass().add("txt3");
		timestamp.setLayoutY(70);
		timestamp.setLayoutX(40);
		this.getChildren().add(sender);
		this.getChildren().add(subject);
		this.getChildren().add(content);
		this.getChildren().add(timestamp);
	}
}
