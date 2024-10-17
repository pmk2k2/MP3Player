//package presentation.scenes;
//
//import application.App;
//import business.MP3Player;
//import business.Playlist;
//import business.PlaylistManager;
//import business.Track;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.control.ListView;
//import javafx.scene.input.MouseEvent;
//
//import java.io.IOException;
//
//public class LibraryViewController extends ViewController {
//
//    @FXML
//    private ListView<Playlist> playlistListView;
//    @FXML
//    private ListView<Track> tracksListView;
//    private ObservableList<Playlist> playlists = FXCollections.observableArrayList();
//    private ObservableList<Track> tracks = FXCollections.observableArrayList();
//    private PlaylistManager playlistManager;
//    private MP3Player mp3Player;
//
//    public LibraryViewController(App application, MP3Player mp3Player, PlaylistManager playlistManager) {
//        super(application);
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("LibraryView.fxml"));
//            loader.setController(this);
//            rootView = loader.load();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        this.mp3Player = mp3Player;
//        this.playlistManager = playlistManager;
//        initialize();
//    }
//
//    @Override
//    public void initialize() {
//        loadPlaylists();
//        loadAllSongs();
//        playlistListView.setOnMouseClicked(this::selectPlaylist);
//    }
//
//    private void loadPlaylists() {
//        playlists.addAll(playlistManager.getAllPlaylists());
//        playlistListView.setItems(playlists);
//    }
//
//    private void loadAllSongs() {
//        tracks.addAll(playlistManager.getAllTracks());
//        tracksListView.setItems(tracks);
//    }
//
//    private void selectPlaylist(MouseEvent event) {
//        Playlist selectedPlaylist = playlistListView.getSelectionModel().getSelectedItem();
//        if (selectedPlaylist != null) {
//            Playlist playlist = playlistManager.getPlaylist(selectedPlaylist.getTitle());
//            if (playlist != null) {
//                mp3Player.setPlaylist(playlist);
//                System.out.println("Selected playlist: " + selectedPlaylist);
//            } else {
//                System.out.println("Failed to load selected playlist.");
//            }
//        } else {
//            System.out.println("No playlist selected.");
//        }
//    }
//    private void selectTrack(MouseEvent event) {
//        Track selectedTrack = tracksListView.getSelectionModel().getSelectedItem();
//        if (selectedTrack != null) {
//            mp3Player.pause();
////            application.switchScene(Scenes.PLAYER_VIEW);
//            if (!mp3Player.isPlaying()) {
//                mp3Player.play(selectedTrack.getMp3FilePath());
//                mp3Player.setLastPlaybackPosition(0);
//                mp3Player.currentTimeProperty().setValue(0);
//                PlayerViewController.playPauseButton.setId("pause-button");
//            }
//        } else {
//            mp3Player.pause();
//            PlayerViewController.playPauseButton.setId("play-button");
//        }
//        mp3Player.actTrackProperty().setValue(selectedTrack);
//    }
//}
//
