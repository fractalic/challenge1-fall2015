public class Player {
	
	public enum PlayerType {

	}
	public Player(){}
	public Player(PlayerType type){}
	public Player(String name, PlayerType type){}

	public Location getLocation(){ return new Location();}
	public boolean moveTo(Direction direction){ return false; }	
	public Location getOpponentLocation(){ return new Location(); }
}

