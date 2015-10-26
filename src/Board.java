import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    
    public enum LocationState {
        
        AVAILABLE(0, "available"),
        UNAVAILABLE(1, "unavailable");
        //OCCUPIED(2, "occupied"); //unnecessary I think.
        

        private final int ID;
        private final String text;


        private LocationState(final int ID, final String text) {
            this.ID = ID;
            this.text = text;
        }

        /* (non-Javadoc)
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return this.text;
        }

    }
    
    private final Integer dimension;
    private final int[][] adjacency;
    private final LocationState[][] availability;
    private List<Location> changeLog;
	
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
	    // initializeAdjacency();
	    
	    this.availability = new LocationState[dimension][dimension];
        initializeAvailability();
        
        changeLog = new ArrayList<Location>();
	}

	/**
	 * Get the dimension of the board.
	 * 
	 * @return Dimension of the board.
	 */
	public int getDimension() {
	    return this.dimension;
	}
	
	/**
	 * Set the state of a given location.
	 * 
	 * @requires location must be constrained to dimension - 1.
	 * @param location The location to modify.
	 * @param state The state to associate with this location.
	 * @modifies State of the given location.
	 * @throws InvalidStateException (unchecked) if location
	 *         is constrained to anything other than dimension - 1.
	 */
	public void setStateAt(final Location location, final LocationState state) {
	    if (location.getUpperBound() != dimension - 1) {
	        throw new InvalidStateException("Invalid location for this board.");
	    }
	    
	    int x = location.getCoordinate(Location.Coordinate.FIRST);
	    int y = location.getCoordinate(Location.Coordinate.SECOND);
	    
	    availability[x][y] = state;
	    
	    if (state == LocationState.UNAVAILABLE) {
	        changeLog.add(location.clone());
	    }
	}
	
	/**
	 * Get the state of a given location.
	 * 
	 * @requires location must be constrained to dimension - 1.
     * @param location The location to modify.
     * @modifies State of the given location.
     * @throws InvalidStateException (unchecked) if location
     *         is constrained to anything other than dimension - 1. 
     * @return The state of this location.
	 */
	public LocationState getStateAt(final Location location) {
	    if (location.getUpperBound() != dimension - 1) {
            throw new InvalidStateException("Invalid location for this board.");
        }
	    
	    int x = location.getCoordinate(Location.Coordinate.FIRST);
        int y = location.getCoordinate(Location.Coordinate.SECOND);
        
        return availability[x][y];
	}
	
	/**
	 * Get all the acceptable movement directions for a particular location.
	 * 
	 * @requires location must be constrained to dimension - 1.
	 * @param location The location to check for possible movements.
	 * @return A list of all directions in which it is possible to move
	 *         from this location.
	 * @throws InvalidStateException (unchecked) if location
     *         is constrained to anything other than dimension - 1.
	 */
	public List<Direction> getAvailableDirectionsAt(final Location location) {
	    if (location.getUpperBound() != dimension - 1) {
            throw new InvalidStateException("Invalid location for this board.");
        }
	    
	    final int xSource = location.getCoordinate(Location.Coordinate.FIRST);
	    final int ySource = location.getCoordinate(Location.Coordinate.SECOND);
        List<Direction> directions = new ArrayList<Direction>();
        int xDest;
        int yDest;
        
        if ((yDest = ySource + 1) < dimension) {
            if (availability[xSource][yDest] == LocationState.AVAILABLE) {
                directions.add(Direction.NORTH);
            }
        }
        if ((yDest = ySource - 1) >= 0) {
            if (availability[xSource][yDest] == LocationState.AVAILABLE) {
                directions.add(Direction.SOUTH);
            }
        }
        if ((xDest = xSource + 1) < dimension) {
            if (availability[xDest][ySource] == LocationState.AVAILABLE) {
                directions.add(Direction.EAST);
            }
        }
        if ((xDest = xSource - 1) >= 0) {
            if (availability[xDest][ySource] == LocationState.AVAILABLE) {
                directions.add(Direction.WEST);
            }
        }
        
        
        /*
	    final int dimensionSquared = dimension*dimension;
	    final int adjacencySourceIndex = x + (y * dimension);
	    int adjacencyDestIndex;
	    
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
        */
        return directions;
	}
	
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
	
	/**
	 * Initialize the availability matrix, marking all locations as available.
	 * 
	 * @modifies availability
	 * @result All locations are marked as available.
	 */
	private void initializeAvailability() {
	    for (int x = 0; x < dimension; x++) {
	        for (int y = 0; y < dimension; y++) {
	            availability[x][y] = LocationState.AVAILABLE;
	        }
	    }
	}

    /**
     * Produce a formatted string indicating board properties
     * and all moves that have occurred.
     * 
     * @param The current mode of the game.
     * @return A formatted string as described.
     */
    public String serialize(Game.Mode mode){
        StringBuilder serialized = new StringBuilder();
        serialized.append("START_CONFIG\n");
        serialized.append("\tDIMENSION: " + dimension.toString() + "\n");
        serialized.append("\tMODE     : " + mode.toString() + "\n");
        serialized.append("START_CONFIG\n\n");
        
        int first = 1;
        int player = 1;
        Location lastLocation = new Location(0,0,1);
        
        for (Location change : changeLog) {
            if (first == 1) {
                lastLocation = change;
                first = 0;
                player = 2;
            } else {
                if (player == 2) {
                    serialized.append("START_BOARD\n");
                    serialized.append("\tP1_LOCATION: " + lastLocation.toString() + "\n");
                    serialized.append("\tP2_LOCATION: " + change.toString() + "\n");
                    serialized.append("END_BOARD\n\n");
                    
                    player = 1;
                } else {
                    serialized.append("START_BOARD\n");
                    serialized.append("\tP1_LOCATION: " + change.toString() + "\n");
                    serialized.append("\tP2_LOCATION: " + lastLocation.toString() + "\n");
                    serialized.append("END_BOARD\n\n");
                    
                    player = 2;
                }

                lastLocation = change;
            }
        }
        
        return serialized.toString();
    }
	
	/**
	 * Determine all the locations which are reachable from this location on
	 * the game board, disregarding current availability of the destination.
	 * 
	 * @param source The initial location.
	 * @return All indices such that
	 */
	/*
	private List<int> getDestinationIndices(final Location source) {
	    
	}
	*/
	
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

