//DONE

package auctionSystem;

import java.time.LocalDateTime;

/**
 * Bid Object.
 * 
 * <p>
 * Bid used by the system to bid on a particular item and returns the date and
 * time the bid was made.
 * 
 * @author CMPSHUG1 & CMPJMCGU
 * @version 1.0
 *
 */
public class Bid {

	private User who;
	private LocalDateTime when;
	private double amount;
	private Auction biddedAuction;

	/**
	 * Constructor.
	 * 
	 * @param who
	 *            The name of the user placing the bid.
	 * @param when
	 *            The time the bid was placed.
	 * @param amount
	 *            The amount that was bidded on an item.
	 * @param biddedAuction
	 *            The auction of which the bid has been placed upon.
	 */
	public Bid(User who, LocalDateTime when, double amount, Auction biddedAuction) {

		setWho(who);
		setWhen(when);
		setAmount(amount);
		setBiddedAuction(biddedAuction);

	}

	/**
	 * Constructor.
	 * 
	 * @param who
	 *            The name of the user placing the bid.
	 * @param amount
	 *            The amount that was bidded on an item.
	 * @param biddedAuction
	 *            The auction of which the bid has been placed upon.
	 */
	public Bid(User who, double amount, Auction biddedAuction) {

		setWho(who);
		setWhen(LocalDateTime.now());
		setAmount(amount);
		setBiddedAuction(biddedAuction);

	}

	public void setWho(User who) {

		this.who = who;

	}

	public User getWho() {

		return who;

	}

	public void setWhen(LocalDateTime when) {

		this.when = when;

	}

	public LocalDateTime getWhen() {

		return when;

	}

	public void setAmount(double amount) {

		this.amount = amount;

	}

	public double getAmount() {

		return amount;

	}

	public Auction getBiddedAuction() {
		return biddedAuction;
	}

	public void setBiddedAuction(Auction biddedAuction) {
		this.biddedAuction = biddedAuction;
	}

	public String toBidString() {

		return (who.getUsername());
	}

}
