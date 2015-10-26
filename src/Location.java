
public class Location {
    /**
     * Abstraction function:
     * Represents a location relative to (0,0) at the bottom left of
     * a game board.
     * 
     * Invariant:
     * The location is immutable.
     * 
     * Invariant:
     * Both x and y are inside the range [0,max]
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
    
    private static final int MIN = 0;
    private final int max;
    private int x;
    private int y;
    
    /**
     * Create a constrained location. The constraints are square,
     * such that both coordinates are constrained to the same bounds.
     * 
     * @requires x and y both in [0, max].
     * @throws OutOfBoundsException (unchecked) if x and y do not meet constraints.
     * @param x The first coordinate of the location.
     * @param y The second coordinate of the location.
     * @param max The maximum value of each coordinate.
     * @return A constrained location, at the specified coordinates.
     */
    public Location(final int x, final int y, final int max) {
        if ((x < MIN) || (x > max) || (y < MIN) || (y > max)) {
            throw new LocationOutOfBoundsException();
        }
        this.x = x;
        this.y = y;
        this.max = max;
    }
    
    /**
     * Create a copy of a Location.
     * 
     * @param location The Location to be copied.
     * @return A copy of the given Location.
     */
    public Location clone(Location location) {
        return new Location(location.x, location.y, location.max);
    }
    
    /**
     * Create a copy of a Location, offset by some value.
     * 
     * @param plusX The amount to add to the first coordinate.
     * @param plusY The amount to add to the second coordinate.
     * @throws LocationOutOfBoundsException if the new location exceeds
     *         the bounds of the old one.
     * @return A new location offset by some (x,y) distance from this.
     */
    public Location cloneOffset(int plusX, int plusY) {
        return new Location(this.x + plusX, this.y + plusY, this.max);
    }
    
    /**
     * Get the value of the upper bound of this location's coordinates.
     * 
     * @return The upper bound specified at creation, or Integer.MAX_VALUE if
     *         no bound was given at creation.
     */
    public int getUpperBound() {
        return this.max;
    }
    
    /**
     * Get the value of a coordinate.
     * 
     * @param coordinate The coordinate to retrieve.
     * @return the value of the specified coordinate.
     */
    public int getCoordinate(final Location.Coordinate coordinate) {
        if (coordinate == Coordinate.FIRST) {
            return this.x;
        } else {
            return this.y;
        }
    }

}
