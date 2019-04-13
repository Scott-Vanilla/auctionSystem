//GET TO OTHER MENUS

package entryLevel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import auctionSystem.Admin;
import auctionSystem.Auction;
import auctionSystem.Buyer;
import auctionSystem.Item;
import auctionSystem.Seller;
import auctionSystem.Status;
import auctionSystem.User;
import auctionSystem.closedCheck;

/**
 * Sys Object.
 * 
 * <p>
 * System controls user flow.
 * 
 * @author CMPSHUG1 & CMPJMCGU
 * @version 1.0
 *
 */
public class Sys implements Serializable {

	private List<User> users = new LinkedList<>();
	private static List<Auction> auctions = new LinkedList<Auction>();
	private static Scanner S = new Scanner(System.in);

	// CHANGE TO FILE LOCATION ON PC -SMac
	private String userFileLocation = "M:\\eclipse-oxygen\\cw new now\\src\\auctionSystem\\users.ser";

	private Scanner textReader;

	private static User currentUser;

	// Constructor

	public Sys() {

		// DEBUGGING AUCTIONS

		auctions.add(new Auction(22.50, 26.00, LocalDateTime.now().plusSeconds(25), new Item("Potion of Minor Stamina"),
				Status.O));
		auctions.add(new Auction(21.50, 24.00, LocalDateTime.now().plusSeconds(60), new Item("Potion of Minor Health"),
				Status.O));
		auctions.add(new Auction(19.50, 19.50, LocalDateTime.now().plusSeconds(10), new Item("Potion of Minor Magicka"),
				Status.O));
		auctions.add(new Auction(135.00, 135.00, LocalDateTime.now().plusSeconds(120),
				new Item("Ascendant Daedric Warhammer (Legendary)"), Status.O));

		// USED FOR FIRST TIME SERIALIZATION

//		 users.add(new Seller("sanguine","rose", false));
//		 users.add(new Buyer ("dovahkiin","dragons"));
//		 users.add(new Admin("boethiah","god"));

		deserialize();
		generalUserMenu();
		closedCheck.doit();
	}

	/**
	 * serialize Method.
	 * 
	 * <p>
	 * Method saves users in user.ser file.
	 * 
	 * 
	 */
	public void serialize() throws IOException {

		try {

			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(userFileLocation));

			out.writeObject(users);

			out.close();
			System.out.printf("Serialized data is saved in users.ser");

		}

