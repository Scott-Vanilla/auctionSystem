package auctionSystem;

import java.io.Serializable;

/**
 * Admin Object.
 * 
 * <p>
 * Admin used by the system to block and unblock users and auctions.
 * 
 * @author CMPSHUG1 & CMPJMCGU
 * @version 1.0
 *
 */
public class Admin extends User implements Serializable {

	/**
	 * Constructor.
	 * 
	 * @param username
	 *            The username of the admin object.
	 * @param password
	 *            The password of the admin object.
	 */
	public Admin(String username, String password) {
		super(username, password);
	}

}
