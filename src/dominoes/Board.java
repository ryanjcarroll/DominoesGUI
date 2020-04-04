package dominoes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the board of played dominoes.
 * @author carroll
 *
 */
public class Board {

	private Domino spinner;
	private List<Domino> played;
	private List<Domino> leftArm, rightArm, topArm, botArm;
	
	/**
	 * Empty constructor for Board object. Has no spinner and no dominoes.
	 */
	public Board() {
		//the double domino which allows branching in top and bottom directions
		spinner = null;
		
		//list of all dominos played in order
		played = new ArrayList<Domino>();
		
		//linked list of each arm
		leftArm = new ArrayList<Domino>();
		rightArm = new ArrayList<Domino>();
		botArm = new ArrayList<Domino>();
		topArm = new ArrayList<Domino>();
	}//empty constructor
		
		
	/**
	 * Gets the spinner, the central domino.
	 * @return The spinner domino, if it exists.
	 */
	public Domino getSpinner() {
		return spinner;
	}//getSpinner

	/**
	 * Returns the value of the current board state.
	 * @return The current count of all outer domino faces.
	 */
	public int getScore() {
		int score = 0;
		
		//special case for no dominoes yet played
		if(played.size() == 0) {
			score = 0;
		} else {
			//special case for when only one domino on the board
			if(played.size() == 1) {
				score += played.get(0).getValue();

			} else { //if multiple dominos have been added
				//top arm score
				if(topArm.size() > 1) {
					Domino top = topArm.get(topArm.size() - 1);
					if(top.isDouble()) {
						score += top.getValue();
					} else {
						score += top.getSide2();
					}
				}
				//bot arm score
				if(botArm.size() > 1) {
					Domino bot = botArm.get(botArm.size() - 1);
					if(bot.isDouble()) {
						score += bot.getValue();
					} else {
						score += bot.getSide1();
					}
				}
				//right arm score
				if(rightArm.size() > 1) {
					Domino right = rightArm.get(rightArm.size() - 1);
					if(right.isDouble()) {
						score += right.getValue();
					} else {
						score += right.getSide2();
					}
				} else {
					Domino right = rightArm.get(rightArm.size() - 1);
					score += right.getValue();
				}
				//left arm score
				if(leftArm.size() > 1) {
					Domino left = leftArm.get(leftArm.size() - 1);
					if(left.isDouble()) {
						score += left.getValue();
					} else {
						score += left.getSide1();
					}
				} else {
					Domino left = leftArm.get(leftArm.size() - 1);
					score += left.getValue();
				}
			}//else for number of dominos added
		}		
		return score;
	}//getScore
	
	/**
	 * Adds the first domino to the board.
	 * @param dom The first domino to add.
	 */
	public void addInitial(Domino dom) {
		rightArm.add(dom);
		leftArm.add(dom);
		
		if(dom.isDouble()) {
			spinner = dom;
			
			topArm.add(spinner);
			botArm.add(spinner);
		}
		played.add(dom);
	}//addInitial
	
	/**
	 * Adds a domino to the right arm.
	 * Alternatively, adds the initial domino.
	 * @param dom The domino to add to the right arm.
	 */
	public void addToRight(Domino dom) {
		if(dom.isDouble()) {
			//check if a spinner exists. If not, this is the new spinner.
			if(spinner == null) {
				spinner = dom;
				
				//combine the initial lists into a joint left arm, and set the right arm blank
				for(int i = 1; i < rightArm.size(); i++) {
					leftArm.add(0, rightArm.get(i));
				}
				leftArm.add(0, spinner);
				rightArm = new ArrayList<Domino>();		
				
				topArm.add(spinner);
				botArm.add(spinner);
			}
		}
		rightArm.add(dom);
		played.add(dom);
	}//addRight
	
	/**
	 * Adds a domino to the left arm.
	 * @param dom The domino to add to the left arm.
	 */
	public void addToLeft(Domino dom) {
		if(dom.isDouble()) {			
			//check if a spinner exists. If not, this is the new spinner.
			if(spinner == null) {
				spinner = dom;
				
				for(int i = 1; i < leftArm.size(); i++) {
					rightArm.add(0, leftArm.get(i));
				}
				rightArm.add(0, spinner);
				leftArm = new ArrayList<Domino>();
				
				topArm.add(spinner);
				botArm.add(spinner);
			}
		}
		played.add(dom);
		leftArm.add(dom);
	}//addLeft
	
	/**
	 * Adds a domino to the top arm.
	 * @param dom The domino to add to the top arm.
	 */
	public void addToTop(Domino dom)  {		
		topArm.add(dom);
		played.add(dom);
	}//addTop
	
