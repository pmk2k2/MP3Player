package presentation.uicomponents;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ControlView extends HBox {
	
	public Button skipBackButton;
	public Button playButton;
	public Button skipButton;
	
	public ControlView() {
		skipBackButton = new Button("skip back");
		skipBackButton.getStyleClass().add("icon-button");

		playButton = new Button();
		playButton.getStyleClass().add("icon-button");
		playButton.setId("play-button");

		skipButton = new Button("skip");
		skipButton.getStyleClass().add("icon-button");

		this.setId("control-bar");
		this.getChildren().addAll(skipBackButton, playButton, skipButton);
	
	}

}
