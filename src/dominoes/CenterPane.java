package dominoes;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 
 * @author carroll
 *
 */
public class CenterPane extends GridPane {

	private HBox right, left, center;
	private VBox top, bot;

	private List<Domino> played;
	private List<Domino> rightArm, leftArm, topArm, botArm; //if first domino is not a double, rightArm is the initial arm.
	private Domino spinner;

	/**
	 * Default constructor for CenterPane objects. Creates each of the border boxes with proper alignments set.
	 */
	public CenterPane() {
		played = new ArrayList<>();

		left = new HBox();
		right = new HBox();

		top = new VBox();
		center = new HBox();
		bot = new VBox();	

		this.add(top, 1, 0);
		this.add(center, 1, 1);
		this.add(left, 0, 1);
		this.add(bot, 1, 2);
		this.add(right, 2, 1);

		this.setAlignment(Pos.CENTER);
		center.setAlignment(Pos.CENTER);
		right.setAlignment(Pos.CENTER);
		left.setAlignment(Pos.CENTER);
		top.setAlignment(Pos.CENTER);
		bot.setAlignment(Pos.CENTER);

		//		this.setStyle("-fx-border-style: solid inside;" + 
		//				"-fx-border-width: 2;" +
		//				"-fx-border-radius: 5;" + 
		//				"-fx-border-color: green;");
		//		left.setStyle("-fx-border-style: solid inside;" + 
		//				"-fx-border-width: 2;" +
		//				"-fx-border-radius: 5;" + 
		//				"-fx-border-color: orange;");
		//		right.setStyle("-fx-border-style: solid inside;" + 
		//				"-fx-border-width: 2;" +
		//				"-fx-border-radius: 5;" + 
		//				"-fx-border-color: orange;");
		//		top.setStyle("-fx-border-style: solid inside;" + 
		//				"-fx-border-width: 2;" +
		//				"-fx-border-radius: 5;" + 
		//				"-fx-border-color: maroon;");
		//		bot.setStyle("-fx-border-style: solid inside;" + 
		//				"-fx-border-width: 2;" +
		//				"-fx-border-radius: 5;" + 
		//				"-fx-border-color: maroon;");
		//		center.setStyle("-fx-border-style: solid inside;" + 
		//				"-fx-border-width: 2;" +
		//				"-fx-border-radius: 5;" + 
		//				"-fx-border-color: teal;");

		rightArm = new ArrayList<>();
		leftArm = new ArrayList<>();
		topArm = new ArrayList<>();
		botArm = new ArrayList<>();
	}//constructor

	/**
	 * Makes an HBox object containing the specified domino.
	 * @param d The domino to encase in a box.
	 * @return The constructed HBox.
	 */
	public HBox dominoBox(Domino d) {
		HBox box = new HBox();
		box.setPadding(new Insets(0,0,0,0));
		box.setSpacing(0);

		box.setStyle("-fx-border-style: solid inside;" + 
				"-fx-border-width: 2;" +
				"-fx-border-radius: 5;" + 
				"-fx-border-color: green;");

		if(!d.isDouble()) {
			d.makeHorizontal();
		}

		box.getChildren().add(d.getImageView());
		box.setMaxWidth(d.getImageView().getFitWidth());     
		box.setMaxHeight(d.getImageView().getFitHeight()); 		

		return box;
	}//dominoBox


	/**
	 * Makes an HBox object containing the specified domino, specifically for Dominoes to be played on the Top or Bot arms.
	 * @param d The domino to encase in a box.
	 * @return The constructed HBox.
	 */
	public HBox topBotDominoBox(Domino d) {
		HBox box = new HBox();
		box.setPadding(new Insets(0,0,0,0));
		box.setSpacing(0);

		box.setStyle("-fx-border-style: solid inside;" + 
				"-fx-border-width: 2;" +
				"-fx-border-radius: 5;" + 
				"-fx-border-color: green;");

		//TODO this won't be correct for T/B axis dominoes, must fix this.
		if(d.isDouble()) {
			d.makeHorizontal();
		}

		box.getChildren().add(d.getImageView());
		box.setMaxWidth(d.getImageView().getFitWidth());     
		box.setMaxHeight(d.getImageView().getFitHeight()); 		

		return box;
	}//dominoBox

