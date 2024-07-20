package application;

import business.MP3Player;
import presentation.PlayerViewController;
import presentation.PlaylistViewController;
import presentation.Scenes;
import presentation.ViewController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;

public class App extends Application {
    private Scene scene;
    private Pane currentScene;
    private HashMap<Scenes, Pane> scenes;

    private MP3Player mp3Player;

    @Override
    public void init() {
        mp3Player = new MP3Player();
        scenes = new HashMap<>();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            ViewController<App> controller;

            controller = new PlayerViewController(this, mp3Player);
            scenes.put(Scenes.PLAYER_VIEW,  controller.getRootView());

            controller = new PlaylistViewController(this, mp3Player);
            scenes.put(Scenes.PLAYLIST_VIEW, controller.getRootView());

            Pane root = scenes.get(Scenes.PLAYLIST_VIEW);
            scene = new Scene(root,400,400);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
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
