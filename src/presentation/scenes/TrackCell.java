package presentation.scenes;

import business.Track;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TrackCell extends ListCell<Track> {
	HBox root;
	VBox infoPane;
	Label title;
	Label artist;
	
	public TrackCell() {
		infoPane = new VBox();
		title = new Label();
		title.setStyle("-fx-text-fill: white");
		artist = new Label();
		artist.setStyle("-fx-text-fill: white");
		infoPane.getChildren().addAll(title, artist);

		root = new HBox();
		root.getChildren().addAll(infoPane);
		root.setId("list-cell");
		
	}
	
	@Override
	protected void updateItem(Track item, boolean empty) {
		super.updateItem(item, empty);
		
		if (!empty) {
			title.setText(item.getTitle());
			artist.setText(item.getArtist());
			
			this.setGraphic(root);
		} else {
			this.setGraphic(null);
		}
		
	}

}
