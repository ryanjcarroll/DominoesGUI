package dominoes;

import javafx.scene.image.ImageView;

/**
 * A class to represent a single domino.
 * @author carroll
 *
 */
public class Domino {
	
	//For non-pair dominos, side 1 will always be the left or bottom side, while side2 is the right or top side.
	private int side1, side2;
	private ImageView iView;
	private boolean clickable;
	private boolean vertical;
	
	/**
	 * Valued constructor for Domino objects with integer inputs.
	 * @param side1 The value for the first side of the domino.
	 * @param side2 The value for the second side of the domino.
	 */
	public Domino(int side1, int side2) {
		this.side1 = side1;
		this.side2 = side2;
		iView = new ImageView("images/" + this.getAsString() + ".png");
		iView.setPreserveRatio(true);
		clickable = false;
		vertical = true;
	}//main constructor
	
	/**
	 * Gets the value of side 1 of the domino, the left or bottom side.
	 * @return Side 1 integer value.
	 */
	public int getSide1() {
		return side1;
	}//getSide1
	
	/**
	 * Gets the value of side 2 of the domino, the right or top side.
	 * @return Side 2 integer value.
	 */
	public int getSide2() {
		return side2;
	}//getSide2
	
	/**
	 * Gets the combined value of both sides of the domino.
	 * @return Combined integer value of side 1 and side 2.
	 */
	public int getValue() {
		return side1 + side2;
	}//getValue
	

	/**
	 * Determines if a domino is a double, having two equal numbered sides.
	 * @return True if domino is a double.
	 */
	public boolean isDouble() {
		return (side1 == side2) ;
	}//isDouble
	
	/**
	 * Prints the domino value to standard output.
	 */
	public void print() {
		System.out.print(side1 + "-" + side2 + " ");
	}//print
	
	/**
	 * Exchanges the values of side1 and side2.
	 */
	public void flip() {
		int temp = side1;
		side1 = side2;
		side2 = temp;
		
		iView.setRotate(180);
	}//flip
	
	/**
	 * Determines if two dominoes are equal in type.
	 */
	public boolean isEqual(Domino other) {
		if(this.getSide1() == other.getSide1() && this.getSide2() == other.getSide2()) {
			return true;
		} else if(this.getSide1() == other.getSide2() && this.getSide2() == other.getSide1()) {
			return true;
		} else {
			return false;
		}
	}//isEqual
	
	/**
	 * Returns the domino as a string, formatted as "0-4", etc.
	 * @return The string value of the domino.
	 */
	public String getAsString() {
		String first = Integer.toString(side1);
		String second = Integer.toString(side2);
		
		return first + "-" + second;
	}//getAsString
	
	/**
	 * Returns the domino as a string, formatted as "0-4", etc, with the lowest number always coming first.
	 * @return The ordered string value of the domino.
	 */
	public String getAsOrderedString() {
		String first = Integer.toString(side1);
		String second = Integer.toString(side2);
		
		if(Integer.valueOf(first) <= Integer.valueOf(second)) {
			return first + "-" + second;
		} else {
			return second + "-" + first;
		}
	
	}//getAsOrderedString

	/**
	 * Gets the ImageView object associated with a domino, if it exists.
	 * @return The Imageview object.
	 */
	public ImageView getImageView() {
		return iView;
	}//getImageView
	
	/**
	 * Sets the ImageView object associated with a domino object.
	 * @param iView The imageView to set.
	 */
	public void setImageView(ImageView iView) {
		this.iView = iView;
	}//setImageView
	
	/**
	 * Rotates domino visually to a horizontal position.
	 */
	public void makeHorizontal() {
		this.setImageView(new ImageView("images/rotated/" + this.getAsOrderedString() + ".png"));
		vertical = false;
	}//makeHorizontal
	
	/**
	 * Rotates domino visually to a vertical position.
	 */
	public void makeVertical() {
		this.setImageView(new ImageView("images/" + this.getAsOrderedString() + ".png"));
		vertical = true;
	}//makeVertical
	
	/**
	 * Returns the value of the vertical property.
	 * @return True if domino is vertical, false otherwise.
	 */
	public boolean isVertical() {
		return vertical;
	}//isVertical
	
	/**
	 * Sets the clickable property.
	 * @param clickable The value to set.
	 */
	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}//setClickable
	
	/**
	 * Returns the value of the clickable property.
	 * @return True if clickable, false otherwise.
	 */
	public boolean isClickable() {
		return clickable;
	}//isClickable
	
}//Domino class
