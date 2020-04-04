package dominoes;

/**
 * Contains move information for AI logic.  Components are Domino and String (right, left, top, bot).
 * @author carroll
 *
 */
public class Move {

	private Domino d;
	private String arm;//valid values are top, left, right, bot

	/**
	 * Empty constructor for move objects.
	 */
	public Move() {
		d = null;
		arm = null;
	}//empty constructor

	/**
	 * Default constructor for Move objects.
	 * @param d The domino to play in the Move.
	 * @param arm The arm on which to play the domino.
	 */
	public Move(Domino d, String arm) {
		this.d = d;
		this.arm = arm;
	}//constructor

	/**
	 * Prints the move contents to standard output.
	 */
	public void print() {
		if(d != null && arm != null) {
			System.out.println("placed domino " + d.getAsString() + " on the " + arm + " arm");
		} else {
			System.out.println("passed turn");
		}
	}//print

	/**
	 * Returns the move contents as a string.
	 */
	public String toString() {
		if(d != null && arm != null) {
			return("placed domino " + d.getAsString() + " on the " + arm + " arm");
		} else {
			return("passed turn");
		}
	}//toString

	public Domino getDomino() {
		return d;
	}

	public String getArm() {
		return arm;
	}

	/**
	 * Returns the resulting score if the move is made.
	 * @return The score of the move.
	 */
	public int getScore(CenterPane cp) {
		int score = 0;
		int oldValue = 0;
		int newValue = 0;
		Domino old;
		boolean subtractOld = true;
		
		switch(arm) {
		case "right":
			old = cp.getRightMost();	

			if(old.isDouble()) {
				oldValue = old.getValue();
			} else {
				oldValue = old.getSide2();
			}
			
			if (d.isDouble()) {
				newValue = d.getValue();
			} else {
				if(d.getSide2() == old.getSide2()) {
					newValue = d.getSide1();
				} else {
					newValue = d.getSide2();
				}
			}
			
			//if there is no left arm yet, dont subtract the spinner
			if(cp.getSpinner() != null && cp.getLeftArm().size() == 0) {
				subtractOld = false;
			}
			break;
		case "left":
			old = cp.getLeftMost();
					
			if(old.isDouble()) {
				oldValue = old.getValue();
			} else {
				oldValue = old.getSide1();
			}
			
			if (d.isDouble()) {
				newValue = d.getValue();
			} else {
				if(d.getSide1() == old.getSide1()) {
					newValue = d.getSide2();
				} else {
					newValue = d.getSide1();
				}
			}
			
			//if there is no right arm yet, dont subtract the spinner
			if(cp.getSpinner() != null && cp.getRightArm().size() == 0) {
				subtractOld = false;
			}
			break;
		case "top":
			old = cp.getTopMost();

			if(old.isDouble()) {
				oldValue = old.getValue();
			} else {
				oldValue = old.getSide1();
			}
			
			if (d.isDouble()) {
				newValue = d.getValue();
			} else {
				if(d.getSide2() == old.getSide2()) {
					newValue = d.getSide1();
				} else {
					newValue = d.getSide2();
				}
			}
			
			//if this is the first top arm played, dont subtract the spinner
			if(cp.getTopArm().size() == 0) {
				subtractOld = false;
			}
			break;
		case "bot":
			old = cp.getBotMost();
		
			if(old.isDouble()) {
				oldValue = old.getValue();
			} else {
				oldValue = old.getSide2();
			}
			
			if (d.isDouble()) {
				newValue = d.getValue();
			} else {
				if(d.getSide2() == old.getSide2()) {
					newValue = d.getSide1();
				} else {
					newValue = d.getSide2();
				}
			}
			
			//if this is the first bot arm played, dont subtract the spinner
			if(cp.getBotArm().size() == 0) {
				subtractOld = false;
			}
			break;
		case "pass":
		default:
			score = 0;
			break;
		}
		
		if(subtractOld) {
			score = cp.getScore() - oldValue + newValue;
		} else {
			score = cp.getScore() + newValue;
		}
		
		return score;
	}//getScore

}//Move class