	/**
	 * Plays the first domino and displays it in the center game pane.
	 * @param d The domino to play.
	 */
	public void playInitial(Domino d) {
		HBox box = dominoBox(d);
		center.getChildren().add(box);

		if(d.isDouble()) {
			spinner = d;
		} else {
			rightArm.add(d);
		}

		played.add(d);
	}//playInitial

	/**
	 * Plays a domino on the right arm of the center game pane.
	 * @param d The domino to play.
	 */
	public void playRight(Domino d) {		
		HBox box = dominoBox(d);
		removeBorders();

		if (getRightMost().getSide2() == d.getSide2()) {
			d.flip();
		}

		//if this needs to become the new spinner
		if(spinner == null && d.isDouble()) {
			spinner = d;	

			Node centerDomBox = center.getChildren().get(0);
			center.getChildren().remove(0);
			left.getChildren().add(centerDomBox);

			Domino centerDom = played.get(0);
			leftArm.add(centerDom);

			int loops = right.getChildren().size();
			for(int i = 0; i < loops; i++) {
				Node n = right.getChildren().get(0);
				right.getChildren().remove(0);
				left.getChildren().add(n);

				Domino dom = rightArm.get(0);
				rightArm.remove(0);
				leftArm.add(dom);
			}

			rightArm = new ArrayList<>();

			center.getChildren().add(box);
		} else {
			rightArm.add(d);
			right.getChildren().add(box);
		}
		played.add(d);	
	}//playRight

	/**
	 * Plays a domino on the left arm of the center game pane.
	 * @param d The domino to play.
	 */
	public void playLeft(Domino d) {		
		HBox box = dominoBox(d);
		removeBorders();

		if (getLeftMost().getSide1() == d.getSide1()) {
			d.flip();
		}

		//if this needs to become the new spinner
		if(spinner == null && d.isDouble()) {
			spinner = d;

			Node centerDomBox = center.getChildren().get(0);
			center.getChildren().remove(0);
			right.getChildren().add(0, centerDomBox);

			Domino centerDom = played.get(0);
			rightArm.add(0, centerDom);

			int loops = left.getChildren().size() - 1;
			for(int i = loops; i >= 0; i--) {
				Node n = left.getChildren().get(i);
				left.getChildren().remove(i);
				right.getChildren().add(0, n);

				Domino dom = leftArm.get(i);
				leftArm.remove(i);
				rightArm.add(0, dom);
			}

			leftArm = new ArrayList<>();

			center.getChildren().add(box);
		} else {
			left.getChildren().add(0,box);
			leftArm.add(0,d);
		}
		played.add(d);
	}//playLeft

	/**
	 * Plays a domino on the top arm of the center game pane.
	 * @param d The domino to play.
	 */
	public void playTop(Domino d) {
		HBox box = topBotDominoBox(d);
		removeBorders();

		if (getTopMost().getSide1() == d.getSide1()) {
			d.flip();
		}

		top.getChildren().add(0, box);
		topArm.add(d);
		played.add(d);
	}//playTop

	/**
	 * Plays a domino on the bottom arm of the center game pane.
	 * @param d The domino to play.
	 */
	public void playBot(Domino d) {
		HBox box = topBotDominoBox(d);
		removeBorders();

		if (getBotMost().getSide2() == d.getSide2()) {
			d.flip();
		}

		bot.getChildren().add(box);
		botArm.add(d);
		played.add(d);
	}//playBot

	/**
	 * Removes the red borders from all played dominoes, creating an effect that makes only the last domino played be outlined. 
	 */
	private void removeBorders() {
		for(Node n : right.getChildren()) {
			n.setStyle("");
		}
		for(Node n : left.getChildren()) {
			n.setStyle("");
		}
		for(Node n : top.getChildren()) {
			n.setStyle("");
		}
		for(Node n : bot.getChildren()) {
			n.setStyle("");
		}
		for(Node n : center.getChildren()) {
			n.setStyle("");
		}
		if(spinner != null) {
			center.getChildren().get(0).setStyle(
					"-fx-border-style: solid inside;" + 
					"-fx-border-width: 2;" +
					"-fx-border-radius: 5;" + 
					"-fx-border-color: gray;");
		}
	}//removeBorders

	/**
	 * Returns the list of played dominoes.
	 */
	public List<Domino> getPlayed() {
		return played;
	}//getPlayed

	/**
	 * Returns the spinner.
	 * @return The spinner if it exists, else null.
	 */
	public Domino getSpinner() {
		return spinner;
	}//getSpinner

