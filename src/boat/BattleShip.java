package boat;

public class BattleShip extends Ship{

	public BattleShip() {
		setLength(8);
	}

	@Override
	public String getShipType() {
		return "battleship 8";
	}
}