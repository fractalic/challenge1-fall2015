import java.util.List;

public class Board {
    
    private int dimension = 0;
    
    //public static final Location origin = new Location(0,0);
	
    /**
     * Create an empty square Board with size
     * dimension by dimension.
     * @param dimension The dimension of the Board along one edge.
     */
	public Board(int dimension) {
	    this.dimension = dimension;
	}

	/* direction is one of North, South, East and West. */
	List<Direction> getAvailableDirectionsAt(Location location){ return null; }	
	
	public String serialize(){ return null; }
	
	/* API
	 * - check if location available
	 * - mark location unavailable
	 * - mark location occupied by player
	 */
}

