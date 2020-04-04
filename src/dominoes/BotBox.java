package dominoes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class BotBox extends HBox{

	private VBox left, right;
	private HBox hand, passBox, nextBox, quitBox;
	private Text bannerText;
	private String style = "-fx-font: 20 arial;";
	private Color color = Color.WHITE;
	private Button nextButton, passButton;

	/**
	 * Default constructor
	 */
	public BotBox(App app) {
		//size features
		this.setMinWidth(1000);
		this.setMinHeight(225);
		this.setStyle("-fx-background-color: blue");

		//announcements banner
		bannerText = new Text("Banner");
		bannerText.setStyle(style);
		bannerText.setFill(color);
		HBox banner = new HBox();
		banner.setMinWidth(800);
		banner.getChildren().add(bannerText);
		banner.setPadding(new Insets(10,20,0,20));
		
		//hand bar
		hand = new HBox();
		hand.setPadding(new Insets(10,20,10,20));
		
		//other boxes which get filled within the Game class
		passBox = new HBox();
		nextBox = new HBox();
		quitBox = new HBox();
//		turnsBox = new HBox();
	
		passBox.setMinHeight(100);
		nextBox.setMinHeight(100);
		
		//quit button
		Button quitButton = new Button("Quit");
		EventHandler<ActionEvent> quitHandler = ((event) -> {
			System.exit(0);
		});
		quitButton.setOnAction(quitHandler);
		quitBox.getChildren().add(quitButton);
		
		//nextButton and passButton
		nextButton = new Button("Next");
		nextButton.setDisable(true);
		passButton = new Button("Pass");
		passButton.setDisable(true);	
		nextBox.getChildren().add(nextButton);
		passBox.getChildren().add(passButton);
		
//		//auto/manual turns toggle button
//		turnsBox.setPadding(new Insets(5,5,5,5));
//		
//		ToggleButton turnsButton = new ToggleButton("Computer Turns: Manual");
//		EventHandler<ActionEvent> turnsHandler = ((event) -> {
//			if(turnsButton.isSelected()) {
//				turnsButton.setText("Computer Turns: Auto");
//				app.game.setAutoComputerTurns(true);
//			} else {
//				turnsButton.setText("Computer Turns: Manual");
//				app.game.setAutoComputerTurns(false);
//			}
//		});
//		turnsButton.setOnAction(turnsHandler);
//		turnsBox.getChildren().add(turnsButton);
		
		//position and format the button boxes
		left = new VBox();
		left.getChildren().addAll(banner, hand);
		left.setAlignment(Pos.CENTER_LEFT);
		
		right = new VBox();
		right.setAlignment(Pos.CENTER_LEFT);
		
		//TODO add zoombox here to enable zoom buttons
		right.getChildren().addAll(nextBox, passBox, quitBox);
		
		nextBox.setAlignment(Pos.CENTER_LEFT);
		nextBox.setPadding(new Insets(5,5,5,5));
		nextBox.setMinHeight(10);
		
		passBox.setAlignment(Pos.CENTER_LEFT);
		passBox.setPadding(new Insets(5,5,5,5));
		passBox.setMinHeight(10);
		
		quitBox.setAlignment(Pos.BOTTOM_LEFT);
		quitBox.setPadding(new Insets(5,5,5,5));
		
		this.getChildren().addAll(left, right);
	}//constructor
	
	/**
	 * returns the HBox representing the hand area.
	 */
	public HBox getHand() {
		return hand;
	}//getHand
	
	/**
	 * Sets the banner text.
	 * @param t The new text to set.
	 */
	public void setBanner(String t) {
		bannerText.setText(t);
	}//setBanner
	
	public HBox getPassBox() {
		return passBox;
	}
	
	public HBox getNextBox() {
		return nextBox;
	}
	
	public Button getNextButton() {
		return nextButton;
	}
	
	public Button getPassButton() {
		return passButton;
	}
	
}//BotBox
