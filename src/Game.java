import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

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

    private String fileName;
    private Mode   mode;
    private Board  board;
    private int    currentPlayerIndex;
    private Timer  botTimer = new Timer();

    private List<Player> players = new ArrayList<Player>();

    private List<ActionListener> moveListeners = new ArrayList<ActionListener>();
    private List<ActionListener> winListeners  = new ArrayList<ActionListener>();

    private Player winningPlayer = null;

    // private List<Location> movements = new ArrayList<Location>();

    /**
     * Check if the current Game is finished, which occurs when one player is
     * guaranteed win no matter what moves either player makes.
     * 
     * @return true if one player is guaranteed to win.
     */
    private boolean isFinished() {
        List<Player> stuckPlayers = new ArrayList<Player>();
        for (Player candidatePlayer : players) {
            if (board.getAvailableDirectionsAt(candidatePlayer.getLocation())
                    .isEmpty()) {
                stuckPlayers.add(candidatePlayer);
            }
        }
        if (stuckPlayers.size() > 0) {
            winningPlayer = stuckPlayers.contains(players.get(0))
                    ? players.get(1) : players.get(0);
            botTimer.cancel();
            return true;
        } else {
            return false;
        }
    }

    /******************************************
     * THE NEW API
     */

    /**
     * Create a new game.
     * 
     * @param mode
     *            The mode of the game.
     * @param dimension
     *            The dimension of one edge of the square board.
     * @param P1Name
     *            The name of player 1. Used to uniquely identify the player.
     * @param P2Name
     *            The name of player 2. Used to uniquely identify the player.
     */
    public Game(final Game.Mode mode, final int dimension, final String P1Name,
            final String P2Name) {
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
     * 
     * @param p1Location
     *            Initial location of player 1.
     * @param p2Location
     *            Initial location of player 2.
     */
    public void begin(Location p1Location, Location p2Location) {
        currentPlayerIndex = 0;

        players.get(0).setLocation(p1Location);
        board.setStateAt(p1Location, Board.LocationState.UNAVAILABLE);

        players.get(1).setLocation(p2Location);
        board.setStateAt(p2Location, Board.LocationState.UNAVAILABLE);

        if (mode == Mode.BOT_BATTLE) {
            takeTurnBot();
        }
    }

    /**
     * Set the player positions and begin the game.
     * 
     * @param filename
     *            The path to a game file.
     */
    public void begin(String filename) throws IOException {

        /* To save the game to the same file later if desired */
        this.fileName = filename;

        /* Just the scanner code. */
        Path path = Paths.get(fileName);
        Scanner scanner = new Scanner(path);

        String[] lineContents;

        Location p1Location = null;
        Location p1PrevLocation = null;

        Location p2Location = null;
        Location p2PrevLocation = null;

        // read file line by line
        scanner.useDelimiter(System.getProperty("line.separator"));
        while (scanner.hasNext()) {
            String line = scanner.next();
            lineContents = line.split(": ");

            if (line.contains("P1_LOCATION")) {
                p1Location = Location.fromString(lineContents[1],
                        this.board.getDimension() - 1);
            } else if (line.contains("P2_LOCATION")) {
                p2Location = Location.fromString(lineContents[1],
                        this.board.getDimension() - 1);
            }

            if (p1Location != p1PrevLocation) {
                players.get(0).setLocation(p1Location);
                board.setStateAt(p1Location, Board.LocationState.UNAVAILABLE);
                notifyMoveListeners();
                nextPlayer();
            }
            if (p2Location != p2PrevLocation) {
                players.get(1).setLocation(p2Location);
                board.setStateAt(p2Location, Board.LocationState.UNAVAILABLE);
                notifyMoveListeners();
                nextPlayer();
            }

            p1PrevLocation = p1Location;
            p2PrevLocation = p2Location;
        }

        scanner.close();

        if (mode == Mode.BOT_BATTLE) {
            takeTurnBot();
        }
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
        if (!isFinished()) {
            throw new InvalidStateException("This is not over!");
        }
        return winningPlayer.clone();
    }

    /**
     * Get the player whose turn it currently is.
     * 
     * @return A copy of the current player.
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex).clone();
    }

    /**
     * Attempt to move the current player to the given location.
     * 
     * @param destination
     *            The location to move the player to.
     * @return true if the player was moved. false otherwise.
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

    /**
     * Subscribe to movements made on board.
     * 
     * @param moveListener
     *            contains actions to take when a move occurs.
     */
    public void addMoveListener(ActionListener moveListener) {
        moveListeners.add(moveListener);
    }

    /**
     * Subscribe to the game ending.
     * 
     * @param winListener
     *            contains actions to take when a win occurs.
     */
    public void addWinListener(ActionListener winListener) {
        winListeners.add(winListener);
    }

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
        writer.print(board.serialize(mode));
        writer.close();
    }

    /******************************************
     * Internal functions.
     */

    /**
     * Add a human player to the game.
     * 
     * @param name
     *            The name of the player to be added to the game.
     */
    private void addHumanPlayer(String name) {
        this.players.add(new Player(name, Player.Type.HUMAN));
    }

    /**
     * Add a bot player to the game.
     * 
     * @param name
     *            The name of the player to be added to the game.
     */
    private void addBotPlayer(String name) {
        this.players.add(new Player(name, Player.Type.BOT));
    }

    /**
     * Notify all subscribers that a movement has been made. A copy of the
     * player at its new location is shared as the event source.
     */
    private void notifyMoveListeners() {
        for (ActionListener moveListener : moveListeners) {
            ActionEvent e = new ActionEvent(
                    players.get(currentPlayerIndex).clone(), 0, "");
            moveListener.actionPerformed(e);
        }
    }

    /**
     * Notify all subscribers that the game has been won. A copy of the winning
     * player is shared as the event source.
     */
    private void notifyWinListeners() {
        for (ActionListener winListener : winListeners) {
            ActionEvent e = new ActionEvent(winningPlayer.clone(), 0, "");
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
     * @param destination
     *            The movement the current player should make.
     * @requires destination is a valid movement for the current player.
     */
    private void takeTurn(Location destination) {
        players.get(currentPlayerIndex).setLocation(destination);
        notifyMoveListeners();
        board.setStateAt(destination, Board.LocationState.UNAVAILABLE);

        if (!isFinished()) {
            nextPlayer();
            if (players.get(currentPlayerIndex)
                    .getType() != Player.Type.HUMAN) {
                takeTurnBot();
            }
        } else {
            notifyWinListeners();
        }
    }

    /**
     * A bot will take it's turn if it is the current player.
     */
    private void takeTurnBot() {
        Location destination;
        if (players.get(currentPlayerIndex).getType() == Player.Type.BOT) {
            destination = getBotMove();
        } else if (players.get(currentPlayerIndex)
                .getType() == Player.Type.BOT_HARD) {
            destination = getBotHardMove();
        } else {
            return;
        }
        botTimer.schedule(new TimerTask() {

            public final void run() {
                takeTurn(destination);
            }
        }, 1000);
    }

    /**
     * Determine a movement for a bot.
     * 
     * @return A random available location adjacent to the current player's
     *         location.
     */
    private Location getBotMove() {
        Player currentPlayer = players.get(currentPlayerIndex);

        Location previousLocation = currentPlayer.getLocation();

        List<Direction> directions = board
                .getAvailableDirectionsAt(previousLocation);
        Direction randomDirection = directions
                .get((int) Math.ceil(Math.random() * (double) directions.size())
                        - 1);

        return previousLocation.cloneOffset(randomDirection);
    }

    /**
     * Determine a movement for a tricky bot.
     */
    private Location getBotHardMove() {
        return getBotMove();
    }

    /**
     * Select the next player.
     */
    private void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    /******************************************
     * UTILITIES
     */

    /**
     * Get the dimension parameter from a saved game file.
     * 
     * @param filename
     *            Absolute path to filename to get dimension from.
     * @throws InvalidStateException
     *             (unchecked) if the game dimension cannot be determined from
     *             the given file.
     */
    public static int getDimensionFromFile(String filename) throws IOException {

        /* Just the scanner code. */
        Path path = Paths.get(filename);
        Scanner scanner = new Scanner(path);

        String[] lineContents;
        int dimension = 0;

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

        if (dimension <= 0) {
            throw new InvalidStateException(
                    "Dimension cannot be determine from file: " + filename);
        }
        return dimension;
    }

    /**
     * Get the dimension parameter from a saved game file.
     * 
     * @param filename
     *            Absolute path to filename to get dimension from.
     * @throws InvalidStateException
     *             (unchecked) if the game mode cannot be determined from the
     *             given file.
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
        } else if (modeString.equals(Game.Mode.BOT_BATTLE.toString())) {
            mode = Game.Mode.BOT_BATTLE;
        } else {
            throw new InvalidStateException(
                    "Cannot get mode from file " + filename);
        }

        return mode;
    }
}
