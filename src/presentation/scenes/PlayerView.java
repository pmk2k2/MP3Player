package presentation.scenes;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import presentation.uicomponents.ControlView;

/**
 * PlayerView als programmierte Klasse.
 * 
 * @author berdux
 *
 */
public class PlayerView extends BorderPane {
	
	VBox headerPane;
	Label titleLabel;
	Label interpretLabel;
	
	ImageView coverImageView;
	
	ControlView controlView;
	
	
	public PlayerView() {
		// Header of View
		headerPane = new VBox();
		titleLabel = new Label("title");
		interpretLabel = new Label("Interpret");
		headerPane.getChildren().addAll(titleLabel, interpretLabel);
		headerPane.setId("player-header");
		// Content
		coverImageView = new ImageView();
		
		// Control Bar of Player
		controlView = new ControlView();
		
		this.setTop(headerPane);
		this.setCenter(coverImageView);
		this.setBottom(controlView);
	}

}
