package dominoes;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


/**
 * Class used to play a standard dominoes game.
 * @author carroll
 *
 */
public class Game {

	private List<Player> players;
	private Set set;
	private App app;
	private Button passButton, nextButton;
	private int counter;
	private boolean autoComputerTurns;
	private AudioClip clickSound, dropSound, scoreSound, endSound;

	/**
	 * Default constructor for Game object.
	 */
	public Game(App app) {
		this.app = app;
		this.set = new Set();
		this.players = new ArrayList<>();

		passButton = app.botBox.getPassButton();
		EventHandler<ActionEvent> passHandler = ((event) -> {
			playDomino(null, "pass");
		});
		passButton.setOnAction(passHandler);

		autoComputerTurns = false;
	
		scoreSound = new AudioClip(
				getClass().getResource("/sounds/score.wav").toString());
		clickSound = new AudioClip(
				getClass().getResource("/sounds/click.wav").toString());
		dropSound = new AudioClip(
				getClass().getResource("/sounds/drop.wav").toString());
		endSound = new AudioClip(
				getClass().getResource("/sounds/fanfare.wav").toString());
	}//constructor

	/**
	 * The main method that runs the game.
	 */
	public void play() {
		createPlayers();
		dealHands();	
		playerTurn(true);	
	}//play

	/**
	 * Prompts the user for Player names and creates two teams of two players each.
	 */
	public void createPlayers() {
		//get player names
		String p0_name = "Player";
		String p1_name = "Computer1";
		String p2_name = "Computer2";	
		String p3_name = "Computer3";

		//create player objects
		players = new ArrayList<Player>();
		Player p0 = new Player(0, p0_name, false, app);
		Player p1 = new Player(1, p1_name, true, app);
		Player p2 = new Player(2, p2_name, true, app);
		Player p3 = new Player(3, p3_name, true, app);

		//add to players list
		this.players.add(p0);
		this.players.add(p1);
		this.players.add(p2);
		this.players.add(p3);

		app.leftBox.setName(p1_name);
		app.topBox.setName(p2_name);
		app.rightBox.setName(p3_name);
	}//createPlayers

	/**
	 * Deals each player their starting hand of seven dominoes.
	 */
	private void dealHands() {
		//shuffle and deal 7 to each player.
		set.shuffle();
		for(Player p : players) {
			set.dealToPlayer(p,7);
		}
		app.leftBox.setHandSize(players.get(1).getHandSize());	
		app.topBox.setHandSize(players.get(2).getHandSize());
		app.rightBox.setHandSize(players.get(3).getHandSize());

		System.out.println(app.centerPane.getWidth());
		System.out.println(app.centerPane.getHeight());
		//create imageView objects for each domino in player's hand and add to hand area
		for(Domino d : players.get(0).getHand()) {
			app.botBox.getHand().getChildren().add(d.getImageView());
		}
	}//dealHands

