package presentation.scenes;

import java.io.IOException;
import java.util.ResourceBundle;

import application.PlayerApp;
import business.MP3Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controller verwaltet seine View und ist fuer die Initialisierung
 * der View verantwortlich.
 * 
 * In diesem Fall laed der Controller eine FXML-Beschreibung, in der die View definiert
 * ist. Die Verknuepfungen der UI-Elemente zu den Attributen erfolgt ueber die 
 * FXML-Annotations der Attribute und der entsprechenden Ids in der FXML.
 * 
 * Die initialize-Methode wird von der JavaFX-Umgebung nach dem laden der View und der
 * Umsetzung der Attribut-Verknuepfungen automatisch aufgerufen
 * 
 * @author berdux
 *
 */
public class PlaylistViewController extends ViewController<PlayerApp> {
	
	@FXML
	Label titleLabel;
	
	@FXML
	Button backButton;
	
	private MP3Player mp3Player;
	
	public PlaylistViewController() {
		
	}

	public PlaylistViewController(PlayerApp application, MP3Player mp3Player) {
		super(application);
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("PlaylistView.fxml"));
			loader.setController(this);
			rootView = loader.load();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.mp3Player = mp3Player;

	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		titleLabel.setText("Paylist View");
	}
	
	@FXML
	public void backAction() {
		System.out.println("back");

		if (application != null) {
			application.switchScene(Scenes.PLAYER_VIEW);
		}
	}

}
