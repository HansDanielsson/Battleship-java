package boat;

public class LightCruiser extends Ship {

	public LightCruiser() {
		setLength(5);
	}

	@Override
	public String getShipType() {
		return "light cruiser 5";
	}
}