	/**
	 * Called to let the human player take their turn, allowing them to play a domino or pass.
	 * Returns true once the turn is over.
	 * @param firstTime Set true if first time the player has attempted to give input this turn, false if this is not the first attempt.
	 */
	private void playerTurn(boolean firstTime) {
		if(firstTime) {
			app.botBox.setBanner("Your Move. Select a domino by clicking on it.");
		} else {
			app.botBox.setBanner("That domino doesn't go there! Select a domino by clicking on it.");
		}
		
		for(Domino d : players.get(0).getHand()) {
			//handler for clicking on dominoes in player's hand
			d.setClickable(true);
			d.getImageView().setOnMouseClicked((MouseEvent select) -> {
				
				clickSound.play();
				
				//special case first domino played
				if(app.centerPane.getPlayed().size() == 0) {
					System.out.println("clicked on domino " + d.getAsString());
					app.botBox.setBanner("Domino " + d.getAsString() + " selected. Click the board area to play.");
					app.root.getCenter().setOnMouseClicked((MouseEvent play) -> {
						if(d.isClickable()) {
							playDomino(d, null);
						}
					});
				}
				//special case second domino played (choose left or right)
				else if (app.centerPane.getPlayed().size() == 1) {
					Domino rightDom = app.centerPane.getRightMost();
					ImageView iv = rightDom.getImageView();

					System.out.println("clicked on domino " + d.getAsString());
					app.botBox.setBanner("Domino " + d.getAsString() + " selected. Click a domino on the board to choose where to play.");
					iv.setOnMouseClicked((MouseEvent play) -> {
						if(d.isClickable()) {						
							playDomino(d, null);							
						}
					});

				}
				//general case before top/bot arms unlocked
				//if the spinner does not exist, OR either of the L/R end dominoes is equal to the spinner
				else if (app.centerPane.getSpinner() == null || app.centerPane.getRightMost().equals(app.centerPane.getSpinner()) || app.centerPane.getLeftMost().equals(app.centerPane.getSpinner())) {
					Domino rightMost = app.centerPane.getRightMost();
					Domino leftMost = app.centerPane.getLeftMost();

					System.out.println("RightMost: " + rightMost.getAsString());
					System.out.println("LeftMost: " + leftMost.getAsString());

					app.botBox.setBanner("Domino " + d.getAsString() + " selected. Click a domino on the board to choose where to play.");

					leftMost.getImageView().setOnMouseClicked((MouseEvent play) -> {
						if(d.isClickable()) {
							playDomino(d, "left");
						}
					});

					rightMost.getImageView().setOnMouseClicked((MouseEvent play) -> {
						if(d.isClickable()) {
							playDomino(d, "right");
						}
					});

				} 
				//general case after the top/bot arms are unlocked
				else {
					Domino rightMost = app.centerPane.getRightMost();
					Domino leftMost = app.centerPane.getLeftMost();
					Domino topMost = app.centerPane.getTopMost();
					Domino botMost = app.centerPane.getBotMost();

					System.out.println("RightMost: " + rightMost.getAsString());
					System.out.println("LeftMost: " + leftMost.getAsString());
					System.out.println("TopMost: " + topMost.getAsString());
					System.out.println("BotMost: " + botMost.getAsString());

					app.botBox.setBanner("Domino " + d.getAsString() + " selected. Click a domino on the board to choose where to play.");

					leftMost.getImageView().setOnMouseClicked((MouseEvent play) -> {
						if(d.isClickable()) {
							playDomino(d, "left");
						}
					});

					rightMost.getImageView().setOnMouseClicked((MouseEvent play) -> {
						if(d.isClickable()) {
							playDomino(d, "right");
						}
					});

					topMost.getImageView().setOnMouseClicked((MouseEvent play) -> {
						if(d.isClickable()) {
							playDomino(d, "top");
						}
					});

					if(!topMost.equals(botMost)) {
						botMost.getImageView().setOnMouseClicked((MouseEvent play) -> {
							if(d.isClickable()) {
								playDomino(d, "bot");
							}
						});
					}
				}
			});
		}
	}//playerTurn

