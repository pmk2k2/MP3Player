package presentation.scenes;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import presentation.uicomponents.ControlView;
import presentation.uicomponents.ImageViewPane;


public class PlayerView extends BorderPane {
	GridPane headerPane;
	Label titleLabel;
	Label artistLabel;
	Label timeLabel;
	Label timeLabel1;
	ImageView coverImageView;
	ControlView controlView;
	Slider volumeSlider;
	Slider timeSlider;
	Button backButton;

	public PlayerView() {
		titleLabel = new Label("title");
		titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
		artistLabel = new Label("Interpret");
		artistLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
		coverImageView = new ImageView();
		coverImageView.setFitHeight(250);
		coverImageView.setFitWidth(250);
		coverImageView.fitHeightProperty();
		coverImageView.fitWidthProperty();


		ImageViewPane pane = new ImageViewPane(coverImageView);
		this.setCenter(pane);

		timeSlider = new Slider();
		timeLabel = new Label("0:00");
		timeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
		timeLabel1 = new Label("0:00");
		timeLabel1.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
		HBox timeBar = new HBox();
		timeBar.getChildren().addAll(timeLabel, timeSlider, timeLabel1 );
		timeBar.setId("time-bar");

		volumeSlider = new Slider();
		Button volumeLow = new Button();
		volumeLow.setId("volume-button-low");
		volumeLow.getStyleClass().add("icon-button");
		Button volumeHigh = new Button();
		volumeHigh.setId("volume-button-high");
		volumeHigh.getStyleClass().add("icon-button");
		HBox volumeBar = new HBox(volumeLow, volumeSlider, volumeHigh);
		volumeBar.setId("volume-bar");

		controlView = new ControlView();
		backButton = new Button();
		backButton.setId("playlist-button");
		backButton.getStyleClass().add("icon-button");
		headerPane = new GridPane();
		headerPane.setAlignment(Pos.CENTER);
		headerPane.setHgap(10);
		headerPane.setVgap(10);

		ColumnConstraints col1 = new ColumnConstraints();
		col1.setHalignment(HPos.LEFT);
		col1.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
		col1.setMinWidth(25);
		col1.setPrefWidth(100);

		ColumnConstraints col2 = new ColumnConstraints();
		col2.setHalignment(HPos.CENTER);
		col2.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
		col2.setMinWidth(25);
		col2.setPrefWidth(100);

		ColumnConstraints col3 = new ColumnConstraints();
		col3.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
		col3.setMinWidth(25);
		col3.setPrefWidth(100);

		RowConstraints row1 = new RowConstraints();
		row1.setMinHeight(5);
		row1.setPrefHeight(5);
		row1.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

		RowConstraints row2 = new RowConstraints();
		row2.setMinHeight(5);
		row2.setPrefHeight(5);
		row2.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

		RowConstraints row3 = new RowConstraints();
		row3.setMinHeight(5);
		row3.setPrefHeight(5);
		row3.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

		headerPane.getColumnConstraints().addAll(col1, col2, col3);
		headerPane.getRowConstraints().addAll(row1, row2, row3);

		GridPane.setConstraints(titleLabel, 1, 0);
		GridPane.setConstraints(artistLabel, 1, 1);
		GridPane.setConstraints(backButton, 0, 1);

		headerPane.getChildren().addAll(titleLabel, artistLabel, backButton);
		headerPane.setId("player-header");
		VBox bottomContainer = new VBox(timeBar, controlView, volumeBar);
		bottomContainer.setId("bottom-container");

		this.setTop(headerPane);
		this.setCenter(coverImageView);
		this.setBottom(bottomContainer);


	}
}
