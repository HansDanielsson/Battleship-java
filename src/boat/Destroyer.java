package boat;

public class Destroyer extends Ship {

	public Destroyer() {
		setLength(4);
	}

	@Override
	public String getShipType() {
		return "destroyer 4";
	}
}