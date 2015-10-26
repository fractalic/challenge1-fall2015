import java.util.ArrayList;
import java.util.List;

public class Board {
    
    private final int dimension;
    private final int[][] adjacency;
    private final int[][] occupation;
	
    /**
     * Create an empty square Board with size
     * dimension by dimension.
     * 
     * @requires Dimension is greater than 0.
     * @param dimension The dimension of the Board along one edge.
     * @throws InvalidStateException if dimension is not greater than 0.
     */
	public Board(final int dimension) {
        if (dimension <= 0) {
            throw new
             InvalidStateException("Cannot create board with given dimension.");
        }
	    this.dimension = dimension;
	    this.adjacency = new int[dimension*dimension][dimension*dimension];
	    initializeAdjacency();
	    this.occupation = new int[dimension][dimension];
	}

	/* direction is one of North, South, East and West. */
	List<Direction> getAvailableDirectionsAt(final Location location) {
	    final int x = location.getCoordinate(Location.Coordinate.FIRST);
	    final int y = location.getCoordinate(Location.Coordinate.SECOND);
	    final int dimensionSquared = dimension*dimension;
	    final int adjacencySourceIndex = x + (y * dimension);
	    int adjacencyDestIndex;
	    List<Direction> directions = new ArrayList<Direction>();
	    
        if ((adjacencyDestIndex = adjacencySourceIndex + dimension) < dimensionSquared) {
            if (adjacency[adjacencyDestIndex][adjacencySourceIndex] == 1) {
                directions.add(Direction.NORTH);
            }
        }
        if ((adjacencyDestIndex = adjacencySourceIndex - dimension) >= 0) {
            if (adjacency[adjacencyDestIndex][adjacencySourceIndex] == 1) {
                directions.add(Direction.SOUTH);
            }
        }
	    if ((adjacencyDestIndex = adjacencySourceIndex + 1) < dimensionSquared) {
	        if (adjacency[adjacencyDestIndex][adjacencySourceIndex] == 1) {
	            directions.add(Direction.EAST);
	        }
	    }
        if ((adjacencyDestIndex = adjacencySourceIndex - 1) >= 0) {
            if (adjacency[adjacencyDestIndex][adjacencySourceIndex] == 1) {
                directions.add(Direction.WEST);
            }
        }
        
        return directions;
	}	
	
	public String serialize(){ return null; }
	
	/**
	 * Initialize the adjacency matrix so that all locations that can be reached
	 * from a given location are known.
	 * 
	 * @modifies adjacency
	 * @results adjacency[x1 + dimension*y1][x2 + dimension*y2] = 1 iff
	 *          Location[x2][y2] is accessible from Location[x1][y1].
	 */
	private void initializeAdjacency() {
	    
	    for (int xSource = 0; xSource < dimension; xSource++) {
	        for (int ySource = 0; ySource < dimension; ySource++) {
	            for (int xDest = 0; xDest < dimension; xDest++) {
	                for (int yDest = 0; yDest < dimension; yDest++) {
	                    if (( (xSource == xDest) && (yDest == ySource + 1) ) ||
	                        ( (xSource == xDest) && (yDest == ySource - 1) ) ||
	                        ( (xDest == xSource + 1) && (yDest == ySource) ) ||
	                        ( (xDest == xSource - 1) && (yDest == ySource) )) {
	                        
	                        adjacency[xDest + (yDest * dimension)]
	                                 [xSource + (ySource * dimension)] = 1;
	                    }
	                }
	            }
	        }
	    }
	    
	}
	
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

