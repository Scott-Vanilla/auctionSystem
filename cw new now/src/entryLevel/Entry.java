//DONE
// Autosave accounts after x seconds using multithreading.

package entryLevel;


import java.io.IOException;

//import sun.audio.AudioPlayer;
//import sun.audio.AudioStream;

/**
 * Entry Class.
 * 
 * <p>
 * Used for a main method to call and communicate with core classes from
 * platform classes.
 * 
 * @author CMPSHUG1 & CMPJMCGU
 * @version 1.0
 *
 */
public class Entry {

	public static void main(String[] args) throws IOException {
		// InputStream in = new
		// FileInputStream("M:\\OO\\CW2_21-40_06-04-master\\5104COMP-Coursework2
		// 1.3\\src\\Skyrim_Theme_-_Dragonborn_Main_Theme-b6RIlS1DQhQ.wav");

		// Create an AudioStream object from the input stream.
		// AudioStream as = new AudioStream(in);

		// Use the static class member "player" from class AudioPlayer to play
		// clip.
		// AudioPlayer.player.start(as);

		System.out.println("-- Welcome to Belethor's General Goods! -- \n ");
		System.out.println("-- The Best Auction in all of Skyrim! (Before I took an arrow to the knee.)-- \n ");

		new Sys();

		// AudioPlayer.player.stop(as);
	}

}
