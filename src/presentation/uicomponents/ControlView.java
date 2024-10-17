package presentation.uicomponents;

import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

public class ControlView extends HBox {
	
	public Button skipBackButton;
	public ToggleButton playPauseButton;
	public Button skipButton;
	public Button shuffleButton;
	public Button repeatButton;
	
	public ControlView() {
		shuffleButton = new Button();
		shuffleButton.getStyleClass().add("icon-button");
		shuffleButton.setId("shuffle-button");

		skipBackButton = new Button();
		skipBackButton.getStyleClass().add("icon-button");
		skipBackButton.setId("skipback-button");

		playPauseButton= new ToggleButton();
		playPauseButton.getStyleClass().add("icon-button");
		playPauseButton.setId("play-button");

		skipButton = new Button();
		skipButton.getStyleClass().add("icon-button");
		skipButton.setId("skip-button");

		repeatButton = new Button();
		repeatButton.getStyleClass().add("icon-button");
		repeatButton.setId("repeat-button");

		this.setId("control-bar");
		this.getChildren().addAll(shuffleButton, skipBackButton, playPauseButton, skipButton, repeatButton);
	
	}

}
