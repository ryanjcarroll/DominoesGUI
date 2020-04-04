package dominoes;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Class for the sidebars on left and right which contain information about the enemy computer players.
 */
public class SideBox extends VBox{

	private Text playerName, handSize;
	private HBox playerNameBox, handSizeBox;
	
	/**
	 * Default constructor
	 */
	public SideBox() {
		this.setPrefWidth(75);
		this.setStyle("-fx-background-color: red");
		
		//player name
		playerName = new Text();
		playerNameBox = new HBox();
		playerNameBox.getChildren().add(playerName);
		
		//number of dominoes in hand
		handSize = new Text();
		handSizeBox = new HBox();
		handSizeBox.getChildren().add(handSize);
		
		this.getChildren().addAll(playerName, handSize);
		this.setAlignment(Pos.CENTER);
	}//cconstructor
	
	
	public void setName(String name) {
		playerName.setText(name);
	}
	
	public void setHandSize(int size) {
		handSize.setText("Hand size: " + String.valueOf(size));
	}
	
	
}//SideBox
