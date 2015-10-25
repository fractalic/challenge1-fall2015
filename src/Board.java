import java.util.List;

public class Board {
	
	public Board(int dimension) {}

	/* direction is one of North, South, East and West. */
	List<Direction> getAvailableDirectionsAt(Location location){ return null; }	
	
	public String serialize(){ return null; }
	
	/* API
	 * - check if location available
	 * - mark location unavailable
	 * - mark location occupied by player
	 */
}

