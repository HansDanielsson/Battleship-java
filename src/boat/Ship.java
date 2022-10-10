package boat;

import sea.Ocean;

public abstract class Ship {

	private int bowRow; // The row (0 to 19) which contains the bow (front) of the ship.
	private int bowColumn; // The column which contains the bow (front) of the ship.
	private int length; // The number of squares occupied by the ship.
	// An "empty sea" location has length 1.
	private boolean horizontal; // True if the ship occupies a single row, false otherwise. Ships will either be
	// placed vertically or horizontally in the ocean.
	private boolean[] hit; // This is a boolean array of size 8 that record hits. Only battleship use all
	// the locations. The others will use fewer.

	public int getBowRow() {
		return bowRow;
	}

	public void setBowRow(int bowRow) {
		this.bowRow = bowRow;
	}

	public int getBowColumn() {
		return bowColumn;
	}

	public void setBowColumn(int bowColumn) {
		this.bowColumn = bowColumn;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		bowRow = -1;
		bowColumn = -1;
		this.length = length;
		horizontal = false;
		hit = new boolean[length];
		for (int i = 0; i < length; i++)
			hit[i] = false;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	public boolean getHit(int i) {
		return hit[i];
	}

	public void setHit(int i) {
		hit[i] = true;
	}

	public abstract String getShipType();

	/**
	 * Returns true if it is okay to put a ship of this length with its bow in this
	 * location, with the given orientation, and returns false otherwise. The ship
	 * must not overlap another ship, or touch another ship (vertically,
	 * horizontally, or diagonally) and it must not "stick out" beyond the array. Do
	 * not actually change either the ship or the Ocean, just says whether it is
	 * legal to do so.
	 * 
	 * @param row
	 * @param column
	 * @param horizonal
	 * @param ocean
	 * @return
	 */
	public boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		int x;
		int y;
		int x1 = row - 1;
		int x2 = row + 2;
		int y1 = column - 1;
		int y2 = column + 2;
		int oceanLength = ocean.getShipArray().length;
		boolean okPlace = true;
		if (horizontal) {
			y2 = column + length + 1;
			if (y2 > oceanLength + 1)
				okPlace = false;
		} else {
			x2 = row + length + 1;
			if (x2 > oceanLength + 1)
				okPlace = false;
		}
		x1 = Math.max(x1, 0);
		x2 = Math.min(x2, oceanLength);
		y1 = Math.max(y1, 0);
		y2 = Math.min(y2, oceanLength);
		for (x = x1; x < x2; x++)
			for (y = y1; y < y2; y++)
				if (ocean.getShipArray()[x][y].getBowRow() != -1)
					okPlace = false;
		return okPlace;
	}

	/**
	 * "Puts" the ship in the ocean. This involves giving values to the bowRow,
	 * bowColumn, and horizontal instance variables in the ship, and it also
	 * involves putting a reference to the ship in each of 1 or more locations (up
	 * to 8) in the ships array in the Ocean object. (Note: This will be as many as
	 * eight identical references; you can't refer to a "part" of a ship, only to
	 * the whole ship.)
	 * 
	 * @param row
	 * @param column
	 * @param horizontal
	 * @param ocean
	 */
	public void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		int position;
		bowRow = row;
		bowColumn = column;
		this.horizontal = horizontal;

		if (horizontal)
			for (position = 0; position < length; position++) {
				ocean.getShipArray()[row][column + position].setBowRow(row);
				ocean.getShipArray()[row][column + position].setBowColumn(column);
			}
		else
			for (position = 0; position < length; position++) {
				ocean.getShipArray()[row + position][column].setBowRow(row);
				ocean.getShipArray()[row + position][column].setBowColumn(column);
			}
	}

	/**
	 * If a part of the ship occupies the given row and column, and the ship hasn't
	 * been sunk, mark that part of the ship as "hit" (in the hit array, 0 indicates
	 * the bow) and return true, otherwise return false.
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean shootAt(int row, int column) {
		boolean shoot = false;
		int hitPos;
		if (horizontal) {
			if (bowRow == row) {
				hitPos = column - bowColumn;
				if (hitPos < length) {
					shoot = true;
					hit[hitPos] = true;
				}
			}
		} else if (bowColumn == column) {
			hitPos = row - bowRow;
			if (hitPos < length) {
				shoot = true;
				hit[hitPos] = true;
			}
		}
		return shoot;
	}

	/**
	 * Return true if every part of the ship has been hit, false otherwise.
	 * 
	 * @return
	 */
	public boolean isSunk() {
		boolean sunk = true;
		for (int i = 0; i < hit.length; i++)
			if (!hit[i])
				sunk = false;
		return sunk;
	}

	/**
	 * Returns a single-character String to use in the Ocean's print method.
	 */
	@Override
	public String toString() {
		boolean sunk = true;
		for (int i = 0; i < hit.length; i++)
			if (!hit[i])
				sunk = false;
		return sunk ? "x." : "S.";
	}
}