	/**
	 * Returns the right-most domino.
	 * @return The right-most domino.
	 */
	public Domino getRightMost(){
		if(rightArm.size() > 0) {
			return rightArm.get(rightArm.size() - 1);
		} else if (spinner != null) {
			return spinner;
		} else {
			return null;
		}
	}//getRightMost

	/**
	 * Returns the left-most domino.
	 * @return The left-most domino.
	 */
	public Domino getLeftMost(){
		if(leftArm.size() > 0) {
			return leftArm.get(0);
		} else if (spinner != null) {
			return spinner;
		} else if(rightArm.size() > 0){
			return rightArm.get(0);
		} else {
			return null;
		}	
	}//getLeftMost

	/**
	 * Returns the top-most domino.
	 * @return The top-most domino.
	 */
	public Domino getTopMost() {
		if(topArm.size() == 0) {
			return spinner;
		} else {
			return topArm.get(topArm.size() - 1);
		}
	}//getTopMost

	/**
	 * Returns the bottom-most domino.
	 * @return The bottom-most domino.
	 */
	public Domino getBotMost() {
		if(botArm.size() == 0) {
			return spinner;
		} else {
			return botArm.get(botArm.size() - 1);
		}
	}//getBotMost

	/**
	 * Returns the score of the current board.
	 * @return The current score.
	 */
	public int getScore() {
		int score = 0;

		//special case no dominoes played
		if(played.size() == 0) {
			score = 0;
		} else {
			//special case for only one domino played
			if (played.size() == 1) {
				score += played.get(0).getValue();
			} 
			//if multiple dominoes have been added
			else {
				//top arm score
				if(topArm.size() > 0) {
					Domino topMost = getTopMost();
					if(topMost.isDouble()) {
						score += topMost.getValue();
					} else {
						score += topMost.getSide1();
					}
				}
				//bot arm score
				if(botArm.size() > 0) {
					Domino botMost = getBotMost();
					if(botMost.isDouble()) {
						score += botMost.getValue();
					} else {
						score += botMost.getSide2();
					}
				}
				//right arm score
				Domino rightMost = getRightMost();
				if(rightMost.isDouble()) {
					score += rightMost.getValue();
				} else {
					score += rightMost.getSide2();
				}
				//left arm score
				Domino leftMost = getLeftMost();
				if(leftMost.isDouble()) {
					score += leftMost.getValue();
				} else {
					score += leftMost.getSide1();
				}
			}//else not one domino
		}//else not zero dominoes
		return score;
	}//getScore

	/**
	 * Returns a score value if and only if that value is a multiple of five.
	 * @return A multiple of 5 score value, or zero otherwise.
	 */
	public int getFilteredScore() {

		int score = getScore();

		if(score %5 == 0) {
			return score;
		} else {
			return 0;
		}
	}//getFilteredScore

	/**
	 * Returns the right arm object.
	 * @return The right arm object.
	 */
	public List<Domino> getRightArm(){
		return rightArm;
	}//getRightArm

	/**
	 * Returns the left arm object.
	 * @return The left arm object.
	 */
	public List<Domino> getLeftArm(){
		return leftArm;
	}//getLeftArm

	/**
	 * Returns the top arm object.
	 * @return The top arm object.
	 */
	public List<Domino> getTopArm(){
		return topArm;
	}//getTopArm

	/**
	 * Returns the bot arm object.
	 * @return The bot arm object.
	 */
	public List<Domino> getBotArm(){
		return botArm;
	}//getBotArm

	/**
	 * Reduces the size of all played domino ImageViews to a given percentage.
	 * @param pct The percent to reduce the image sizes by.
	 */
	public void reduceSizes(double pct) {
		if(played.size() > 0) {
			Domino first = played.get(0);
			if(first.getImageView().getFitHeight() == 0) {
				first.getImageView().setFitHeight(first.getImageView().getImage().getHeight());
				first.getImageView().setFitWidth(first.getImageView().getImage().getWidth());
			}

			double height = first.getImageView().getFitHeight();
			double width = first.getImageView().getFitWidth();
			double newHeight = pct * height;
			double newWidth = pct * width;

			for(Domino d : played) {
				if(d.isVertical() == first.isVertical()) {
					d.getImageView().setFitHeight(newHeight);
					d.getImageView().setFitWidth(newWidth);
				} else {
					d.getImageView().setFitHeight(newWidth);
					d.getImageView().setFitWidth(newHeight);
				}
			}
		}
	}//reduceSizes

}//CenterPane class
