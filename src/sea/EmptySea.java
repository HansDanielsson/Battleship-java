package sea;

import boat.Ship;

public class EmptySea extends Ship {

	public EmptySea() {
		setLength(1);
	}

	/**
	 * Overrides that is inherited from Ship, and always returns false.
	 */
	@Override
	public boolean shootAt(int row, int column) {
		return false;
	}

	/**
	 * Overrides that is inherited from Ship, and always returns false.
	 */
	@Override
	public boolean isSunk() {
		return false;
	}

	/**
	 * Returns a single-character String to use in the Ocean's print method.
	 */
	@Override
	public String toString() {
		return "..";
	}

	@Override
	public String getShipType() {
		return "empty 1";
	}
}