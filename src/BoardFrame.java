import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class BoardFrame extends JFrame{

	private  String title           = "CPEN221 Game";
	
	private final String PLAYER1_MARKER  = "P1";
	private final String PLAYER2_MARKER  = "P2";
	private String       PLAYER1_NAME    = "P1";
	private String       PLAYER2_NAME    = "P2";
	private int          PLAYER1_ID;
	private int          PLAYER2_ID;

	private final String STATUS = "";
	
	private final Color PLAYER1_COLOR  = Color.RED;
	private final Color PLAYER2_COLOR  = Color.LIGHT_GRAY;
	
	private final int BUTTON_HORIZONTAL_GAP = 0;
	private final int BUTTON_VERTICAL_GAP   = 0;
	private final int BUTTON_DIMENSION      = 40;
	
	private int rowCount    = 1;
	private int columnCount = 1;
	private int dimension   = 1;
	
	JPanel masterPanel = null;
	JPanel boardPanel  = null; 
	JPanel infoPanel   = null;
	
	Map<JButton, Location> buttonPoint;
	
	private Game game;
	
	

	/**
	 * Create the application.
	 */
	public BoardFrame(int rowCount, int columnCount, Game.Mode mode) {
		
		this.rowCount    = rowCount;
		this.columnCount = columnCount;
		this.dimension   = rowCount;
		this.title += " - " + mode.toString() + " Mode";
		buttonPoint = new HashMap<JButton, Location>();
		
		/** Auto-generated by WindowBuilder Pro **/
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		{
			JMenu mnFile = new JMenu("File");
			menuBar.add(mnFile);
			{
				JMenuItem mntmOpen = new JMenuItem("Open");
				mnFile.add(mntmOpen);
			}
			{
				JMenuItem mntmSave = new JMenuItem("Save");
				mnFile.add(mntmSave);
			}
			{
				JMenuItem mntmSaveAndQuit = new JMenuItem("Save and Quit");
				mnFile.add(mntmSaveAndQuit);
			}
			{
				JMenuItem mntmQuit = new JMenuItem("Quit");
				mnFile.add(mntmQuit);
			}
		}
		{
			JMenuItem mntmHelp = new JMenuItem("Help");
			menuBar.add(mntmHelp);
		}
		
		initialize();
		startGame(mode);
	}
	
	/**
	 * Start a new game.
	 */
	private void startGame(Game.Mode mode) {
        game = new Game(mode);
        game.addBoard(new Board(this.dimension));
        Player newPlayer;
        if (mode == Game.Mode.BOT_BATTLE) {
            newPlayer = new Player(PLAYER1_NAME, Player.Type.BOT);
            PLAYER1_ID = newPlayer.getSharedID();
            game.addPlayer(newPlayer);
            
            newPlayer = new Player(PLAYER2_NAME, Player.Type.BOT);
            PLAYER2_ID = newPlayer.getSharedID();
            game.addPlayer(newPlayer);
        }
        if (mode == Game.Mode.ONE_PLAYER) {
            newPlayer = new Player(PLAYER1_NAME, Player.Type.HUMAN);
            PLAYER1_ID = newPlayer.getSharedID();
            game.addPlayer(newPlayer);
            
            newPlayer = new Player(PLAYER2_NAME, Player.Type.BOT);
            PLAYER2_ID = newPlayer.getSharedID();
            game.addPlayer(newPlayer);
        }
        if (mode == Game.Mode.TWO_PLAYER) {
            newPlayer = new Player(PLAYER1_NAME, Player.Type.HUMAN);
            PLAYER1_ID = newPlayer.getSharedID();
            game.addPlayer(newPlayer);
            
            newPlayer = new Player(PLAYER2_NAME, Player.Type.HUMAN);
            PLAYER2_ID = newPlayer.getSharedID();
            game.addPlayer(newPlayer);
        }
        game.begin();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		this.masterPanel = new JPanel(new BorderLayout());
		this.boardPanel  = this.createBoardPanel(); 
		this.infoPanel   = this.createInfoPanel();
		
		//Now add everything to master panel.
		
		this.masterPanel.add(this.boardPanel, BorderLayout.CENTER);
		this.masterPanel.add(this.infoPanel, BorderLayout.NORTH);
		//add masterPanel to your window
		this.getContentPane().add(this.masterPanel);
	    
		
	    //this.frmCpenGame = new JFrame();
		this.setTitle(this.title);
		this.setBounds(100, 100, 1000, 1000);
	    
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.pack();
		this.setLocationByPlatform(true); 
		//this.setLocationRelativeTo(null); // display frame in center of screen
		this.setVisible(true);
	}
	
	private JPanel createBoardPanel(){
			    
		GridLayout boardLayout = new GridLayout(this.rowCount, 
					   							this.columnCount, 
					   							this.BUTTON_HORIZONTAL_GAP, 
					   							this.BUTTON_VERTICAL_GAP);
		JPanel panel = new JPanel();
        panel.setLayout(boardLayout);

		int totalButtonCount = rowCount * columnCount;

	    for (int i = 0; i < totalButtonCount; i++) {
	    	String buttonText = "";
	    	Color buttonColor = null;
	    	if(i == this.columnCount/2) {
	    		buttonText  = this.PLAYER2_MARKER;
	    		buttonColor = this.PLAYER2_COLOR;
	    	} else if (i == (totalButtonCount - this.columnCount/2 - 1)){
	    		buttonText  = this.PLAYER1_MARKER;
	    		buttonColor = this.PLAYER1_COLOR;
	    	}
	    	
	    	JButton button = this.createButton(buttonColor, buttonText);
	    	
	    	buttonPoint.put(button, new Location(
	    	                Math.floorMod(i, this.dimension),
	    	                Math.floorDiv(i, this.dimension),
	    	                this.dimension - 1) );
	    	
	    	/** Associate Click event handlers **/
	    	button.addActionListener(
	    	    new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        buttonClick(e);
                    }
	    	    }
	    	);
	    	
	    	panel.add(button);
	    }
		return panel;
	}
	
	private void buttonClick(ActionEvent e) {
	    JButton button = (JButton) e.getSource();
	    Location buttonLocation = buttonPoint.get(e.getSource());
	    if (game.canMoveTo(buttonLocation)) {
	        button.setBackground(this.PLAYER1_COLOR);
	        game.moveTo(buttonLocation);
	    }
	}
	
	private JButton createButton(Color color, String text) {
		JButton button = new JButton(text);
		button.setPreferredSize(new Dimension(BUTTON_DIMENSION, BUTTON_DIMENSION));
	    button.setBorder( new LineBorder(Color.BLACK) );
	    button.setOpaque(true);
    	button.setBackground(color);
   
    	//button.setForeground(color);
    	return button;
	}
	
	private JPanel createInfoPanel() {
		JPanel infoPanel = new JPanel();
		
		//infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		
		JLabel label = new JLabel("Turn: " + this.PLAYER1_NAME);
		label.setFont((new Font("Serif", Font.BOLD, 14)));
		infoPanel.add(label);

		return infoPanel;
	}
	
	private void setButtonColor(JButton button, Color color){}
	private void setButtonText(JButton button, String text){}
	
	public void setInfoLabelText(String text) {
		JLabel infoLabel = (JLabel) this.infoPanel.getComponent(0);
		infoLabel.setText(text);
	}
}
