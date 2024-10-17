
package presentation.scenes;

import java.io.IOException;
import java.util.List;

import application.App;
import business.MP3Player;
import business.Playlist;
import business.PlaylistManager;
import business.Track;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;


public class PlaylistViewController extends ViewController<App> {
	
	@FXML
	Label titleLabel;
	@FXML
	private ListView<Track> listView;
	
	@FXML
	Button backButton;
	
	private MP3Player mp3Player;
	private PlaylistManager playlistManager;

	public PlaylistViewController(App application, MP3Player mp3Player, PlaylistManager playlistManager) {
		super(application);
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PlaylistView.fxml"));
			loader.setController(this);
			rootView = loader.load();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.mp3Player = mp3Player;
		this.playlistManager = playlistManager;
		initialize();
	}


	@Override
	public void initialize() {
		if (playlistManager != null) {
			Playlist selectedPlaylist = playlistManager.createPlaylistFromM3U("/Users/macbook/IdeaProjects/eibo/src/tracks/list.m3u");
			selectedPlaylist.setTitle("p1");
			selectedPlaylist = playlistManager.getPlaylist(selectedPlaylist.getTitle());
			mp3Player.setPlaylist(selectedPlaylist);
			playlistManager.getAllTracks();
			listView.setOnMouseClicked(this::handleTrackSelection);
			if (selectedPlaylist != null) {
				setPlaylist(selectedPlaylist);
				System.out.println("Playlist selected");
			} else {
				System.out.println("Playlist not found.");
			}
		}

	}



	public void setPlaylist(Playlist selectedPlaylist) {
		titleLabel.setText("Playlist: " + selectedPlaylist.getTitle());
		List<Track> tracks = selectedPlaylist.getTracks();
		listView.setCellFactory(new Callback<ListView<Track>, ListCell<Track>>() {
			@Override
			public ListCell<Track> call(ListView<Track> tracks) {
				return new TrackCell();
			}
		});
		ObservableList<Track> playlistModel = listView.getItems();
		playlistModel.addAll(tracks);

//		Thread deleteThread = new Thread(() -> {
//			while(playlistModel.size() > 5) {
//				try {
//					Platform.runLater(() -> playlistModel.remove(0));
//					Thread.sleep(500);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		});

		//deleteThread.start();

	}

	@FXML
	private void handleTrackSelection(MouseEvent event) {
		Track selectedTrack = listView.getSelectionModel().getSelectedItem();
		titleLabel.setText(selectedTrack.toString());
		if (selectedTrack != null) {
			mp3Player.pause();
			application.switchScene(Scenes.PLAYER_VIEW);
			if (!mp3Player.isPlaying()) {
				mp3Player.play(selectedTrack.getMp3FilePath());
				mp3Player.setLastPlaybackPosition(0);
				mp3Player.currentTimeProperty().setValue(0);
				mp3Player.setTrackNo(listView.getItems().indexOf(selectedTrack));
				PlayerViewController.playPauseButton.setId("pause-button");
			}
		} else {
			mp3Player.pause();
			PlayerViewController.playPauseButton.setId("play-button");
		}
		mp3Player.actTrackProperty().setValue(selectedTrack);
	}

	@FXML
	public void backAction() {
		System.out.println("back");

		if (application != null) {
			application.switchScene(Scenes.PLAYER_VIEW);
		}
	}



}