	/**
	 * Called when the user plays a domino from their hand onto the play area.
	 * @param d The domino to play.
	 * @param arm Set to "right", "left", "top", "bot", or null
	 */
	private void playDomino(Domino d, String arm) {	
		boolean done = false;
		
		if(arm != "pass") {
			d.setClickable(false);
			
			//special case first domino played
			if (app.centerPane.getPlayed().size() == 0) {
				System.out.println("placed initial domino " + d.getAsString() + ".");
				removeDominoFromHand(d);
				app.centerPane.playInitial(d);	
				done = true;
				app.botBox.setBanner("You placed initial domino " + d.getAsString() + " for a score of " + app.centerPane.getFilteredScore() + ".");
			}
			//special case second domino played 
			else if (app.centerPane.getPlayed().size() == 1){			
				Domino firstDom = app.centerPane.getPlayed().get(0);
				//ways to play left
				if (firstDom.getSide1() == d.getSide2()) {
					removeDominoFromHand(d);
					app.centerPane.playLeft(d);
					done = true;
					System.out.println("placed left domino " + d.getAsString());
					app.botBox.setBanner("You placed domino " + d.getAsString() + " on the left arm for a score of " + app.centerPane.getFilteredScore() + ".");
				} else if (firstDom.getSide1() == d.getSide1()) {
					removeDominoFromHand(d);
					app.centerPane.playLeft(d);
					done = true;
					System.out.println("placed and flipped left domino " + d.getAsString());
					app.botBox.setBanner("You placed domino " + d.getAsString() + " on the left arm for a score of " + app.centerPane.getFilteredScore() + ".");
				}
				//ways to play right
				else if (firstDom.getSide2() == d.getSide1()) {
					removeDominoFromHand(d);
					app.centerPane.playRight(d);
					done = true;
					System.out.println("placed right domino " + d.getAsString());
					app.botBox.setBanner("You placed domino " + d.getAsString() + " on the right arm for a score of " + app.centerPane.getFilteredScore() + ".");
				} else if (firstDom.getSide2() == d.getSide2()) {
					removeDominoFromHand(d);
					app.centerPane.playRight(d);
					done = true;
					System.out.println("placed and flipped right domino " + d.getAsString());
					app.botBox.setBanner("You placed domino " + d.getAsString() + " on the right arm for a score of " + app.centerPane.getFilteredScore() + ".");
				} else {
					System.out.println("That domino does not go there! (second domino played does not fit with either side of first domino, including with flips)");
					app.botBox.setBanner("That domino does not go there! Select a domino by clicking on it.");
				}			
			} 
			//general case, third or later domino played
			else if (players.get(0).getHand().size() > 0) {
				if(arm == "right") {
					Domino rightDom = app.centerPane.getRightMost();

					if(rightDom.getSide2() == d.getSide1()) {
						removeDominoFromHand(d);
						app.centerPane.playRight(d);
						done = true;
						System.out.println("placed right domino " + d.getAsString());
						app.botBox.setBanner("You placed domino " + d.getAsString() + " on the right arm for a score of " + app.centerPane.getFilteredScore() + ".");
					} else if (rightDom.getSide2() == d.getSide2()) {
						removeDominoFromHand(d);
						app.centerPane.playRight(d);
						done = true;
						System.out.println("placed and flipped right domino " + d.getAsString());
						app.botBox.setBanner("You placed domino " + d.getAsString() + " on the right arm for a score of " + app.centerPane.getFilteredScore() + ".");
					} else {
						System.out.println("That domino doesn't go there! (tried to place right)");
						app.botBox.setBanner("That domino does not go there! Select a domino by clicking on it.");
					}

				} else if(arm == "left") {
					Domino leftDom = app.centerPane.getLeftMost();

					if(leftDom.getSide1() == d.getSide2()) {
						removeDominoFromHand(d);
						app.centerPane.playLeft(d);
						done = true;
						System.out.println("placed left domino " + d.getAsString());
						app.botBox.setBanner("You placed domino " + d.getAsString() + " on the left arm for a score of " + app.centerPane.getFilteredScore() + ".");
					} else if (leftDom.getSide1() == d.getSide1()) {
						removeDominoFromHand(d);
						app.centerPane.playLeft(d);
						done = true;
						System.out.println("placed and flipped left domino " + d.getAsString());
						app.botBox.setBanner("You placed domino " + d.getAsString() + " on the left arm for a score of " + app.centerPane.getFilteredScore() + ".");
					} else {
						System.out.println("That domino doesn't go there! (tried to place left)");
						app.botBox.setBanner("That domino does not go there! Select a domino by clicking on it.");
					}

				} else if(arm == "top") {
					Domino topDom = app.centerPane.getTopMost();

					if (topDom.getSide1() == d.getSide2()) {
						removeDominoFromHand(d);
						app.centerPane.playTop(d);
						done = true;
						System.out.println("Placed top domino " + d.getAsString());
						app.botBox.setBanner("You placed domino " + d.getAsString() + " on the top arm for a score of " + app.centerPane.getFilteredScore() + ".");
					} else if (topDom.getSide1() == d.getSide1()) {
						removeDominoFromHand(d);
						app.centerPane.playTop(d);
						done = true;
						System.out.println("Placed and flipped top domino " + d.getAsString());
						app.botBox.setBanner("You placed domino " + d.getAsString() + " on the top arm for a score of " + app.centerPane.getFilteredScore() + ".");
					} else {
						System.out.println("That domino doesn't go there! (tried to place top)");
						app.botBox.setBanner("That domino does not go there! Select a domino by clicking on it.");
					}

				} else if(arm == "bot") {
					Domino botDom = app.centerPane.getBotMost();

					if (botDom.getSide2() == d.getSide1()) {
						removeDominoFromHand(d);
						app.centerPane.playBot(d);
						done = true;
						System.out.println("Placed bot domino " + d.getAsString());
						app.botBox.setBanner("You placed domino " + d.getAsString() + " on the bot arm for a score of " + app.centerPane.getFilteredScore() + ".");
					} else if (botDom.getSide2() == d.getSide2()) {
						removeDominoFromHand(d);
						app.centerPane.playBot(d);
						done = true;
						System.out.println("Placed and flipped bot domino " + d.getAsString());
						app.botBox.setBanner("You placed domino " + d.getAsString() + " on the bot arm for a score of " + app.centerPane.getFilteredScore() + ".");
					} else {
						System.out.println("That domino doesn't go there! (tried to place bot)");
						app.botBox.setBanner("That domino does not go there! Select a domino by clicking on it.");
					}
				}//end of right left top and bot	
			}//end of general case
			//special case no dominoes left in hand
			else {
				System.out.println("No dominoes left in hand to play");
			}
		} else {
			app.botBox.setBanner("You passed your turn.");
			done = true;
		}

		
		if(done) {
			dropSound.play();
			
			//turn off domino listeners to prevent plays while not the player's turn.
			for(Domino dom : players.get(0).getHand()) {
				dom.getImageView().setOnMouseClicked(event -> {
					app.botBox.setBanner("It is not your turn. Click NEXT to go to the next player's turn");
					System.out.println("Cannot click that now. Not your turn.");
				});
			}
			passButton.setDisable(true);

			if(arm != "pass") {
				updateScoreBoard(1);
			}
			finishTurn(0);

			//play computer turns if game not over
			if(!isGameOver()) {
				if(autoComputerTurns) {
					playTimedComputerTurns();
				} else {
					playManualComputerTurns();
				}
			}
		//if not done (player tried to do something invalid), try again with a new turn
		} else {
			playerTurn(false);
		}
	}//playDomino

