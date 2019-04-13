// DONE

package auctionSystem;

import java.io.Serializable;

/**
 * 
 * Seller Object.
 * 
 * <p>
 * The seller object is a type of user and is used when selling items.
 * 
 * @author CMPSHUG1 & CMPJMCGU
 * @version 1.0
 */
public class Seller extends User implements Blockable, Serializable {

	private boolean isUserBlocked;

	public Seller(String username, String password, boolean isUserBlocked) {

		super(username, password);
		setUserBlocked(isUserBlocked);

	}

	/**
	 * isBlocked Method.
	 * <p>
	 * Holds a boolean value determining if the seller in question is blocked by
	 * the system.
	 * 
	 * @param user
	 * 
	 * @return boolean
	 */
	@Override
	public boolean isBlocked() {

		return isUserBlocked;

	}

	/**
	 * setBlocked Method.
	 * <p>
	 * Renders a seller as blocked.
	 * 
	 * @param boolean
	 *            isUserBlocked
	 * 
	 */
	@Override
	public void setBlocked() {

		setUserBlocked(true);

	}

	@Override
	public void setUnBlocked() {

		setUserBlocked(false);

	}

	public void setUserBlocked(boolean isUserBlocked) {
		this.isUserBlocked = isUserBlocked;
	}

}
