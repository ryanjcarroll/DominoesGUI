package dominoes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a full set of 28 Domino objects for a standard dominoes game.
 * @author carroll
 *
 */
public class Set {

	private List<Domino> pack;
		
	/**
	 * Default constructor for a set of 28 dominoes. 
	 */
	public Set() {
		this.pack = new ArrayList<Domino>();
		
		//Loop creates the set of dominoes
		for(int i = 0 ; i < 7; i++) {
			for(int j = i; j < 7; j++) {
				Domino dom = new Domino(i,j);
				pack.add(dom);
			}
		}
	}//default constructor
	
	/**
	 * Shuffles the set of dominoes.
	 */
	public void shuffle() {
		Collections.shuffle(pack);
	}//shuffle
	
	/**
	 * Deals a number of dominoes to the specified player.
	 * @param p The player to deal to.
	 * @param num The number of dominoes to deal.
	 */
	public void dealToPlayer(Player p, int num) {
		for(int i = 0; i < num; i++) {
			dealToPlayer(p);
		}
	}//dealToPlayer (multiple)
	
	/**
	 * Deals a single domino to a player.
	 * @param p The player to deal to.
	 */
	public void dealToPlayer(Player p) {
		Domino d = pack.get(0);
		pack.remove(0);
		p.addToHand(d);
	}//dealToPlayer (single)
	
}//Set class
