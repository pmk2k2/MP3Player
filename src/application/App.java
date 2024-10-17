package application;

import business.MP3Player;
import business.PlaylistManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import presentation.scenes.*;

import java.util.HashMap;

public class App extends Application {
    private Scene scene;
    private Pane currentScene;
    private HashMap<Scenes, Pane> scenes;

    private MP3Player mp3Player;
    private PlaylistManager playlistManager;

    @Override
    public void init() {
        mp3Player = new MP3Player();
        playlistManager = new PlaylistManager();
        scenes = new HashMap<>();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            ViewController<App> controller;

            controller = new PlayerViewController(this, mp3Player);
            scenes.put(Scenes.PLAYER_VIEW,  controller.getRootView());

            controller = new PlaylistViewController(this,mp3Player,playlistManager);
            scenes.put(Scenes.PLAYLIST_VIEW, controller.getRootView());

//            controller = new LibraryViewController(this,mp3Player,playlistManager);
//            scenes.put(Scenes.LIBRARY_VIEW, controller.getRootView());

            Pane root = scenes.get(Scenes.PLAYLIST_VIEW);
            scene = new Scene(root,800,600);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void switchScene(Scenes sceneName) {
        Pane nextScene;

        if (scenes.containsKey(sceneName)) {
            nextScene = scenes.get(sceneName);
            scene.setRoot(nextScene);
            currentScene = nextScene;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
