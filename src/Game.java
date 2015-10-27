import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Game {
	
	public enum Mode {
		
		TWO_PLAYER(0, "TWO_PLAYER"),
		ONE_PLAYER(1, "ONE_PLAYER"),
		BOT_BATTLE(2, "BOT_BATTLE");
		

	    private final String text;
	    private final int ID;


	    private Mode(final int ID, final String text) {
	        this.text = text;
	        this.ID = ID;
	    }

	    /* (non-Javadoc)
	     * @see java.lang.Enum#toString()
	     */
	    @Override
	    public String toString() {
	        return this.text;
	    }
	    
	}

    private String fileName;
	private Mode   mode;
	private Board  board;
	private int botPlayers = 0;
	private int humanPlayers = 0;
	private boolean ready;
	private Player currentPlayer;
	private int currentPlayerIndex;
	
	private List<Player> players;
	
	private List<PlayerMoveListener> playerMoveListeners = new ArrayList<PlayerMoveListener>();
	
	/**
	 * Create a new Game with given Mode.
	 * Call open() to load a previous game
	 * or begin() to begin a new Game.
	 * 
	 * @param mode The Game mode, which sets the number of players.
	 * @requires mode is not null.
	 * @modifies none
	 * @return an empty Game in an unplayable state.
	 */
	public Game(Mode mode){
	    this.mode = mode;
	    players = new ArrayList<Player>();
	    ready = false;
	}
	
	/**
	 * Determine if it is possible for the current player to move to the location.
	 * 
	 * @param location The location to query.
	 * @return true if the current player can move to the given location.
	 *         false otherwise.
	 */
	public boolean canMoveTo(Location destination) {
	    Location currentLocation = currentPlayer.getLocation();
	    Direction directionTo = currentLocation.getDirectionTo(destination);
	    
	    if (board.getAvailableDirectionsAt(
	              currentLocation).contains(directionTo)) {
	        return true;
	    }
	    
	    return false;
	}
	
	/**
	 * Move the current player to a location.
	 * 
	 * @requires It is allowable to move the current player to the given location.
	 * @modifies The location of the current player.
	 * @throws InvalidStateException (unchecked) if it is not possible to make
	 *         the requested move.
	 */
	public void moveTo(Location destination) {
	    if (!canMoveTo(destination)) {
	        throw new InvalidStateException(
	                  "Cannot move to requested location: " +
	                  destination.toString());
	    }
	    
	    Location previousLocation = currentPlayer.getLocation();
	    
	    currentPlayer.setLocation(destination);
	    board.setStateAt(destination, Board.LocationState.UNAVAILABLE);
	    
	    this.notifyPlayerMoveListeners(previousLocation, currentPlayer);
	    
	    nextPlayer();
	}
	
	/**
	 * Return the current mode of the Game.
	 * 
	 * @return The current mode of the Game, dictating the number of players.
	 */
	public Mode getMode() {
	    return this.mode;
	}

	/**
	 * Check if the current Game is finished, which occurs
	 * when one player is guaranteed win no matter what moves either player makes.
	 * 
	 * @return true if one player is guaranteed to win.
	 */
	public boolean isFinished(){ return false; }
	
	/**
	 * Return the winning Player.
	 * 
	 * @requires The game must be finished, as determined by isFinished().
	 * @throws InvalidStateException (unchecked) if game has not finished.
	 * @return A copy of the winning Player.
	 */
	public Player getWinner(){ return null; }
	
	/**
	 * Add a board to the game.
	 * 
	 * @param board The board to be added to the game.
	 * @requires No boards have been added to the game.
	 * @throws InvalidStateException (unchecked) if the game already has an associated Board.
	 */
	public void addBoard(Board board) {
	    if (this.board != null) {
	        throw new InvalidStateException("Cannot add more than one board.");
	    }
	    
	    this.board = new Board(board.getDimension());
	}
	
	/**
	 * Add a player to the game.
	 * 
	 * @param player The player to add to the game.
	 * @requires The number of added Players must not exceed the Player limit
	 *           and must match the player types specified by the Game Mode
	 * @throws InvalidStateException (unchecked) if limit of players for this game mode is
	 *         already met, or the Player types do not match the required types.
	 */
	public void addPlayer(Player player) {
	    if (this.mode == Mode.ONE_PLAYER) {
	        if (this.humanPlayers == 1 && player.getType() == Player.Type.HUMAN) {
	            throw new InvalidStateException("Too many human players.");
	        }
	        if (this.botPlayers == 1 &&
	            (player.getType() == Player.Type.BOT ||
	             player.getType() == Player.Type.BOT_HARD) ) {
                throw new InvalidStateException("Too many bot players.");
            }
	        
	    } else if (this.mode == Mode.TWO_PLAYER) {
	        if (this.humanPlayers == 2 && player.getType() == Player.Type.HUMAN) {
                throw new InvalidStateException("Too many human players.");
            }
            if (player.getType() == Player.Type.BOT ||
                 player.getType() == Player.Type.BOT_HARD) {
                throw new InvalidStateException("Too many bot players.");
            }
            
	    } else if (this.mode == Mode.BOT_BATTLE) {
	        if (player.getType() == Player.Type.HUMAN) {
                throw new InvalidStateException("Too many human players.");
            }
            if (this.botPlayers == 2 &&
                (player.getType() == Player.Type.BOT ||
                 player.getType() == Player.Type.BOT_HARD) ) {
                throw new InvalidStateException("Too many bot players.");
            }
	    }
	    
        this.players.add(player.clone());
	    
	}
	
	/**
	 * Get the Player for the current turn.
	 * 
	 * @requires The game is in a playable state.
	 * @throws InvalidStateException (unchecked) if the game is not playable
	 *         (uninitialized or finished).
	 * @return The Player that will move next.
	 */
	public Player getCurrentPlayer() { 
	    return currentPlayer.clone();
	}

	/**
	 * Save the current state of the game
	 * 
	 * @param fileName The file name in which to save the game state.
	 * @requires The game is in a playable or finished state.
	 * @throws InvalidStateException (unchecked) if the game is not
	 *         finished and is not playable.
	 * @effects The Game state is written to fileName.
	 */
	void save(String fileName) throws IOException {}
	
	/**
	 * Clear the Board, reset the Player positions,
	 * and replay the game if it was loaded from a file.
	 * 
	 * @modifies Board state, Player positions.
	 * @effects Sets the initial state to make the game ready to play,
	 *          and simulates all loaded plays.
	 */
	public void begin() {
	    Boolean doReplay = true;
	    this.begin(doReplay);
	}
	
	/**
     * Clear the Board, reset the Player positions,
     * and optionally replay the game if it was loaded from a file.
     * 
     * @requires A board and at least one player has been added to the game.
     * @param replay Choose whether to simulate the play of a loaded
     *        game or begin immediately from loaded state.
     * @modifies Board state, Player positions.
     * @effects Sets the initial state to make the game ready to play,
     *          and optionally simulates all loaded plays.
     * @throws InvalidStateException (unchecked) if there is no board or
     *         no players.
     */
    public void begin(Boolean replay) {
        // TODO: implement load and replay
        
        if (board == null || players.size() == 0) {
            throw new InvalidStateException(
                      "Cannot begin a game with missing board or players");
        }
        
        ready = true;
        currentPlayerIndex = 0;
        nextPlayer();
        currentPlayer.setLocation(new Location(board.getDimension() / 2,
                                 board.getDimension() - 1,
                                 board.getDimension() - 1) );
        nextPlayer();
        currentPlayer.setLocation(new Location(board.getDimension() / 2,
                                 0,
                                 board.getDimension() - 1) );
        nextPlayer();
    }
    
    /**
     * Load the current player and move to the next player.
     * 
     * @requires At least one player has been added to the game,
     *           and the starting player has been selected.
     * @modifies Current player.
     * @effects Prepares the game for the player whose turn is next.
     */
    private void nextPlayer() {
        currentPlayer = players.get(currentPlayerIndex);
        currentPlayerIndex = (++currentPlayerIndex) % players.size();
    }
	
	/**
	 * Opens an already saved game specified by fileName. 
	 * Parses the file specified by fileName. If fileName is a valid 
	 * game, it initializes a game and simulates it up to the last 
	 * move specified in the file.
	 * @param  fileName an absolute path to the game file to be opened 
	 * @return true if file is valid game and opened successfully, 
	 * 		   false otherwise 
	 */
	public void open(String fileName) throws IOException{

		/* To save the game to the same file later if desired */
		this.fileName = fileName; 

		/* Just the scanner code. */
		Path path = Paths.get(fileName);
		Scanner scanner = new Scanner(path);
         
		// read file line by line
		scanner.useDelimiter(System.getProperty("line.separator"));
		while(scanner.hasNext()){
			String line = scanner.next();
			/* … */
		}
		scanner.close();
	}
	
	/**
	 * Subscribe to player position updates.
	 * 
	 * @param listener Function to receive updates about player movements.
	 */
	public void addPlayerMoveListener(PlayerMoveListener listener) {
	    playerMoveListeners.add(listener);
	}
	
	/**
	 * Notify all listeners subscribed to player movement.
	 */
	public void notifyPlayerMoveListeners(Location previousLocation, Player player) {
	    for (PlayerMoveListener listener : playerMoveListeners) {
	        listener.playerMovePerformed(previousLocation, player);
	    }
	}
	
	/*
     * API
     * - save
     * - open
     * - playback
     * - check if playing back
     * - make a move
     * - check who's turn it is
     * -check the current mode
     * - check if game finished
     * - check who won
     * - set playback speed ?
     * I don't know what set board and set player are. That would expose
     * the internal representation.
     * 
     * I'll need to implement my own callbacks for replay mode.
     * 
     * Private
     * -
     */
    
	
	
}

