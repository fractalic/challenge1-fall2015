import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Game {

    public enum Mode {

        TWO_PLAYER(0, "TWO_PLAYER"), ONE_PLAYER(1, "ONE_PLAYER"), BOT_BATTLE(2,
                "BOT_BATTLE");

        private final String text;
        private final int ID;

        private Mode(final int ID, final String text) {
            this.text = text;
            this.ID = ID;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return this.text;
        }

    }

    private String  fileName;
    private Mode    mode;
    private Board   board;
    private int     botPlayers   = 0;
    private int     humanPlayers = 0;
    //private boolean ready;
    //private Player  currentPlayer;
    private int     currentPlayerIndex;

    private List<Player> players = new ArrayList<Player>();

    private List<ActionListener> moveListeners = new ArrayList<ActionListener>();
    private List<ActionListener> winListeners  = new ArrayList<ActionListener>();

    private List<Location> movements = new ArrayList<Location>();

    /**
     * Create a new Game with given Mode. Call open() to load a previous game or
     * begin() to begin a new Game.
     * 
     * @param mode
     *            The Game mode, which sets the number of players.
     * @requires mode is not null.
     * @modifies none
     * @return an empty Game in an unplayable state.
     */
    /*public Game(Mode mode) {
        this.mode = mode;
        players = new ArrayList<Player>();
        ready = false;
    }
*/
    /**
     * Determine if it is possible for the current player to move to the
     * location.
     * 
     * @param location
     *            The location to query.
     * @throws InvalidStateException
     *             (unchecked) if the game has not been started using begin().
     * @return true if the current player can move to the given location. false
     *         otherwise.
     */
    /*public boolean canMoveTo(Location destination) {
        if (!ready) {
            throw new InvalidStateException("Cannot move.");
        }

        Location currentLocation = currentPlayer.getLocation();
        Direction directionTo = currentLocation.getDirectionTo(destination);

        if (board.getAvailableDirectionsAt(currentLocation)
                .contains(directionTo)) {
            return true;
        }

        return false;
    }*/

    /**
     * Load the current player and move to the next player.
     * 
     * @requires At least one player has been added to the game, and the
     *           starting player has been selected.
     * @modifies Current player.
     * @effects Prepares the game for the player whose turn is next.
     */
    /*private void nextPlayer() {
        currentPlayer = players.get(currentPlayerIndex);
        currentPlayerIndex = (++currentPlayerIndex) % players.size();
    }*/

    /**
     * Move the current player to the given location if the current player is a
     * human.
     * 
     * @effects Current player is moved if the move is valid, and turn passes to
     *          appropriate player.
     */
    /*public void requestMove(Location movement) {
        if (canMoveTo(movement)) {
            moveTo(movement);
        }
    }*/

    /**
     * Move the current player to a location.
     * 
     * @requires It is allowable to move the current player to the given
     *           location.
     * @modifies The location of the current player.
     * @throws InvalidStateException
     *             (unchecked) if it is not possible to make the requested move.
     */
    /*public void moveTo(Location destination) {
        if (!canMoveTo(destination)) {
            throw new InvalidStateException(
                    "Cannot move to requested location: "
                            + destination.toString());
        }

        Location previousLocation = currentPlayer.getLocation();

        currentPlayer.setLocation(destination);
        board.setStateAt(destination, Board.LocationState.UNAVAILABLE);

        this.notifyPlayerMoveListeners(previousLocation, currentPlayer);

        nextPlayer();

        if (currentPlayer.getType() == Player.Type.BOT) {
            this.moveRandom();
        }
    }*/

    /**
     * Move the current player to a location.
     * 
     * @requires It is allowable to move the current player to the given
     *           location.
     * @modifies The location of the current player.
     * @throws InvalidStateException
     *             (unchecked) if it is not possible to make the requested move.
     */
    /*private void moveToReplay(Location destination) {
        if (!canMoveTo(destination)) {
            throw new InvalidStateException(
                    "Cannot move to requested location: "
                            + destination.toString());
        }

        Location previousLocation = currentPlayer.getLocation();

        currentPlayer.setLocation(destination);
        board.setStateAt(destination, Board.LocationState.UNAVAILABLE);

        //this.notifyPlayerMoveListeners(previousLocation, currentPlayer);

        nextPlayer();
    }*/

    /**
     * Move the current player in a random direction.
     * 
     * @requires The player is a bot.
     * @modifies The location of the current player.
     * @throws InvalidStateException
     *             (unchecked) if it is not possible to make the requested move.
     */
    private void moveRandom() {
        Player currentPlayer = players.get(currentPlayerIndex);
        if (currentPlayer.getType() != Player.Type.BOT) {
            throw new InvalidStateException(
                    "Player must be " + Player.Type.BOT.toString());
        }

        Location previousLocation = currentPlayer.getLocation();

        List<Direction> directions = board
                .getAvailableDirectionsAt(previousLocation);
        Direction randomDirection = directions
                .get((int) Math.ceil(Math.random() * (double) directions.size())
                        - 1);

        Location newLocation = previousLocation.cloneOffset(randomDirection);
        currentPlayer.setLocation(newLocation);
        board.setStateAt(newLocation, Board.LocationState.UNAVAILABLE);

        //this.notifyPlayerMoveListeners(previousLocation, currentPlayer);

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
     * Check if the current Game is finished, which occurs when one player is
     * guaranteed win no matter what moves either player makes.
     * 
     * @return true if one player is guaranteed to win.
     */
    public boolean isFinished() {
        return false;
    }

    /**
     * Return the winning Player.
     * 
     * @requires The game must be finished, as determined by isFinished().
     * @throws InvalidStateException
     *             (unchecked) if game has not finished.
     * @return A copy of the winning Player.
     */
    public Player getWinner() {
        return null;
    }

    /**
     * Add a board to the game.
     * 
     * @param board
     *            The board to be added to the game.
     * @requires No boards have been added to the game.
     * @throws InvalidStateException
     *             (unchecked) if the game already has an associated Board.
     */
    /*public void addBoard(Board board) {
        if (this.board != null) {
            throw new InvalidStateException("Cannot add more than one board.");
        }

        this.board = new Board(board.getDimension());
    }*/

    /**
     * Add a player to the game.
     * 
     * @param player
     *            The player to add to the game.
     * @requires The number of added Players must not exceed the Player limit
     *           and must match the player types specified by the Game Mode
     * @throws InvalidStateException
     *             (unchecked) if limit of players for this game mode is already
     *             met, or the Player types do not match the required types.
     */
    /*public void addPlayer(Player player) {
        if (this.mode == Mode.ONE_PLAYER) {
            if (this.humanPlayers == 1
                    && player.getType() == Player.Type.HUMAN) {
                throw new InvalidStateException("Too many human players.");
            }
            if (this.botPlayers == 1 && (player.getType() == Player.Type.BOT
                    || player.getType() == Player.Type.BOT_HARD)) {
                throw new InvalidStateException("Too many bot players.");
            }

        } else if (this.mode == Mode.TWO_PLAYER) {
            if (this.humanPlayers == 2
                    && player.getType() == Player.Type.HUMAN) {
                throw new InvalidStateException("Too many human players.");
            }
            if (player.getType() == Player.Type.BOT
                    || player.getType() == Player.Type.BOT_HARD) {
                throw new InvalidStateException("Too many bot players.");
            }

        } else if (this.mode == Mode.BOT_BATTLE) {
            if (player.getType() == Player.Type.HUMAN) {
                throw new InvalidStateException("Too many human players.");
            }
            if (this.botPlayers == 2 && (player.getType() == Player.Type.BOT
                    || player.getType() == Player.Type.BOT_HARD)) {
                throw new InvalidStateException("Too many bot players.");
            }
        }

        this.players.add(player.clone());

    }*/

    /**
     * Get the Player for the current turn.
     * 
     * @requires The game is in a playable state.
     * @throws InvalidStateException
     *             (unchecked) if the game is not playable (uninitialized or
     *             finished).
     * @return The Player that will move next.
     */
    /*public Player getCurrentPlayer() {
        return currentPlayer.clone();
    }*/

    /**
     * Save the current state of the game
     * 
     * @param fileName
     *            The file name in which to save the game state.
     * @requires The game is in a playable or finished state.
     * @throws InvalidStateException
     *             (unchecked) if the game is not finished and is not playable.
     * @effects The Game state is written to fileName.
     */
    void save(String fileName) throws IOException {
        /**
         * @source http://stackoverflow.com/questions/2885173/\
         *         how-to-create-a-file-and-write-to-a-file-in-java
         */
        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        writer.print(board.serialize(this.getMode()));
        writer.close();
    }

    /**
     * Clear the Board, reset the Player positions, and optionally replay the
     * game if it was loaded from a file.
     * 
     * @requires A board and at least one player has been added to the game.
     * @param replay
     *            Choose whether to simulate the play of a loaded game or begin
     *            immediately from loaded state.
     * @param location1
     *            Initial location of player 1.
     * @param location2
     *            Initial location of player 2.
     * @modifies Board state, Player positions.
     * @effects Sets the initial state to make the game ready to play, and
     *          optionally simulates all loaded plays.
     * @throws InvalidStateException
     *             (unchecked) if there is no board or no players.
     */
    /*public void begin(Boolean replay, Location location1, Location location2) {
        // TODO: implement load and replay

        if (board == null || players.size() != 2) {
            throw new InvalidStateException(
                    "Cannot begin a game with missing board or players");
        }

        ready = true;
        currentPlayerIndex = 0;
        nextPlayer();
        currentPlayer.setLocation(location1);
        board.setStateAt(currentPlayer.getLocation(),
                Board.LocationState.UNAVAILABLE);
        nextPlayer();
        currentPlayer.setLocation(location2);
        board.setStateAt(currentPlayer.getLocation(),
                Board.LocationState.UNAVAILABLE);
        nextPlayer();
    }*/

    /**
     * Replay an already saved game specified by fileName. Parses the file
     * specified by fileName.
     * 
     * @param fileName
     *            an absolute path to the game file to be opened
     */
    public void replay(String filename) throws IOException {

        /* To save the game to the same file later if desired */
        this.fileName = filename;

        /* Just the scanner code. */
        Path path = Paths.get(fileName);
        Scanner scanner = new Scanner(path);

        String[] lineContents;

        boolean passedInitialMove = false;
        int player = 1;

        // read file line by line
        scanner.useDelimiter(System.getProperty("line.separator"));
        while (scanner.hasNext()) {
            String line = scanner.next();
            if (line.contains("END_BOARD")) {
                passedInitialMove = true;
            }
            if (player == 1) {
                if (line.contains("P1_LOCATION")) {
                    if (passedInitialMove) {
                        lineContents = line.split(": ");
                        movements.add(Location.fromString(lineContents[1],
                                this.board.getDimension() - 1));
                    }
                }
                player = 2;
            } else {
                if (line.contains("P2_LOCATION")) {
                    if (passedInitialMove) {
                        lineContents = line.split(": ");
                        movements.add(Location.fromString(lineContents[1],
                                this.board.getDimension() - 1));
                    }
                }
                player = 1;
            }
        }
        scanner.close();
        for (Location move : movements) {
            //moveToReplay(move);
        }
    }

    /**
     * Get the dimension parameter from a saved game file.
     * 
     * @param filename
     *            Absolute path to filename to get dimension from.
     */
    public static int getDimensionFromFile(String filename) throws IOException {

        /* Just the scanner code. */
        Path path = Paths.get(filename);
        Scanner scanner = new Scanner(path);

        String[] lineContents;
        int dimension = 1;

        // read file line by line
        scanner.useDelimiter(System.getProperty("line.separator"));
        while (scanner.hasNext()) {
            String line = scanner.next();
            if (line.contains("DIMENSION")) {
                lineContents = line.split(": ");
                dimension = Integer.valueOf(lineContents[1]);
            }
        }
        scanner.close();
        return dimension;
    }

    /**
     * Get the dimension parameter from a saved game file.
     * 
     * @param filename
     *            Absolute path to filename to get dimension from.
     */
    public static Mode getModeFromFile(String filename) throws IOException {
        Mode mode = Mode.TWO_PLAYER;
        String[] lineContents;
        String modeString = Mode.TWO_PLAYER.toString();

        /* Just the scanner code. */
        Path path = Paths.get(filename);
        Scanner scanner = new Scanner(path);

        // read file line by line
        scanner.useDelimiter(System.getProperty("line.separator"));
        while (scanner.hasNext()) {
            String line = scanner.next();
            if (line.contains("MODE")) {
                lineContents = line.split(": ");
                modeString = lineContents[1];
            }
        }
        scanner.close();

        if (modeString.equals(Game.Mode.TWO_PLAYER.toString())) {
            mode = Game.Mode.TWO_PLAYER;
        } else if (modeString.equals(Game.Mode.ONE_PLAYER.toString())) {
            mode = Game.Mode.ONE_PLAYER;
        } else {
            mode = Game.Mode.BOT_BATTLE;
        }

        return mode;
    }

    /**
     * Subscribe to player position updates.
     * 
     * @param listener
     *            Function to receive updates about player movements.
     *//*
    public void addPlayerMoveListener(PlayerMoveListener listener) {
        playerMoveListeners.add(listener);
    }*/

    /**
     * Notify all listeners subscribed to player movement.
     *//*
    public void notifyPlayerMoveListeners(Location previousLocation,
            Player player) {
        for (PlayerMoveListener listener : playerMoveListeners) {
            listener.playerMovePerformed(previousLocation, player);
        }
    }*/

    /**
	 * THE NEW API
	 */
    /**
     * Create a new game.
     * @param mode The mode of the game.
     * @param dimension The dimension of one edge of the square board.
     * @param P1Name The name of player 1. Used to uniquely identify the player.
     * @param P2Name The name of player 2. Used to uniquely identify the player.
     */
    public Game(final Game.Mode mode, final int dimension, final String P1Name, final String P2Name) {
        this.mode = mode;
        
        if (mode == Mode.TWO_PLAYER) {
            addHumanPlayer(P1Name);
            addHumanPlayer(P2Name);
        } else if (mode == Mode.ONE_PLAYER) {
            addHumanPlayer(P1Name);
            addBotPlayer(P2Name);
        } else {
            addBotPlayer(P1Name);
            addBotPlayer(P2Name);
        }
        
        this.board = new Board(dimension);
    }
    
    /**
     * Set the player positions and begin the game.
     * A game cannot be loaded after begin has been called.
     * 
     * @param p1Location
     *            Initial location of player 1.
     * @param p2Location
     *            Initial location of player 2.
     */
    public void begin(Location p1Location, Location p2Location) {
        currentPlayerIndex = 0;
        
        players.get(0).setLocation(p1Location);
        board.setStateAt(p1Location,
                Board.LocationState.UNAVAILABLE);
        
        players.get(1).setLocation(p2Location);
        board.setStateAt(p2Location,
                Board.LocationState.UNAVAILABLE);
    }

    /**
     * Attempt to move the current player to the given location.
     * 
     * @param destination The location to move the player to.
     * @return true if the player was moved.
     *         false otherwise.
     */
    public boolean requestMove(Location destination) {
        if (players.get(currentPlayerIndex).getType() == Player.Type.HUMAN) {
            if (canMove(players.get(currentPlayerIndex), destination)) {
                takeTurn(destination);
                return true;
            }
        }
        return false;
    }
    
    public void save() {
        throw new InvalidStateException("not yet implemented");
    }
    public void load() {
        throw new InvalidStateException("not yet implemented");
    }
    
    /**
     * Subscribe to movements made on board.
     * 
     * @param moveListener contains actions to take when a move occurs.
     */
    public void addMoveListener(ActionListener moveListener) {
        moveListeners.add(moveListener);
    }
    
    /**
     * Subscribe to the game ending.
     * 
     * @param winListener contains actions to take when a win occurs.
     */
    public void addWinListener(ActionListener winListener) {
        winListeners.add(winListener);
    }
    
    
    /**
     * Internal functions.
     */
    
    /**
     * Add a human player to the game.
     * @param name The name of the player to be added to the game.
     */
    private void addHumanPlayer(String name) {
        this.players.add(new Player(name, Player.Type.HUMAN));
    }
    
    /**
     * Add a bot player to the game.
     * @param name The name of the player to be added to the game.
     */
    private void addBotPlayer(String name) {
        this.players.add(new Player(name, Player.Type.BOT));
    }
    
    /**
     * Notify all subscribers that a movement has been made.
     * A copy of the player at its new location is shared as the event source.
     */
    private void notifyMoveListeners() {
        for (ActionListener moveListener : moveListeners) {
            ActionEvent e = new ActionEvent(players.get(currentPlayerIndex).clone(), 0, "");
            moveListener.actionPerformed(e);
        }
    }
    
    /**
     * Notify all subscribers that the game has been won.
     * A copy of the winning player is shared as the event source.
     */
    private void notifyWinListeners() {
        for (ActionListener winListener : winListeners) {
            ActionEvent e = new ActionEvent(players.get(currentPlayerIndex).clone(), 0, "");
            winListener.actionPerformed(e);
        }
    }
    
    /**
     * Determine if it is possible for the current player to move to the
     * location.
     * 
     * @param location
     *            The location to query.
     * @return true if the current player can move to the given location. false
     *         otherwise.
     */
    private boolean canMove(Player player, Location destination) {
        Location sourceLocation = player.getLocation();
        Direction directionTo = sourceLocation.getDirectionTo(destination);

        if (board.getAvailableDirectionsAt(sourceLocation)
                .contains(directionTo)) {
            return true;
        }

        return false;
    }
    
    /**
     * Take the turn of the current player and move to next player.
     * 
     * @param destination The movement the current player should make.
     * @requires destination is a valid movement for the current player.
     */
    private void takeTurn(Location destination) {
        players.get(currentPlayerIndex).setLocation(destination);
        notifyMoveListeners();
        nextPlayer();
    }
    
    /**
     * Select the next player.
     */
	private void nextPlayer() {
	    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
	}
}
