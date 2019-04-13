//DONE

package auctionSystem;

import java.io.Serializable;

/**
 * 
 * User Object.
 * <p>
 * Contains username and password
 * 
 * Author - CMPJMCGU & CMPSHUG Version - 1.0
 */

public abstract class User implements Serializable {

	protected String username;
	protected String password;

	public User(String username, String password) {

		setUsername(username);
		setPassword(password);

	}

	public void setUsername(String username) {

		this.username = username;

	}

	public String getUsername() {

		return username;

	}

	public void setPassword(String password) {

		this.password = password;

	}

	public String getPassword() {

		return password;

	}

	public String toSaveBuyer() {
		return ("B " + getUsername() + " " + getPassword());

	}

	public String toSaveSeller(User u) {
		return ("S " + getUsername() + " " + getPassword() + " " + ((Seller) u).isBlocked());

	}

	public String toSaveAdmin(User u) {
		return ("A " + getUsername() + " " + getPassword());

	}
}
