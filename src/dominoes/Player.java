package dominoes;
import java.util.List;
import java.util.ArrayList;

/**
 * Class to represent a player and their hand in the dominoes game.
 * @author carroll
 *
 */
public class Player {

	private App app;
	private String name;
	private List<Domino> hand;
	private int id;
	private boolean computer;
	
	/**
	 * Default constructor for player objects.
	 * @param id The player id, a number from 1 to 4.
	 * @param name The player's name.
	 */
	public Player(int id, String name, boolean computer, App app) {
		this.computer = computer;
		this.id = id;
		this.name = name;
		this.app = app;
		hand = new ArrayList<Domino>();
	}//constructor
	
	/**
	 * Gets the player's name.
	 * @return The player's name.
	 */
	public String getName() {
		return name;
	}//getName
	
	/**
	 * Get's the player's id.
	 * @return The player's id.
	 */
	public int getId() {
		return id;
	}//getId
	
	/**
	 * Gets the player's hand as a list of domino objects.
	 * @return The player's hand.
	 */
	public List<Domino> getHand(){
		return hand;
	}//getHand
	
	/**
	 * Gets the size of the player's hand as an integer.
	 * @return The hand size.
	 */
	public int getHandSize() {
		return hand.size();
	}//getHandSize
	
	/**
	 * Adds a domino to the player's hand.
	 * @param dom The domino to add.
	 */
	public void addToHand(Domino dom) {
		hand.add(dom);
	}//addToHand
	
	/**
	 * Removes a domino from the player's hand. 
	 * @param dom The domino to remove.
	 * @return True if a domino was removed from the hand.
	 */
	public boolean removeFromHand(Domino dom) {
		boolean removed = false;
		for(int i = 0; i < hand.size(); i++) {
			if(dom.isEqual(hand.get(i))) {
				hand.remove(i);
				removed = true;
			}
		}
		return removed;
	}//removeFromHand
	
	/**
	 * Gets the combined value of all dominoes in a player's hand.
	 * @return The player's hand value.
	 */
	public int valueOfHand() {
		int value = 0;
		
		for(Domino dom : hand) {
			value += dom.getValue();
		}
		
		return value;
	}//valueOfHand
	
	/**
	 * Prints the player object.
	 */
	public void print() {
		System.out.println("Id: " + id + ", Name: " + name);
	}//print
	
	/**
	 * Returns true if the player is a computer.
	 * @return True if computer, false if human player.
	 */
	public boolean isComputer() {
		return computer;
	}//isComputer
	
	/**
	 * Suggests a player's first possible move based on the current board. Easy difficulty.
	 * @return The suggested move.
	 */
	public Move suggestEasy() {
		Move move = new Move();
		List<Move> moves = new ArrayList<>();
		
		Domino rightMost = app.centerPane.getRightMost();
		Domino leftMost = app.centerPane.getLeftMost();
		Domino topMost = app.centerPane.getTopMost();
		Domino botMost = app.centerPane.getBotMost();
		Domino spinner = app.centerPane.getSpinner();
		
		int rightSide = rightMost.getSide2();
		int leftSide = leftMost.getSide1();
		
		List<Domino> rightArm = app.centerPane.getRightArm();
		List<Domino> leftArm = app.centerPane.getLeftArm();
		
		//if top and bot arms are playable, look at potential plays off those arms as well as right and left
		if(spinner != null && rightArm.size() > 0 && leftArm.size() > 0) {
			int topSide = topMost.getSide1();
			int botSide = botMost.getSide2();
			
			for(Domino d : hand) {
				if(d.getSide1() == topSide || d.getSide2() == topSide) {
					moves.add(new Move(d, "top"));
				}
				if(d.getSide1() == botSide || d.getSide2() == botSide) {
					moves.add(new Move(d, "bot"));
				}
				if(d.getSide1() == rightSide || d.getSide2() == rightSide) {
					moves.add(new Move(d, "right"));
				}
				if(d.getSide1() == leftSide || d.getSide2() == leftSide) {
					moves.add(new Move(d, "left"));
				}
			}//for
		}
		//if top and bot are not yet unlocked, look only at right and left arms
		else {
			for(Domino d : hand) {
				if(d.getSide1() == rightSide || d.getSide2() == rightSide) {
					moves.add(new Move(d, "right"));
				}
				if(d.getSide1() == leftSide || d.getSide2() == leftSide) {
					moves.add(new Move(d, "left"));
				}
			}//for
		}
		
		if(moves.size() > 0) {			
			//return moves.get(0);  //returns the first possible move
			
			//determine the highest scoring move in hand
			int bestScore = -1;
			for(Move m : moves) {
				int thisScore = m.getScore(app.centerPane);
			
				if(thisScore % 5 != 0) {
					thisScore = 0;
				}
				if(thisScore > bestScore) {
					bestScore = thisScore;
					move = m;
				}
			}
			return move;
		} else {
			return new Move(null, "pass");
		}
	}//suggestEasy
	
}//Player class
