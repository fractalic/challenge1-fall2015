
public enum Direction {
    
    NORTH(0, "North/Up"),
    SOUTH(1, "South/Down"),
    EAST(2, "East/Right"),
    WEST(3, "West/Down");
    

    private final int ID;
    private final String text;


    private Direction(final int ID, final String text) {
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
