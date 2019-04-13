//DONE

package auctionSystem;

/**
 * Item Object
 * 
 * Author - CMPJMCGU & CMPSHUG Version - 1.0
 */

public class Item {

	private String description;

	public Item(String description) {

		setDescription(description);

	}

	/**
	 * Constructor
	 * <p>
	 * Contains validation on item description
	 * 
	 * Author - CMPJMCGU & CMPSHUG Version - 1.0
	 */

	public Item() {

		System.err.println("Please re-enter the item description");

	}

	public void setDescription(String description) {

		this.description = description;

	}

	public String getDescription() {

		return description;

	}
}