	/**
	 * Removes a domino from the player's hand, as well as the ImageView from the hand area in the GUI.
	 * @param d The domino to remove.
	 */
	private void removeDominoFromHand(Domino d) { 
		for(Domino inHand: players.get(0).getHand()) {		
			if(d.isEqual(inHand)) {
				System.out.println("Removing domino " + inHand.getAsString() + " from player hand");
				players.get(0).removeFromHand(inHand);
				app.botBox.getHand().getChildren().remove(inHand.getImageView());
				break;
			}
		}	
	}//removeDominoFromHand

	/**
	 * Updates the scoreboard and adds to it if the current board is a scoring play.
	 * @param team Team id, either 1 (player and comp3) or 2 (comp1 and comp2).
	 */
	private void updateScoreBoard(int team) {
		int count = app.centerPane.getScore();
		app.topBox.setCount(count);
		int filteredScore = app.centerPane.getFilteredScore();
		app.topBox.addScore(team, filteredScore);

		if(filteredScore > 0) {
			System.out.println("Team " + team + " scored " + filteredScore + " points!");
			scoreSound.play();
		}
	}//updateScoreBoard

	/**
	 * Called to play the 3 AI player turns in sequence, using a 2-second interval timer.
	 */
	private void playTimedComputerTurns() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
			private int i = 1;

