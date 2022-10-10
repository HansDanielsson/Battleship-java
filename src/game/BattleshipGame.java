/**
 * 
 */
package game;

import java.util.Scanner;

import sea.Ocean;

/**
 * @author Hans
 *
 */
public class BattleshipGame {

private static Ocean board = new Ocean();
	
	static Scanner scanner = new Scanner(System.in).useDelimiter("\\A");

	private static String messageInput(String msg) {
		System.out.print(msg + " ");
		return scanner.nextLine();
	}

	private static void printShots() {
		System.out.println("Number of shots: " + board.getShotsFired());
		System.out.println("Number of hits : " + board.getHitCount());
		System.out.println("Number of ships: " + board.getShipsSunk());
	}

	private static void inputShots() {
		int row;
		int column;
		String inShot = messageInput("Shoot 5 shots:");
		String[] arrInShot = inShot.split(";");
		String[] sShot;
		for (String Point : arrInShot) {
			sShot = Point.split(",");
			try {
				row = Integer.parseInt(sShot[0]);
				column = Integer.parseInt(sShot[1]);

			} catch (Exception e) {
				row = 0;
				column = 0;
			}
			if (board.shootAt(row, column)) {
				System.out.println("Hitt ship row:" + row + ", col:" + column);
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		board.placeAllShipsRandomly();
		board.print();
		
		String textIn;
		boolean playAgain = true;
		while (playAgain) {
			inputShots();
			if (board.isGameOver()) {
				playAgain = false;
			} else {
				textIn = messageInput("Play again (Y/N)?");
				if (textIn.equalsIgnoreCase("P"))
					board.print();
				else
					playAgain = textIn.equalsIgnoreCase("Y");
			}
		}
		board.print();
		printShots();
	}
}