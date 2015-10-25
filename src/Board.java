import java.util.List;

public class Board {
    
    private int dimension = 0;
	
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
	 * - serialize (save a board)
	 * - deserialize (load board state from saved format)
	 * - Don't support replay here. Just pass out a list of moves using
	 *   deserialize.
	 * - I want to implement a second board type which allows simulation of all
	 *   possible movements in order to determine the optimal move for an ai player.
	 *   This board can then recommend moves to AI Players.
	 */
}