		catch (IOException i) {

			i.printStackTrace();
		}

	}

	/**
	 * deserialize Method.
	 * 
	 * <p>
	 * Method takes takes data from serialized file and changes it to array
	 * Object.
	 * 
	 * 
	 */
	public void deserialize() {
		try {

			ObjectInputStream in = new ObjectInputStream(new FileInputStream(userFileLocation));

			users = (List<User>) in.readObject();

			in.close();

		}

		catch (IOException i) {

			System.err.println("File is not found.");
			fileNotFound();

		}

		catch (ClassNotFoundException c) {

			System.out.println("User class not found");
			c.printStackTrace();
			return;
		}

	}

	/**
	 * fileNotFound Method.
	 * 
	 * <p>
	 * Allows for a new file path to be declared if current one isn't found.
	 * 
	 */
	public void fileNotFound() {
		// Protocol if the file is not found at path address.
		System.out.println("Do you want to:");
		System.out.println("N: Enter a new file address.");
		System.out.println("Q: Quit");
		String menuChoice = S.nextLine().toUpperCase();

		if ("N".equals(menuChoice)) {
			System.out.println("Please enter a new file address.");
			System.out.print("File Address: ");
			setUserFileLocation(S.nextLine());
			deserialize();
		} else if ("Q".equals(menuChoice)) {
			System.exit(0);
		}

		else {
			System.out.println("Please enter a valid choice.");
			fileNotFound();
		}
	}

	/**
	 * generalUserMenu Method.
	 * 
	 * <p>
	 * Creates menu options for user to choose from.
	 * 
	 */
	public void generalUserMenu() {

		boolean flag = false;

		while (flag == false) {

			System.out.println("-- Guest User Menu -- ");
			System.out.println("[B]rowse");
			System.out.println("[S]etup Account");
			System.out.println("[L]ogon");
			System.out.println("[E]xit");
			char menuChoice = S.next().toUpperCase().charAt(0);

			switch (menuChoice) {

			case 'B':
				browseAuction();
				break;
			case 'S':
				setupAccount();
				break;
			case 'L':
				logon();
				break;
			case 'E':
				System.out.println("Fare-thee-well Dragonborn!");
				closedCheck.setShouldCheck(false);
				try {
					serialize();
				} catch (IOException e) {
					e.printStackTrace();
				}
				flag = true;
				break;
			default:
				System.out.println("Please enter a valid option.");
			}

		}
	}

	/**
	 * buyerUserMenu Method.
	 * 
	 * <p>
	 * Creates options for a Buyer to choose from.
	 * 
	 */
	public void buyerUserMenu() {

		boolean flag = false;

		while (flag == false) {

			System.out.println("\n-- " + currentUser.getUsername() + "'s Buyer Menu -- ");
			System.out.println("[B]rowse");
			System.out.println("[M]y Bids");
			System.out.println("[A]uctions Won");
			System.out.println("[L]og Off");
			char menuChoice = S.next().toUpperCase().charAt(0);

			switch (menuChoice) {

			case 'B':
				browseAuction();
				break;
			case 'M':
				buyersBids();
				break;
			// case 'A' : wonAuctions();
			// break;
			case 'L':
				System.out.println("Signed Out!\n");
				setCurrentUser(null);
				flag = true;
				break;
			default:
				System.out.println("Please enter a valid option.");
			}

		}
	}

	/**
	 * sellerUserMenu Method.
	 * 
	 * <p>
	 * Creates options for a Seller to choose from.
	 * 
	 */
	public void sellerUserMenu() {

		boolean flag = false;

		while (flag == false) {

			System.out.println("\n-- " + currentUser.getUsername() + "'s Seller Menu -- ");
			System.out.println("[A]uction Creation");
			System.out.println("[V]erify An Auction");
			System.out.println("[L]og Off");
			char menuChoice = S.next().toUpperCase().charAt(0);

			switch (menuChoice) {

			case 'A':
				placeAuction();
				break;
			case 'V':
				unverifiedAuctions();
				break;
			case 'L':
				System.out.println("Signed Out!\n");
				setCurrentUser(null);
				flag = true;
				break;
			default:
				System.out.println("Please enter a valid option.");
			}

		}
	}

	/**
	 * adminUserMenu Method.
	 * 
	 * <p>
	 * Creates options for an Admin to choose from.
	 * 
	 */
	public void adminUserMenu() {

		boolean flag = false;

		while (flag == false) {

			System.out.println("\n-- " + currentUser.getUsername() + "'s Admin Menu -- ");
			System.out.println("[B]lock Seller");
			System.out.println("[U]nblock Seller");
			// System.out.println("[A]uction Block/Unblock");
			System.out.println("[L]og Off");
			char menuChoice = S.next().toUpperCase().charAt(0);

			switch (menuChoice) {

			case 'B':
				blockViaAdmin();
				break;
			case 'U':
				unblockViaAdmin();
				break;
			// case 'A' : blockAuctionViaAdmin();
			// break;
			case 'L':
				System.out.println("Signed Out!\n");
				setCurrentUser(null);
				flag = true;
				break;
			default:
				System.out.println("Please enter a valid option.");
			}

		}
	}

	/**
	 * placeAuction Method.
	 * 
	 * <p>
	 * Allows for a new auction to be created by a Seller.
	 * 
	 */
	public void placeAuction() {
		int days = 0;

		System.out.println("What is your Item? (Please provide a short effective title.)");
		String description = S.next();
		System.out.println("Please enter a start price.");
		double startPriceInput = S.nextDouble();

		// double startPriceInput = 0;
		// while (startPriceInput == 0)
		// try {
		// if(S.hasNextDouble()){
		// startPriceInput = S.nextDouble();
		// }
		// } catch (NumberFormatException nfe) {
		// System.out.print("Try again: ");
		// startPriceInput = S.nextDouble();
		// }

		System.out.println("If wanted, please enter a reserve price. (else please press enter.)");
		double reservePriceInput = S.nextDouble();
		while ((days > 7 || days == 0)) {
			System.out.println("Please enter the length of your auction. (Under 7 Days.)");
			days = S.nextInt();
			Auction newAuction = new Auction(startPriceInput, reservePriceInput, days, new Item(description));
			auctions.add(newAuction);
		}
	}

	/**
	 * buyersBids Method.
	 * 
	 * <p>
	 * Allows Buyers to view their items that they have bid on at the date and
	 * time they bid on them.
	 * 
	 */
	public static void buyersBids() {
		int i = 0;
		char direction = '\0';
		boolean flag;

		ArrayDeque<String> stack = new ArrayDeque<String>();

		System.out.println("\n-- " + currentUser.getUsername() + "'s Bids -- \n");

		i = Auction.getBids().stream().filter((b) -> (b.toBidString().equals(currentUser.getUsername()))).map((b) -> {
			stack.push(
					String.format("Item: %s, Amount: £%.2f, Date: %s", b.getBiddedAuction().getItem().getDescription(),
							b.getAmount(), b.getWhen().format(DateTimeFormatter.ofPattern("d/MMM/uuuu HH:mm"))));
			return b;
		}).map((_item) -> {
			return _item;
		}).map((_item) -> 1).reduce(i, Integer::sum);

		while (i > 0) {
			System.out.println(stack.pop() + "\n");
			System.out.print("Next - N >> \n");

			flag = true;
			do {
				direction = S.next().toUpperCase().charAt(0);

				switch (direction) {

				case 'N':
					i--;
					flag = false;
					break;

				default:
					System.out.print("Please enter N to see next auction.\n");
					break;
				}
			} while (flag == true);
		}
	}

	// /**
	// * wonAuctions Method.
	// *
	// * Allows for a Buyer to view the closed auctions they have bidded on and
	// won.
	// *
	// */
	// public static void wonAuctions(){
	//
	// System.out.println("\n-- " + currentUser.getUsername() + "'s Auction
	// Wins! -- \n");
	//
	// for (Auction a : auctions){
	//
	// if (a.getStatus() == Status.C){
	//
	// if((a.getCurrentBid().getWho().getUsername()).equals(currentUser.getUsername())
	// && (a.getReservePrice() <= a.getCurrentBid().getAmount())){
	//
	// System.out.println(String.format("Item: %s, Winning Bid: £%.2f, Date:
	// %s", a.getItem().getDescription(), a.getCurrentBid().getAmount(),
	// a.getCurrentBid().getWhen().format(DateTimeFormatter.ofPattern("d/MMM/uuuu
	// HH:mm"))));
	//
	// }
	//
	// }
	//
	// }
	//
	// }
	/**
	 * unverifiedAuctions Method.
	 * 
	 * <p>
	 * Allows for a Seller to verify auctions they have created.
	 * 
	 */
	public void unverifiedAuctions() {

		boolean verifyQuestion = true;
		int i = 0;

		System.out.println("\n -- Verify An Auction -- \n");

		for (Auction a : auctions) {

			verifyQuestion = true;

			if (a.toUsernameString().equals(currentUser.getUsername()) && (a.getStatus().equals(Status.P))) {

				System.out.println(String.format(" Item: %s%s \n", a.getItem().getDescription(), a.toString()));
				;
				System.out.println("Do you wish to verify this auction? [Y|N]");

				while (verifyQuestion == true) {

					char choice = S.next().toUpperCase().charAt(0);
					switch (choice) {
					case 'Y':
						System.out.println("Your auction is now verified and active.");
						a.setStatus(Status.O);
						verifyQuestion = false;
						break;
					case 'N':
						System.out.println(
								"Please come back later to verify your auction. It will remain pending until you do so.");
						verifyQuestion = false;
						break;
					default:
						System.out.println("Invalid Response. Please Enter [Y|N]");
						break;
					}

				}
				i++;
			}
		}

	}

	/**
	 * browseAuction Method.
	 * 
	 * <p>
	 * Checks if user is an instance of a Buyer.
	 * <p>
	 * If they are a Buyer they can bid if they aren't they can only browse.
	 * 
	 */
	public synchronized void browseAuction() {

		int i = -1;
		int j = 0;
		int c = 1;
		char direction = '\0';
		boolean flag;
		List<Auction> currentlyOpenAuctions = new LinkedList<Auction>();

		Auction currentAuction = null;
		// USED STACK SO MOST RECENT SEEN FIRST BY USER

		ArrayDeque<String> stack = new ArrayDeque<String>();

		System.out.println("\n -- Auction List -- \n");

		for (Auction a : auctions) {

			i++;

			if (auctions.get(i).getStatus() == Status.O) {

				j++;
				currentlyOpenAuctions.add(a);
				stack.push(
						String.format(" Item: %s Bid: £%.2f" + " %s", auctions.get(i).getItem().getDescription() + "\n",
								+auctions.get(i).getCurrentBid().getAmount(), a.toString()));
				;

			}
		}

		while (j > 0) {
			System.out.println("-- Auction No. " + c + " --\n");
			System.out.println(stack.pop() + "\n");
			System.out.print("Next - N >> \n");
			if (Buyer.class.isInstance(getCurrentUser())) {
				System.out.println("Bid on item - B >> \n");
			}
			c++;
			flag = true;
			do {

				direction = S.next().toUpperCase().charAt(0);

				switch (direction) {

				case 'N':
					j--;
					flag = false;
					break;

				case 'B':
					if (Buyer.class.isInstance(getCurrentUser())) {
						j--;
						for (Auction a : auctions) {
							if (a.getItem().getDescription()
									.equals(currentlyOpenAuctions.get(j).getItem().getDescription())) {
								currentAuction = a;
							}
						}
						Auction.placeBid(currentAuction);
						j = -1;
						flag = false;
					} else {
						direction = '\0';
						System.err.print("Please enter N to see next auction.\n");
					}
					break;

				default:
					System.err.print("Please enter N to see next auction.\n");

				}

			} while (flag == true);
		}
	}

	/**
	 * setupAccount Method.
	 * 
	 * <p>
	 * Allows for accounts to be created in buyer view or seller view.
	 * 
	 */
	public void setupAccount() {

		char option;
		boolean isMenuDone;
		boolean isUsernameTaken = true;
		String usernameInput;
		String passwordInput;

		System.out.print("Please enter a Username: ");
		do {
			usernameInput = S.next();
			System.out.print("Please enter a password: ");
			passwordInput = S.next();
			do {
				System.out.println("[B|S] B - Buyer, S - Seller");
				option = S.next().toUpperCase().charAt(0);
				isMenuDone = true;
			} while (isMenuDone == false);
			for (User u : users) {
				if (u.getUsername().equals(usernameInput)) {
					System.out.println("This username is already taken. Please try again.");
				} else {
					isUsernameTaken = false;
				}
			}
		} while (isUsernameTaken == true);

		if (isUsernameTaken == false) {
			switch (option) {

			case 'B':
				users.add(new Buyer(usernameInput, passwordInput));
				break;
			case 'S':
				users.add(new Seller(usernameInput, passwordInput, false));
				break;
			default:
				System.out.println("Please enter a valid response.");
				isMenuDone = false;
				break;

			}
		}
	}

	/**
	 * logon Method.
	 * 
	 * <p>
	 * Allows for users to sign in to their account.
	 * <p>
	 * If the user is blocked they are prompt with a message and can not log in.
	 * 
	 */
	public void logon() {

		System.out.println("Please enter your Username.");
		String usernameInput = S.next();
		System.out.println("Please enter your password.");
		String passwordInput = S.next();

		for (User u : users) {
			if (u.getUsername().equals(usernameInput) && u.getPassword().equals(passwordInput)) {

				setCurrentUser(u);

				break;
			}

		}

		if (Buyer.class.isInstance(getCurrentUser())) {

			buyerUserMenu();

		} else if (Seller.class.isInstance(getCurrentUser())) {

			if (((Seller) currentUser).isBlocked() == false) {

				sellerUserMenu();
			} else {

				System.err.println("FUS RO DAH! You have been blocked!");

			}
		} else if (Admin.class.isInstance(getCurrentUser())) {

			adminUserMenu();

		} else {

			System.out.println("Your username or password was incorrect. Please try again.");

		}

	}

	/**
	 * blockViaAdmin Method.
	 * 
	 * <p>
	 * Allows for Admins to block a user by their name.
	 * 
	 */
	public void blockViaAdmin() {

		System.out.println("Please enter a Username you wish to block.");
		String usernameInput = S.next();

		for (User u : users) {

			if (Seller.class.isInstance(u)) {

				if (u.getUsername().equals(usernameInput)) {

					((Seller) u).setBlocked();
					System.out.println("You have successfully blocked: " + usernameInput);

				}

			}

		}
	}

	/**
	 * unblockViaAdmin Method.
	 * 
	 * <p>
	 * Allows for Admins to unblock a user by their name.
	 * 
	 */
	public void unblockViaAdmin() {

		System.out.println("Please enter a Username you wish to unblock.");
		String usernameInput = S.next();

		users.stream().filter((u) -> (Seller.class.isInstance(u)))
				.filter((u) -> (u.getUsername().equals(usernameInput))).map((u) -> {
					((Seller) u).setUnBlocked();
					return u;
				}).forEachOrdered((_item) -> {
					System.out.println("You have successfully unblocked: " + usernameInput);
				});
	}

	// public void blockAuctionViaAdmin() {
	//
	// int i = -1;
	// int j = 0;
	// char direction = '\0';
	// boolean flag;
	//
	// Auction currentAuction = null;
	// // USED STACK SO MOST RECENT SEEN FIRST BY USER
	//
	// ArrayDeque<String> stack = new ArrayDeque<String>();
	//
	// System.out.println("\n -- Auction List -- \n");
	//
	// for (Auction a : auctions){
	//
	// i++;
	//
	// if(auctions.get(i).getStatus() != Status.C){
	//
	// j++;
	//
	// stack.push(String.format(" Item: %s%s Owner: %s\n",
	// auctions.get(i).getItem().getDescription(),a.toString(),auctions.get(i).toUsernameString()));;
	//
	// }
	// }
	//
	// while(j > 0) {
	// System.out.println(stack.pop() + "\n");
	// System.out.print("Next - N >> \n");
	// System.out.println("Block Auction - B >> \n");
	// flag = true;
	// do{
	//
	// direction = S.next().toUpperCase().charAt(0);
	//
	// switch (direction) {
	//
	// case 'N': j--;
	// flag = false;
	// break;
	//
	// case 'B':
	// j--;
	// currentAuction = auctions.get(i);
	// currentAuction.setBlocked();
	// i = -1;
	// flag = false;
	// break;
	//
	// default: System.err.print("Please enter N to see next auction.\n");
	//
	// }
	//
	// }while(flag == true);
	// }
	// }
	//

	// IF currentUser == null --> then no user signed in. GOTO GUEST MENU

	public static User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public static List<Auction> getAuctions() {
		return auctions;
	}

	public String getUserFileLocation() {
		return userFileLocation;
	}

	public void setUserFileLocation(String userFileLocation) {
		this.userFileLocation = userFileLocation;
	}

}
