package auctionSystem;

/**
 * 
 * Buyer Object.
 * 
 * <p>
 * The closedCheck class contains thread for checking if auction dates have
 * expired.
 * 
 * @author CMPSHUG1 & CMPJMCGU
 * @version 1.0
 */
public class closedCheck implements Runnable {

	private static boolean shouldCheck = true;

	/**
	 * 
	 * doit Method.
	 * 
	 * <p>
	 * Starts the thread to check expired auctions.
	 * 
	 * @author CMPSHUG1 & CMPJMCGU
	 * @version 1.0
	 */
	public static void doit() {

		new Thread(new closedCheck()).start();
	}

	@Override
	public void run() {

		while (shouldCheck == true) {
			try {
				Thread.sleep(4500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Auction.close();

		}
	}

	public boolean isShouldCheck() {
		return shouldCheck;
	}

	public static void setShouldCheck(boolean shouldCheck) {
		closedCheck.shouldCheck = shouldCheck;
	}

}
