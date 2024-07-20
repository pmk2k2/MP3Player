package presentation.scenes;

import application.PlayerApp;
import business.MP3Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import presentation.uicomponents.ControlView;

/**
 * Controller verwaltet seine View und ist fuer die Initialisierung
 * der View verantwortlich.
 * 
 * In diesem Fall erzeugt der Controller seine View als normale
 * Objekt-Instanziierung. 
 * Weitere Variante: laden einer FXML-Beschreibung, in der View definiert
 * ist.
 * 
 * @author berdux
 *
 */
public class PlayerViewController extends ViewController<PlayerApp> {
		
	Label titleLabel;
	Label interpretLabel;
	
	Button playButton;
	Button skipBackButton;
	Button skipButton;	
	
	private class SkipBackHandler implements EventHandler<ActionEvent> {
		private int counter = 0;
		
		@Override
		public void handle(ActionEvent event) {
			counter++;
			mp3Player.skipBack();
		}
	}
	
	private MP3Player mp3Player;
	
	public PlayerViewController(PlayerApp application, MP3Player mp3Player) {
		super(application);
		
		rootView = new PlayerView();
		
		PlayerView view = (PlayerView) rootView;
		
		titleLabel = view.titleLabel;
		interpretLabel = view.interpretLabel;
		
		ControlView controlView = view.controlView;
		playButton = controlView.playButton;
		skipBackButton = controlView.skipBackButton;
		skipButton = controlView.skipButton;
		
		this.mp3Player = mp3Player;
		
		initialize();
	}
	
	public void initialize() {
		// Anonyme Klasse, die die Methode des Interfaces umsetzt
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mp3Player.play();
			}
		});
		
		// Lambda-Ausdruck, der nur noch die Methode definiert
		skipButton.setOnAction(event -> mp3Player.skip());
		
		// Member-Klasse, die dann ganz normal als Objekt erzeugt wird
		skipBackButton.setOnAction(new SkipBackHandler());
	}

}