	/**
	 * Adds a domino to the bottom arm.
	 * @param dom The domino to add to the bottom arm.
	 */
	public void addToBot(Domino dom) {	
		botArm.add(dom);
		played.add(dom);
	}//addBot
	
	/**
	 * Prints the current layout of the board object to standard output.
	 */
	public void print() {
		//heading info
		System.out.println("CURRENT BOARD:");
		System.out.println("Count: " + getScore());
	
		//print the top arm, adjusting left for each left arm domino.
		for(int i = topArm.size() - 1; i >= 1; i--) {
			System.out.println();
			for(int space = 0; space < (leftArm.size() - 1) * 4; space++) {
				System.out.print(" ");
			}
			topArm.get(i).print();
		}

		//print the horizontal middle arms, left then right
		System.out.println();
		for(int i = leftArm.size() - 1; i >= 1; i--) {
			leftArm.get(i).print();
		}
		for(Domino d : rightArm) {
			d.print();
		}

		//print the bottom arm, adjusting left for each left arm domino
		for(int i = 1; i < botArm.size(); i++) {
			System.out.println();
			for(int space = 0; space < (leftArm.size() - 1) * 4; space++) {
				System.out.print(" ");
			}
			botArm.get(i).print();
		}
		System.out.println();
	}//print
	
	/**
	 * Gets the right arm.
	 * @return The right arm.
	 */
	public List<Domino> getRightArm(){
		return rightArm;
	}//getRightArm
	
	/**
	 * Gets the left arm.
	 * @return The left arm.
	 */
	public List<Domino> getLeftArm(){
		return leftArm;
	}//getLeftArm
	
	/**
	 * Gets the top arm.
	 * @return The top arm.
	 */
	public List<Domino> getTopArm(){
		return topArm;
	}//getTopArm
	
	/**
	 * Gets the bot arm.
	 * @return The bot arm.
	 */
	public List<Domino> getBotArm(){
		return botArm;
	}//getBotArm
	
	/**
	 * Gets the list of played dominoes.
	 * @return The list of played dominoes
	 */
	public List<Domino> getPlayed(){
		return played;
	}//getPlayed
	
	/**
	 * Returns a string of all possible matching numbers which could be played on the current board.
	 * @return The numbers which are playable on the current board.
	 */
	public List<Integer> possiblePlays() {
		List<Integer> allNumbers = new ArrayList<>();
		int rightValue, leftValue, topValue, botValue;
		
		Domino rightDomino = rightArm.get(rightArm.size() - 1);
		Domino leftDomino = leftArm.get(leftArm.size() - 1);
		
		//get value on the right arm
		if(rightArm.size() > 1) {
			if(rightDomino.isDouble()) {
				rightValue = rightDomino.getSide2();
			} else {
				rightValue = rightDomino.getSide2();
			}
		} else {
			rightValue = rightDomino.getSide2();
		}
		
		//get value on the left arm
		if(leftArm.size() > 1) {
			if(leftDomino.isDouble()) {
				leftValue = leftDomino.getSide1();
			} else {
				leftValue = leftDomino.getSide1();
			}
		} else {
			leftValue = leftDomino.getSide1();
		}
		
		//if the spinner has been played on both sides, then consider the top and bot arms also
		if(topArm.size() > 0 && botArm.size() > 0) {
			Domino topDomino = topArm.get(topArm.size() - 1);
			Domino botDomino = botArm.get(botArm.size() - 1);
			
			//get value on the top arm
			//if the top arm is only the spinner, then no value is added
			if(topArm.size() > 1) {
				if(topDomino.isDouble()) {
					topValue = topDomino.getSide2();
				} else {
					topValue = topDomino.getSide2();
				}
			} else {
				topValue = -1; //marker to designate there is nowhere to play on this arm
			}
			//get value on the bot arm
			if(botArm.size() > 1) {
				if(botDomino.isDouble()) {
					botValue = botDomino.getSide1();
				} else {
					botValue = botDomino.getSide1();
				}
			} else {
				botValue = -1; //marker to designate there is nowhere to play on this arm
			}
		}//top and bot arms
		else {
			topValue = -1; //marker to designate there is nowhere to play on this arm
			botValue = -1; //same ^
		}

		//add all numbers to a list
		allNumbers.add(rightValue);
		allNumbers.add(leftValue);
		if(topValue != -1) {
			allNumbers.add(topValue);
		}
		if(botValue != -1) {
			allNumbers.add(botValue);
		}
		
		//filter list for distinct numbers only
		List<Integer> distinctNumbers = allNumbers.stream()
				.distinct()
				.collect(Collectors.toList());
		
		return distinctNumbers;
	}//possiblePlays

}//Board class
