package sea;

import java.util.Random;

import boat.BattleCruiser;
import boat.BattleShip;
import boat.Cruiser;
import boat.Destroyer;
import boat.LightCruiser;
import boat.Ship;
import boat.Submarine;

public class Ocean {
	private static Ship[][] ships = new Ship[20][20]; // Used to quickly determine which ship is in any given location.
	private static int shotsFired; // The total number of shots fired by the user;
	private static int hitCount; // The number of times a shot hit a ship.
	private static int shipsSunk; // The number of ships sunk.

	public Ocean() {
		for (int x = 0; x < ships.length; x++)
			for (int y = 0; y < ships[x].length; y++)
				ships[x][y] = new EmptySea();

		shotsFired = 0;
		hitCount = 0;
		shipsSunk = 0;
	}

	/**
	 * Place all randomly on the ocean. Place larger ships before smaller ones. Or
	 * you may end up with no legal place to put a large ship. You will want to use
	 * the Random class in the java.util package, so look that up in the Java API.
	 */
	public void placeAllShipsRandomly() {
		
		int row;
		int column;
		var rn = new Random();
		boolean horizontal = rn.nextInt(100) < 50;

		// one 8-Battleship
		var battleShipPos = new BattleShip();
		battleShipPos.setHorizontal(horizontal);
		var shiploop = true;
		while (shiploop) {
			row = rn.nextInt(20);
			column = rn.nextInt(20);
			if (battleShipPos.okToPlaceShipAt(row, column, horizontal, this)) {
				ships[row][column] = battleShipPos;
				battleShipPos.placeShipAt(row, column, horizontal, this);
				shiploop = false;
			}
		}
		// one 7-Battle cruiser
		horizontal = rn.nextInt(100) < 50;
		var battleCruiserPos = new BattleCruiser();
		battleCruiserPos.setHorizontal(horizontal);
		shiploop = true;
		while (shiploop) {
			row = rn.nextInt(20);
			column = rn.nextInt(20);
			if (battleCruiserPos.okToPlaceShipAt(row, column, horizontal, this)) {
				ships[row][column] = battleCruiserPos;
				battleCruiserPos.placeShipAt(row, column, horizontal, this);
				shiploop = false;
			}
		}
		// two 6-Cruisers
		int forloop;
		for (forloop = 0; forloop < 2; forloop++) {
			horizontal = rn.nextInt(100) < 50;
			var cruiserPos = new Cruiser();
			cruiserPos.setHorizontal(horizontal);
			shiploop = true;
			while (shiploop) {
				row = rn.nextInt(20);
				column = rn.nextInt(20);
				if (cruiserPos.okToPlaceShipAt(row, column, horizontal, this)) {
					ships[row][column] = cruiserPos;
					cruiserPos.placeShipAt(row, column, horizontal, this);
					shiploop = false;
				}
			}
		}
		// two 5-Light Cruisers
		for (forloop = 0; forloop < 2; forloop++) {
			horizontal = rn.nextInt(100) < 50;
			var lightcruiserPos = new LightCruiser();
			lightcruiserPos.setHorizontal(horizontal);
			shiploop = true;
			while (shiploop) {
				row = rn.nextInt(20);
				column = rn.nextInt(20);
				if (lightcruiserPos.okToPlaceShipAt(row, column, horizontal, this)) {
					ships[row][column] = lightcruiserPos;
					lightcruiserPos.placeShipAt(row, column, horizontal, this);
					shiploop = false;
				}
			}
		}
		// three 4-Destroyers
		for (forloop = 0; forloop < 3; forloop++) {
			horizontal = rn.nextInt(100) < 50;
			var destroyerPos = new Destroyer();
			destroyerPos.setHorizontal(horizontal);
			shiploop = true;
			while (shiploop) {
				row = rn.nextInt(20);
				column = rn.nextInt(20);
				if (destroyerPos.okToPlaceShipAt(row, column, horizontal, this)) {
					ships[row][column] = destroyerPos;
					destroyerPos.placeShipAt(row, column, horizontal, this);
					shiploop = false;
				}
			}
		}
		// four 3-Submarines
		for (forloop = 0; forloop < 4; forloop++) {
			horizontal = rn.nextInt(100) < 50;
			var submarinePos = new Submarine();
			submarinePos.setHorizontal(horizontal);
			shiploop = true;
			while (shiploop) {
				row = rn.nextInt(20);
				column = rn.nextInt(20);
				if (submarinePos.okToPlaceShipAt(row, column, horizontal, this)) {
					ships[row][column] = submarinePos;
					submarinePos.placeShipAt(row, column, horizontal, this);
					shiploop = false;
				}
			}
		}
	}

	/**
	 * Returns true if the given location contains a ship, false if it does not.
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean isOccupied(int row, int column) {
		return ships[row][column].getBowRow() != -1;
	}

	/**
	 * Returns true if the given location contains a "real" ship, still afloat.
	 * False if it does not. In addition, this method updates the number of shots
	 * that have been fired, and the number of hits. Note: If a location contains a
	 * "real" ship, shootAt should return true every time the user shoots at that
	 * same location. Once a ship has been "sunk", additional shots at its location
	 * should return false.
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean shootAt(int row, int column) {
		boolean shoot = false;
		int bowRow = ships[row][column].getBowRow();
		if (bowRow != -1) {
			int hitPos;
			int bowColumn = ships[row][column].getBowColumn();
			if (ships[bowRow][bowColumn].isHorizontal())
				hitPos = column - bowColumn;
			else
				hitPos = row - bowRow;
			ships[bowRow][bowColumn].setHit(hitPos);
			ships[row][column].setBowRow(-1);
			ships[row][column].setBowColumn(-1);
			if (ships[bowRow][bowColumn].isSunk()) {
				System.out.println("You just sank a " + ships[bowRow][bowColumn].getShipType());
				shipsSunk++;
			}
			hitCount++;
			shoot = true;
		}
		shotsFired++;
		return shoot;
	}

	/**
	 * Return the number of shots fired.
	 * 
	 * @return
	 */
	public int getShotsFired() {
		return shotsFired;
	}

	/**
	 * Returns the number of hits recorded. All hits are counted, not just the first
	 * time a given square is hit.
	 * 
	 * @return
	 */
	public int getHitCount() {
		return hitCount;
	}

	/**
	 * Return the number of ships sunk.
	 * 
	 * @return
	 */
	public int getShipsSunk() {
		return shipsSunk;
	}

	/**
	 * Return true if all ships have been sunk, otherwise false.
	 * 
	 * @return
	 */
	public boolean isGameOver() {
		boolean gameOver = true;
		for (int i = 0; i < ships.length; i++)
			for (int j = 0; j < ships[i].length; j++)
				if (ships[i][j].getBowRow() != -1)
					gameOver = false;
		return gameOver;
	}

	/**
	 * Returns the 20 X 20 array of ships.
	 * 
	 * @return
	 */
	public Ship[][] getShipArray() {
		return ships;
	}

	/**
	 * Print the ocean with ships on.
	 */
	public void print() {
		int row;
		int col;
		System.out.print("    ");
		for (col = 0; col < ships.length; col++) {
			System.out.print(col / 10);
			System.out.print(col % 10);
		}
		System.out.println(" ");
		for (row = 0; row < ships.length; row++) {
			System.out.print(row / 10);
			System.out.print(row % 10 + ": ");
			for (col = 0; col < ships[row].length; col++)
				System.out.print(ships[row][col]);
			System.out.println(" ");
		}
	}
}