
public class Location {
    /**
     * Abstraction function:
     * Represents a location relative to (0,0) at the bottom left of
     * a game board.
     * 
     * Invariant:
     * Upon creation, the location may be constrained
     * to square bounds. The location constraints and constraint status
     * never change.
     * 
     * Invariant:
     * Both x and y are inside the range [min,max]
     * 
     */
    enum Coordinate {
        FIRST(0, "First axis of location."),
        SECOND(1, "Second axis of location.");
        

        private final int ID;
        private final String text;


        private Coordinate(final int ID, final String text) {
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
    private final boolean isConstrained;
    private final int min;
    private final int max;
    private int x;
    private int y;
    
    /**
     * Create an unconstrained location.
     * 
     * @param x The first coordinate of the location.
     * @param y The second coordinate of the location.
     * @return An unconstrained location at the specified coordinates.
     */
    public Location(int x, int y) {
        this.isConstrained = false;
        this.set(x, y);
        this.min = Integer.MIN_VALUE;
        this.max = Integer.MAX_VALUE;
    }
    
    /**
     * Create a constrained location. The constraints are square,
     * such that both coordinates are constrained to the same bounds.
     * 
     * @requires x in [min, max] and y in [min, max].
     * @throws OutOfBoundsException (unchecked) if x and y do not meet constraints.
     * @param x The first coordinate of the location.
     * @param y The second coordinate of the location.
     * @param min The minimum value of each coordinate.
     * @param max The maximum value of each coordinate.
     * @return A constrained location, at the specified coordinates.
     */
    public Location(int x, int y, int min, int max) {
        this.set(x, y);
        this.isConstrained = false;
        this.min = min;
        this.max = max;
        // TODO: @throws OutOfBoundsException (unchecked) if x and y do not meet constraints.
    }
    
    /**
     * Update the location.
     * 
     * @requires x and y in [this.getContraintMin(), this.getConstraintMax()]
     * @param x The new first coordinate.
     * @param y The new second coordinate.
     * @throws OutOfBoundsException (unchecked) if x and y do not meet constraints.
     * @modifies First and second coordinates of location.
     */
    public void set(int x, int y) {
        if (this.isConstrained) {
            if ((x >= min) && (x <= max)) {
                this.x = x;
            }
            if ((y >= min) && (y <= max)) {
                this.y = y;
            }
        } else {
            this.x = x;
            this.y = y;
        }
        // TODO: @throws OutOfBoundsException (unchecked) if x and y do not meet constraints.
    }
    
    /**
     * Check if this Location is constrained.
     * 
     * @return true if this Location was constrained at creation.
     */
    public boolean isConstrained() {
        return this.isConstrained;
    }
    
    /**
     * Get the value of the upper bound of this location's coordinates.
     * 
     * @return The upper bound specified at creation, or Integer.MAX_VALUE if
     *         no bound was given at creation.
     */
    public int getConstraintMax() {
        if (this.isConstrained) {
            return this.max;
        } else {
            return Integer.MAX_VALUE;
        }
    }
    
    /**
     * Get the value of the lower bound of this location's coordinates.
     * 
     * @return The lower bound specified at creation, or Integer.MIN_VALUE if
     *         no bound was given at creation.
     */
    public int getConstraintMin() {
        if (this.isConstrained) {
            return this.min;
        } else {
            return Integer.MIN_VALUE;
        }
    }
    
    /**
     * Get the value of a coordinate.
     * 
     * @param coordinate The coordinate to retrieve.
     * @return the value of the specified coordinate.
     */
    public int getCoordinate(Location.Coordinate coordinate) {
        if (coordinate == Coordinate.FIRST) {
            return this.x;
        } else {
            return this.y;
        }
    }

}
