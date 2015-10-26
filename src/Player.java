import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a player in the game.
 * Player's can be placed at arbitrary locations, including outside the bounds
 * of the board.
 * 
 * @author benhughes
 * 
 * @invariant Once set, the Player's location is always defined.
 * @invariant The Player's name is never undefined.
 * @invariant The Player's name never changes.
 * @invariant The Player's type is never undefined.
 * @invariant The Player's type never changes.
 * @invariant The Player's unique-ID never changes.
 * @invariant The shared-ID is copied to any cloned Players. This allows clone
 *            receivers to match a returned Player clone to a local Player.
 *
 */
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
	
	private static int        NEXT_UNIQUE_ID = 1;
	private final int         uniqueID;
	private int               sharedID;
	private Location          location;
	private final String      name;
	private final Player.Type type;
	
	/**
	 * Create a Player of a given type with a given name.
	 * @param name The name of the Player.
	 * @param type The type of the Player.
	 * @return an unplaced Player.
	 */
	public Player(final String name, final Player.Type type){
	    this.name = name;
	    this.type = type;
	    
	    this.uniqueID = NEXT_UNIQUE_ID;
	    this.sharedID = this.uniqueID;
	    NEXT_UNIQUE_ID++;
	}
	
    /**
     * Create a copy of the player, if its location has been set.
     * 
     * @throws InvalidStateException (unchecked) If this Player's
     *         location has not been set.
     * @return A copy of the player with same shared-ID, name, type and location
     *         but distinct unique-ID.
     */
    public Player clone() {
        if (this.location == null) {
            throw new InvalidStateException("Cannot clone player.");
        }
        Player clonedPlayer = new Player(this.name, this.type);
        clonedPlayer.sharedID = this.sharedID;
        clonedPlayer.setLocation(this.location);
        return clonedPlayer;
    }
    
    /**
     * Get the id of this Player.
     * 
     * @return The unique id of this Player.
     */
    public int getID() {
        return this.uniqueID;
    }
    
    /**
     * Get the shared ID of this Player.
     * The shared ID can be used to determine if two players represent
     * the same data, while preventing internal representation exposure.
     */
    public int getSharedID() {
        return this.sharedID;
    }

	/**
	 * Get the location of the Player on the board.
	 * 
	 * @throws InvalidStateException (unchecked) if the Player's location
	 *         has not yet been set.
	 * @return The current location of the Player on the board.
	 */
	public Location getLocation() { 
	    if (this.location == null) {
	        throw new InvalidStateException("Player's location has not been set.");
	    }

	    return location.clone();
	}
	
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
	public void setLocation(final Location location) {
	    this.location = location.clone();
	}
	
	/**
	 * Get the name of the player.
	 * 
	 * @return The name of the player.
	 */
	public String getName() {
	    return this.name;
	}
	
	/**
	 * Get the type of the player.
	 * 
	 * @return The type of the player.
	 */
	public Player.Type getType() {
	    return this.type;
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
	//public boolean moveTo(Direction direction){ return false; }	
	//public Location getOpponentLocation(){ return new Location(); }
}

