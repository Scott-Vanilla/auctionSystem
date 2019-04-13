//PlaceBid && SHOW USER AUCTIONS ACTIVE && SHOW USERS PREVIOUS AUCTIONS

package auctionSystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import entryLevel.Sys;

/**
 * Auction Object.
 * 
 * <p>
 * Auction used by the system to sell and buy items according to the user.
 * 
 * @author CMPSHUG1 & CMPJMCGU
 * @version 1.0
 *
 */

public class Auction implements Blockable {

	/*
	 * ENUMERATIONS O - Open C - Closed P - Pending B - Blocked
	 */

	private double startPrice;
	private double reservePrice;
	private Bid currentBid;
	private LocalDateTime date;
	private Status status;
	private LocalDateTime closingDate;
	private Item item;
	private User auctionOwner;
	private static Scanner S = new Scanner(System.in);
	private Bid defaultBid = new Bid(null, 0, null);
	private static List<Bid> bids = new LinkedList<Bid>();

	// TO DO: CREATE MULTIPLE CONSTRUCTORS FOR USABILITY. - sMac << COMPLETED >>

	/**
	 * Constructor.
	 * 
	 * @param startPrice
	 *            The start price of the auction.
	 * @param reservePrice
	 *            The reserve price for the auction.
	 * @param days
	 *            The number of days the auction object is existing.
	 * @param item
	 *            The item being displayed for sale in the auction.
	 */
	public Auction(double startPrice, double reservePrice, int days, Item item) {

		setStartPrice(startPrice);
		setReservePrice(reservePrice);
		setDate(LocalDateTime.now());
		setStatus(Status.P);
		setClosingDate(LocalDateTime.now().plusDays(days));
		setItem(item);
		setCurrentBid(defaultBid);
		setAuctionOwner(Sys.getCurrentUser());

	}

	/**
	 * Constructor.
	 * 
	 * @param startPrice
	 *            The start price of the auction.
	 * @param reservePrice
	 *            The reserve price for the auction.
	 * @param closingDate
	 *            The day the auction object is set to close.
	 * @param item
	 *            The item being displayed for sale in the auction.
	 * @param status
	 *            The status displayed explaining the state in which the auction
	 *            object is current at.
	 */
	public Auction(double startPrice, double reservePrice, LocalDateTime closingDate, Item item, Status status) {

		setStartPrice(startPrice);
		setReservePrice(reservePrice);
		setDate(LocalDateTime.now());
		setStatus(status);
		setClosingDate(closingDate);
		setItem(item);
		setCurrentBid(defaultBid);
		setAuctionOwner(new Seller("admin1", "731001001", false));

	}

	/**
	 * Constructor.
	 * 
	 * @param startPrice
	 *            The start price of the auction.
	 * @param days
	 *            The number of days the auction object is existing.
	 * @param item
	 *            The item being displayed for sale in the auction.
	 */
	public Auction(double startPrice, int days, Item item) {

		setStartPrice(startPrice);
		setReservePrice(startPrice);
		setDate(LocalDateTime.now());
		setStatus(Status.P);
		setClosingDate(LocalDateTime.now().plusDays(days));
		setItem(item);
		setCurrentBid(defaultBid);
		setAuctionOwner(Sys.getCurrentUser());

	}

	/**
	 * Constructor.
	 * 
	 * <p>
	 * When no parameters are entered when constructing an error is excecuted.
	 */

	public Auction() {

		System.out.println("Please enter a valid start date, and if wanted a reserve price too.");

	}

