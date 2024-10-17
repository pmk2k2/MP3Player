package presentation.scenes;

import application.App;
import business.MP3Player;
import business.Track;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import presentation.uicomponents.ControlView;
import java.io.ByteArrayInputStream;



public class PlayerViewController extends ViewController<App> {

	Label titleLabel;
	Label artistLabel;
	Label timeLabel;
	Label timeLabel1;

	static ToggleButton playPauseButton;
	Button skipBackButton;
	Button skipButton;
	Button shuffleButton;
	Button repeatButton;
	Slider timeSlider;
	Slider volumeSlider;
	Button backButton;
	ImageView coverImageView;
	private MP3Player mp3Player;

	public PlayerViewController(App application, MP3Player mp3Player) {
		super(application);
		rootView = new PlayerView();

		PlayerView view = (PlayerView) rootView;

		titleLabel = view.titleLabel;
		artistLabel = view.artistLabel;
		timeLabel = view.timeLabel;
		timeLabel1 = view.timeLabel1;
		coverImageView = view.coverImageView;
		volumeSlider = view.volumeSlider;
		timeSlider = view.timeSlider;

		ControlView controlView = view.controlView;
		playPauseButton =controlView.playPauseButton;
		skipBackButton = controlView.skipBackButton;
		skipButton = controlView.skipButton;
		shuffleButton = controlView.shuffleButton;
		repeatButton = controlView.repeatButton;

		backButton = view.backButton;
		this.mp3Player = mp3Player;
		initialize();

	}
	private void updateCurrentTrack(Track selectedTrack) {
		if (selectedTrack != null) {
			artistLabel.setText(selectedTrack.getArtist());
			titleLabel.setText(selectedTrack.getTitle());
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(selectedTrack.getImageData());
			Image image = new Image(byteArrayInputStream);
			coverImageView.setImage(image);

			BackgroundSize bgSize = new BackgroundSize(800, 600, true, true, true, true);
			Rectangle darkOverlay = new Rectangle(800, 600, Color.rgb(0, 0, 0, 0.8));
			ImageView bImage = new ImageView(image);
			bImage.setFitWidth(800);
			bImage.setFitHeight(600);
			Group group = new Group(bImage, darkOverlay);
			SnapshotParameters params = new SnapshotParameters();
			params.setFill(Color.TRANSPARENT);
			WritableImage blendedImage = group.snapshot(params, null);

			BackgroundImage bgImage = new BackgroundImage(blendedImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bgSize);
			Background bg = new Background(bgImage);
			rootView.setBackground(bg);


		}
	}

	@Override
	public void initialize() {
		playPauseButton.addEventHandler(ActionEvent.ACTION,
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						new Thread(() -> {
							if (mp3Player.isPlaying()) {
								Platform.runLater(
										() -> playPauseButton.setId("play-button")
								);
								mp3Player.pause();
							} else {
								Platform.runLater(
										() -> playPauseButton.setId("pause-button")
								);
								mp3Player.play(mp3Player.getActTrack().getMp3FilePath());
								Platform.runLater(
										() -> playPauseButton.setId("pause-button")
								);
							}
						}).start();
					}
				});
		skipButton.setOnAction(actionEvent -> mp3Player.skip());
		skipBackButton.setOnAction(actionEvent -> mp3Player.skipBack());
		shuffleButton.setOnAction(actionEvent -> mp3Player.shuffle(true));
		repeatButton.setOnAction(actionEvent -> mp3Player.repeat(true));
//addEventHandler is a more general method that allows you to attach multiple event handlers to a single node for a specific event type.
//It's useful when you want to handle the same event type with multiple event handlers.
//It allows for attaching handlers for custom events or any predefined event types.
//setOnAction is specifically used for handling action events.
//It's a shorthand method when you are dealing with a button or any other UI control that generates action events.
//It's simpler and more specific when you only need to handle the action event for a particular control.


		volumeSlider.setMin(0);
		volumeSlider.setMax(100);
		volumeSlider.setValue(50);

		volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				mp3Player.volume(volumeSlider.getValue());
			}
		});
		timeSlider.setOnMouseReleased(event -> {

            int seekTo = (int) timeSlider.getValue();
            System.out.println(seekTo);
            mp3Player.seek(seekTo*1000, mp3Player.getActTrack().getMp3FilePath());
//			timeSlider.setValue(newValue.intValue()+seekTo);

            mp3Player.currentTimeProperty().addListener((v, oldValue, newValue) -> {

                Track currentTrack = mp3Player.getActTrack();

                timeSlider.setMax(mp3Player.getActTrack().getLength());

                timeSlider.setValue(newValue.intValue()+seekTo);

                if (currentTrack != null && newValue.intValue() >= currentTrack.getLength()) {

                    mp3Player.pause();
                    mp3Player.skip();
                }

            });

        });


		mp3Player.currentTimeProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Platform.runLater(() -> {
					timeSlider.setValue(newValue.intValue());
					int minutes = newValue.intValue() / 60;
					int seconds = newValue.intValue() % 60;
					timeLabel.setText(minutes + ":" + String.format("%02d", seconds));
					int totalDuration = mp3Player.actTrackProperty().get().getLength();
					int remainingTime = totalDuration - newValue.intValue();
					int minutes1 = remainingTime / 60;
					int seconds1 = remainingTime % 60;
					timeLabel1.setText(minutes1 + ":" + String.format("%02d", seconds1));
					timeSlider.setValue(newValue.intValue());
					timeSlider.setMax(totalDuration);

				});
			}
			// In der Methode versuchen wir innerhalb des Timesliders den Abspielpunkt zu ändern, jedoch haben wir es nicht hinbekommen

		});
		mp3Player.actTrackProperty().addListener((observable, oldTrack, newTrack) -> {
			Platform.runLater(() -> updateCurrentTrack(newTrack));

		});
		backButton.setOnAction(actionEvent -> {
			if (application != null) {
				application.switchScene(Scenes.PLAYLIST_VIEW);
			}
		});
	}}

