public class Player {
    
	
	public enum Type {
	    HUMAN(0, "Human player."),
        BOT(1, "Bot with no intelligence."),
        BOT_HARD(2, "Bot with more intelligence.");
        

        private final int ID;
        private final String text;


        private Type(final int ID, final String text) {
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
	
	/*
	 * API
	 * I think I want to get rid of the blank constructors.
	 * 
	 * I want to get rid of getOpponentLocation because that is not scalable.
	 * I think I want to store the player location on the player, because that
	 * makes it easy to pass to another level.
	 * 
	 * I think I'll set the location directly instead of using moveTo
	 */
	//public Player(){}
	//public Player(PlayerType type){}
	
	/**
	 * Create a Player of a given type with a given name.
	 * @param name The name of the Player.
	 * @param type The type of the Player.
	 * @return an unplaced Player.
	 */
	public Player(final String name, final Player.Type type){}

	/**
	 * Get the location of the Player on the board.
	 * 
	 * @return The current location of the Player on the board.
	 */
	public Location getLocation(){ return new Location();}
	
	/**
	 * Place the Player on the board.
	 * 
	 * @requires location is a valid location on the board.
	 * 
	 * @param location is a valid location where the player
	 *        will be placed.
	 * @effects The Player updates its location to the given value.
	 * @modifies Player's location.
	 */
	public void setLocation(final Location location) { }
	
	//public boolean moveTo(Direction direction){ return false; }	
	//public Location getOpponentLocation(){ return new Location(); }
}

