package auctionSystem;

/**
 * Interface
 * 
 * Blockable interface that contains method signatures implemented in Auction
 * and Seller classes.
 * 
 */
public interface Blockable {
	public boolean isBlocked();

	public void setBlocked();

	public void setUnBlocked();
}
