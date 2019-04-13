// VICTORY LEFT TO DO

package auctionSystem;

import java.io.Serializable;


/**
 * 
 * Buyer Object.
 * 
 * <p>
 * The buyer object is a type of user and is used when buying items.
 * 
 * @author CMPSHUG1 & CMPJMCGU
 * @version 1.0
 */
public class Buyer extends User implements Serializable {

	public Buyer(String username, String password) {
		super(username, password);
	}

	/**
	 * victory Method.
	 * <p>
	 * Alerts the winning buyer of a victory.
	 */

	public void victory() {

		System.err.println(String.format("CONGRATULATIONS! YOU WON AN AUCTION!"));

	}

}
