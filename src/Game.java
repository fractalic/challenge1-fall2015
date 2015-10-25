import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class Game {
	
	public enum Mode {
		
		TWO_PLAYER(0, "Two Player"),
		ONE_PLAYER(1, "One Player"),
		BOT_BATTLE(2, "Bot Battle");
		

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


	public Game(){}
	
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
	public Game(Mode mode){}
	
	/**
	 * Return the current mode of the Game.
	 * 
	 * @return The current mode of the Game, dictating the number of players.
	 */
	public Mode getMode(){ return null; }

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
	public void addBoard(Board board) {}
	
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
     * @param replay Choose whether to simulate the play of a loaded
     *        game or begin immediately from loaded state.
     * @modifies Board state, Player positions.
     * @effects Sets the initial state to make the game ready to play,
     *          and optionally simulates all loaded plays.
     */
    public void begin(Boolean replay) {}
	
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
	
	private String fileName;
	
}