	/**
	 * placeBid Method.
	 * 
	 * <p>
	 * Method places bid on an item in an auction.
	 * <p>
	 * Allows for start price to be set for open auctions and upper and lower
	 * increments to be placed.
	 * 
	 * @param currentAuction
	 * 
	 */
	public static void placeBid(Auction currentAuction) {
		double bidValueInput = 0;
		boolean biddingFlag = true;
		double upperInc = (currentAuction.currentBid.getAmount() * 1.20);
		double lowerInc = (currentAuction.currentBid.getAmount() * 1.10);

		while (biddingFlag == true) {

			if (currentAuction.getCurrentBid().getAmount() == 0) {
				System.out.println(
						String.format("The start price for this item is: £%.2f ", currentAuction.getStartPrice()));
				System.out.println("Would you like to bid this amount? [Y|N] ");
				boolean flag = true;
				do {
					char response = S.next().toUpperCase().charAt(0);
					switch (response) {
					case 'Y':
						if (currentAuction.status == Status.O) {
							bidValueInput = currentAuction.startPrice;
						} else {

							System.err.println("The auction has closed or been blocked.");

						}
						flag = false;
						break;
					case 'N':
						biddingFlag = false;
						flag = false;
						break;
					default:
						System.err.println("Please enter [Y|N].");
					}
				} while (flag == true);
			} else {
				System.out.println("Please choose your bid: ");
				System.out.println(String.format("[U]pper bid: £%.2f ", upperInc));
				System.out.println(String.format("[L]ower bid: £%.2f ", lowerInc));
				System.out.println("[S]pecify value");
				System.out.println("[C]ancel");
				boolean flag = true;
				do {
					char response = S.next().toUpperCase().charAt(0);
					switch (response) {
					case 'U':
						if (currentAuction.status == Status.O) {
							bidValueInput = upperInc;
						} else {

							System.err.println("The auction has closed or been blocked.");

						}

						flag = false;
						break;
					case 'L':
						if (currentAuction.status == Status.O) {
							bidValueInput = lowerInc;
						} else {

							System.err.println("The auction has closed or been blocked.");

						}
						flag = false;
						break;

					case 'S':
						boolean isIncrementRespected = false;

						do {
							System.out.println(
									String.format("Please specify value between £%.2f and £%.2f", lowerInc, upperInc));
							double userValue = S.nextDouble();
							if (lowerInc <= userValue && userValue <= upperInc) {
								if (currentAuction.status == Status.O) {
									bidValueInput = userValue;
									isIncrementRespected = true;
								} else {

									System.err.println("The auction has closed or been blocked.");
									flag = false;
									break;

								}
							}
						} while (isIncrementRespected == false);

						flag = false;
						break;

					case 'C':
						biddingFlag = false;
						flag = false;
						break;
					default:
						System.err.println("Please enter [U|L|C].");
					}
				} while (flag == true);

			}

			if (biddingFlag == true) {

				Bid bidA = new Bid(Sys.getCurrentUser(), bidValueInput, currentAuction);
				currentAuction.setCurrentBid(bidA);
				bids.add(bidA);
				System.out.println(String.format(
						"\n  -- Congrats! --\n You have placed a bid on: '%s'\n Amount: £%.2f\n On: %s \n",
						currentAuction.getItem().getDescription(), bidA.getAmount(),
						LocalDateTime.now().format(DateTimeFormatter.ofPattern("d/MMM/uuuu HH:mm"))));
				biddingFlag = false;

			}
		}

	}

	/**
	 * close Method.
	 * 
	 * <p>
	 * Method closes an auction if the auction is open and reached its closing
	 * date.
	 * 
	 * @see closeCheck
	 * 
	 */

	public synchronized static void close() {
		int i = -1;

		Comparator<LocalDateTime> dateTimeComparitorLambda = (a, b) -> a.compareTo(b);
		int lambdaComparison;

		for (Auction a : Sys.getAuctions()) {
			i++;
			lambdaComparison = dateTimeComparitorLambda.compare(LocalDateTime.now(),
					(Sys.getAuctions().get(i).getClosingDate()));
			if ((lambdaComparison >= 0) && (Sys.getAuctions().get(i).getStatus() != Status.C)) {
				Sys.getAuctions().get(i).setStatus(Status.C);
				System.err.println(String.format("AUCTION CLOSED!!\n	Auction: %s\n	Winning Bid: £%.2f",
						a.getItem().getDescription(), a.getCurrentBid().getAmount()));
				try {
					if ((a.getReservePrice()) <= (a.getCurrentBid().getAmount())) {
						((Buyer) a.getCurrentBid().getWho()).victory();
					} else {

						System.err.println("Reserve Price Not Met - No Winner!");

					}
				} catch (Exception e) {

					System.err.println("Auction Close Error.");
				}
			}
		}
	}

	@Override
	public boolean isBlocked() {

		return (Status.B) == status;

	}

	@Override
	public void setBlocked() {

		setStatus(Status.B);

	}

	@Override
	public void setUnBlocked() {
		setStatus(Status.O);

	}

	public static List<Bid> getBids() {
		return bids;
	}

	public double getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(double startPrice) {
		this.startPrice = startPrice;
	}

	public double getReservePrice() {
		return reservePrice;
	}

	public void setReservePrice(double reservePrice) {
		this.reservePrice = reservePrice;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDateTime getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(LocalDateTime closingDate) {
		this.closingDate = closingDate;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Bid getCurrentBid() {
		return currentBid;
	}

	public void setCurrentBid(Bid currentBid) {
		this.currentBid = currentBid;
	}

	public User getAuctionOwner() {
		return auctionOwner;
	}

	public void setAuctionOwner(User user) {
		this.auctionOwner = user;
	}

	@Override
	public String toString() {

		String returningString = String.format("\n Start Price: £%.2f \n Closing Date: "
				+ closingDate.format(DateTimeFormatter.ofPattern("d/MMM/uuuu HH:mm")), startPrice);

		return (returningString);

	}

	public String toFullString() {

		String returningString = String.format(
				"\n Start Price: £%.2f \n Reserve Price: £%.2f \n Closing Date: "
						+ closingDate.format(DateTimeFormatter.ofPattern("d/MMM/uuuu HH:mm")),
				startPrice, reservePrice);

		return (returningString);

	}

	public String toUsernameString() {

		return (auctionOwner.getUsername());

	}

}