			//loop through the 3 AI players
			@Override
			public void handle(ActionEvent event) {
				if(!isGameOver() && i < 4) {
					computerTurn(i);
				}
				i++;
			}//handle
		}));
		timeline.setCycleCount(4);
		timeline.play();
		timeline.setOnFinished(event -> {
			finishTurn(3);
			passButton.setDisable(false);
			playerTurn(true);
		});
	}//playTimedComputerTurns

	/**
	 * Called to play the 3 AI player turns in sequence, allowing the user to click a "Next" button to trigger each sequential turn.
	 */
	private void playManualComputerTurns() {
		counter = 1;

		nextButton = app.botBox.getNextButton();
		nextButton.setDisable(false);
		nextButton.setText("Next");
		
		EventHandler<ActionEvent> nextHandler = ((event) -> {
			//count of which computer's turn it currently is (1-3)
			if(counter > 3) {
				finishTurn(counter);
				nextButton.setDisable(true);
				passButton.setDisable(false);
				playerTurn(true);
			} else if(counter == 3){
				nextButton.setText("My Turn");
				computerTurn(counter);
				counter++;
			} else {
				computerTurn(counter);
				counter++;
			}
		});
		nextButton.setOnAction(nextHandler);
	}//playManualComputerTurns

	/**
	 * Plays the computer player's turn.
	 * @param id The computer player's id number (1-3).
	 */
	private void computerTurn(int id) {
		System.out.println(players.get(id).getName() + "'s hand: ");
		for(Domino d : players.get(id).getHand()) {
			System.out.print(d.getAsString() + " ");
		}
		System.out.println();

		//get best move
		Move suggestedMove = players.get(id).suggestEasy();

		//play best move
		suggestedMove.print();

		if(suggestedMove.getDomino() == null || suggestedMove.getArm() == null) {
			//if no valid move, pass the turn
		} 
		//in the case where there is at least one valid move
		else {
			Domino dom = suggestedMove.getDomino();
			String arm = suggestedMove.getArm();

			computerPlayDomino(id, dom, arm);
		}
		
		if(suggestedMove.getArm() == "pass" || suggestedMove.getArm() == null) {
			app.botBox.setBanner(players.get(id).getName() + " " + suggestedMove.toString() + ".");
		} else {
			app.botBox.setBanner(players.get(id).getName() + " " + suggestedMove.toString() + " for a score of " + app.centerPane.getFilteredScore() + ".");
		}
		finishTurn(id);
	}//computerTurn

	/**
	 * Plays a selected domino from one of the computer players' hands onto a given arm of the board.
	 * @param id The number of the computer player (1-3)
	 * @param d The domino to play.
	 * @param arm The arm off which to play.
	 */
	private void computerPlayDomino(int id, Domino d, String arm) {
		Player computer = players.get(id);

		if(arm == "right") {
			app.centerPane.playRight(d);
			computer.removeFromHand(d);
			dropSound.play();
		}
		if(arm == "left") {
			app.centerPane.playLeft(d);
			computer.removeFromHand(d);
			dropSound.play();
		}
		if(arm == "top") {
			app.centerPane.playTop(d);
			computer.removeFromHand(d);
			dropSound.play();
		}
		if(arm == "bot") {
			app.centerPane.playBot(d);
			computer.removeFromHand(d);
			dropSound.play();
		}

		if(arm == "pass" || arm == null) {
			//do not score update if move was to pass
		} else {
			if(id == 2) {
				updateScoreBoard(1);
			} else {
				updateScoreBoard(2);
			}
		}

		int handSize = computer.getHandSize();
		if(id == 1) {
			app.leftBox.setHandSize(handSize);
		} else if (id == 2) { 
			app.topBox.setHandSize(handSize);
		} else if (id == 3) {
			app.rightBox.setHandSize(handSize);
		}
	}//computerPlayDomino

	/**
	 * Called at the end of each player's turn to re-size the board, and check if the game has ended.
	 */
	private void finishTurn(int id) {
		System.out.println("finishing turn of " + id);
		app.centerPane.reduceSizes(1);
		resizeBoard();

		if(isGameOver()) {			
			counter = 1;
			endSound.play();
			nextButton.setDisable(false);
			
			EventHandler<ActionEvent> endHandler = ((event) -> {
				//notify that game is over
				if(counter == 1) {
					app.botBox.setBanner("Game is over. " + players.get(id).getName() + " placed the final domino. Click end to continue.");
					counter ++;
				} 
				//the losing team will have their hand values added to the winner's score
				else if (counter == 2) {
					if(id == 1 || id == 3) {
						app.botBox.setBanner("Team 1 will have the total value of their hands added to Team 2's score.");
					} else {
						app.botBox.setBanner("Team 2 will have the total value of their hands added to Team 1's score.");
					}
					counter ++;
				} 
				//add the first loser's hand
				else if (counter == 3) {
					String hand = "";
					int handValue = 0;
					if(id == 1 || id == 3) {
						for(Domino d : players.get(0).getHand()) {
							hand += d.getAsString() + " ";
							handValue += d.getValue();
						}
						handValue = roundByFives(handValue);
						app.topBox.addScore(2, handValue);
						app.botBox.setBanner(players.get(0).getName() + "'s hand of " + hand + "adds " + handValue + ".");
					} else {
						for(Domino d : players.get(1).getHand()) {
							hand += d.getAsString() + " ";
							handValue += d.getValue();
						}
						handValue = roundByFives(handValue);
						app.topBox.addScore(1, handValue);
						app.botBox.setBanner(players.get(1).getName() + "'s hand of " + hand + "adds " + handValue + ".");
					}
					counter ++;
				} 
				//add the second loser's hand
				else if(counter == 4) {
					String hand = "";
					int handValue = 0;
					if(id == 1 || id == 3) {
						for(Domino d : players.get(2).getHand()) {
							hand += d.getAsString() + " ";
							handValue += d.getValue();
						}
						handValue = roundByFives(handValue);
						app.topBox.addScore(2, handValue);
						app.botBox.setBanner(players.get(2).getName() + "'s hand of " + hand + "adds " + handValue + ".");
					} else {
						for(Domino d : players.get(3).getHand()) {
							hand += d.getAsString() + " ";
							handValue += d.getValue();
						}
						handValue = roundByFives(handValue);
						app.topBox.addScore(1, handValue);
						app.botBox.setBanner(players.get(3).getName() + "'s hand of " + hand + "adds " + handValue + ".");
					}
					counter ++;
				}
				//show the results
				else if (counter == 5) {
					if(app.topBox.getTeam1Score() > app.topBox.getTeam2Score()) {
						app.botBox.setBanner("Team 1 wins with a score of " + app.topBox.getTeam1Score() + "!");
					} else  if(app.topBox.getTeam1Score() < app.topBox.getTeam2Score()){
						app.botBox.setBanner("Team 2 wins with a score of " + app.topBox.getTeam2Score() + "!");
					} else {
						app.botBox.setBanner("The result is a tie!");
					}
					counter ++;
				} else if (counter == 6) {
					app.botBox.getNextBox().getChildren().remove(nextButton);
				}

			});
			nextButton.setOnAction(endHandler);
			nextButton.setText("End");
			if(app.botBox.getNextBox().getChildren().size() == 0) {
				app.botBox.getNextBox().getChildren().add(nextButton);
			}
		}
	}//finishTurn
	
	/**
	 * Resizes the dominoes on the board if they have become too big for the pane to contain.
	 */
	private void resizeBoard() {
		double width = app.centerPane.getWidth();
		double height = app.centerPane.getHeight();
		if(width > 1145 || height > 668) {
			System.out.println("Resizing...");
			app.centerPane.reduceSizes(0.80);
			app.centerPane.setPrefWidth(1144);
			app.centerPane.setPrefHeight(667);			
		}
	}

	/**
	 * Takes an integer and rounds it to the nearest multiple of five.
	 * @param num The integer to round.
	 * @return The rounded integer.
	 */
	private int roundByFives(int num) {
		int modFive = num % 5;

		if(modFive < 2.5) {
			return num - modFive;
		} else {
			return num + 5 - modFive;
		}
	}//roundByFives

	/**
	 * Returns true if any player has zero dominoes in their hand.
	 * @return True if the game is over, false otherwise
	 */
	private boolean isGameOver() {
		boolean isGameOver = false;

		for(Player p : players) {
			if(p.getHandSize() < 1) {
				isGameOver = true;
			}
		}
		return isGameOver;
	}//isGameOver

	/**
	 * Sets the value for autoComputerTurns. If true, computer turns happen every 2 seconds. If false, player manually clicks through them.
	 * @param value True for auto, false for manual.
	 */
	public void setAutoComputerTurns(Boolean value) {
		autoComputerTurns = value;
	}//setAutoComputerTurns

}//Game class
