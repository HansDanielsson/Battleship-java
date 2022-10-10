package boat;

public class Submarine extends Ship {

	public Submarine() {
		setLength(3);
	}

	@Override
	public String getShipType() {
		return "submarine 3";
	}
}