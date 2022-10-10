package boat;

public class BattleCruiser extends Ship {
	public BattleCruiser() {
		setLength(7);
	}

	@Override
	public String getShipType() {
		return "battlecruiser 7";
	}
}