package dominoes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Object for the bar above the play area, which contains info on Player 3 as well as the scoreboard.
 * @author carroll
 *
 */
public class TopBox extends HBox{

	private VBox scoreBoard;
	private HBox playerNameBox, handSizeBox;
	private Text team1Score, team2Score, countScore;
	private Text playerName, handSize;
	
	/**
	 * Default constructor for TopBox object.
	 */
	public TopBox() {
		this.setPrefWidth(1000);
		this.setPrefHeight(75);
		this.setStyle("-fx-background-color: blue");
		
		createScoreBoard();
		createComputer();
		
	}//constructor
	
	/**
	 * Initializes the scoreboard object with team scores.
	 */
	private void createScoreBoard() {
		scoreBoard = new VBox();
		scoreBoard.setPadding(new Insets(5,20,5,20));
		
		HBox team1 = new HBox();
		HBox team2 = new HBox();
		HBox count = new HBox();
		
		Text team1Name = new Text("Your Team: ");
		Text team2Name = new Text("Enemy Team: ");
		Text countName = new Text("Count: ");
		team1Score = new Text("0");
		team2Score = new Text("0");
		countScore = new Text("0");
		
		String style = "-fx-font: 20 arial;";
		Color color = Color.WHITE;
		team1Name.setFill(color);
		team2Name.setFill(color);
		team1Score.setFill(color);
		team2Score.setFill(color);
		countName.setFill(color);
		countScore.setFill(color);
		team1Name.setStyle(style);
		team2Name.setStyle(style);
		team1Score.setStyle(style);
		team2Score.setStyle(style);
		countName.setStyle(style);
		countScore.setStyle(style);
		
		count.getChildren().addAll(countName, countScore);
		team1.getChildren().addAll(team1Name, team1Score);
		team2.getChildren().addAll(team2Name, team2Score);
		
		scoreBoard.getChildren().addAll(team1, team2, count);
		this.getChildren().add(scoreBoard);
	}//createScoreBoard
	
	/**
	 * Creates the computer player's name and hand size elements.
	 */
	private void createComputer() {
		playerName = new Text();
		playerName.setFill(Color.WHITE);
		playerNameBox = new HBox();
		playerNameBox.getChildren().add(playerName);
		
		//number of dominoes in hand
		handSize = new Text();
		handSize.setFill(Color.WHITE);
		handSizeBox = new HBox();
		handSizeBox.getChildren().add(handSize);
		
		VBox stacker = new VBox();
		stacker.setPrefWidth(App.SCENE_WIDTH - (350));
		stacker.setAlignment(Pos.BOTTOM_CENTER);
		stacker.setPadding(new Insets(0,0,10,0));
		stacker.getChildren().addAll(playerName, handSize);
		this.getChildren().add(stacker);
	}//createComputer
	
	public void setName(String name) {
		playerName.setText(name);
	}
	
	public void setHandSize(int size) {
		handSize.setText("Hand size: " + String.valueOf(size));
	}
	
	/**
	 * Sets the scoreboard to a new value by adding to it.
	 * @param team The team for which to adjust the score.
	 * @param score The score for the selected team to add.
	 */
	public void addScore(int team, int score) {
		if(team == 1) {
			int oldScore = Integer.parseInt(team1Score.getText());
			int newScore = oldScore + score;
			team1Score.setText(Integer.toString(newScore));
		} else if (team == 2) {
			int oldScore = Integer.parseInt(team2Score.getText());
			int newScore = oldScore + score;
			team2Score.setText(Integer.toString(newScore));
		}
	}//addScore
	
	/**
	 * Sets the count to a new value.
	 * @param count The current board score.
	 */
	public void setCount(int count) {
		countScore.setText(Integer.toString(count));
	}//setCount
	
	public int getTeam1Score() {
		return Integer.valueOf(team1Score.getText());
	}
	
	public int getTeam2Score() {
		return Integer.valueOf(team2Score.getText());
	}
	
}//TopBox
