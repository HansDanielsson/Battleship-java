package boat;

public class Cruiser extends Ship {

	public Cruiser() {
		setLength(6);
	}

	@Override
	public String getShipType() {
		return "cruiser 6";
	}
}