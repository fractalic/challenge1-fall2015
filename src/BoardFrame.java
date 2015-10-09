import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.GridLayout;

public class BoardFrame {

	private final String TITLE           = "CPEN221 Game";
	private final int BUTTON_DIMENSION   = 40;
	private final String PLAYER1_MARKER  = "P1";
	private final String PLAYER2_MARKER  = "P2";
	
	private final Color PLAYER1_COLOR  = Color.RED;
	private final Color PLAYER2_COLOR  = Color.LIGHT_GRAY;
	
	private JFrame frmCpenGame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BoardFrame window = new BoardFrame();
					window.frmCpenGame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BoardFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		int rows = 16;
	    int cols = 16;
	    int totalButtonCount = rows * cols;
	    int verticalGap = 0;
	    int horizontalGap = 0;
		
	    frmCpenGame = new JFrame();
		frmCpenGame.setTitle(TITLE);
		frmCpenGame.setBounds(100, 100, 1000, 1000);
		
		Container pane = frmCpenGame.getContentPane();
	    pane.setLayout(new GridLayout(rows, cols, horizontalGap, verticalGap));
	    
	    for (int i = 0; i < totalButtonCount; i++) {
	      //JButton button = new JButton();
	    	String buttonText = "";
	    	Color buttonColor = null;
	    	if(i == cols/2) {
	    		buttonText = PLAYER2_MARKER;
	    		buttonColor = PLAYER2_COLOR;
	    	} else if (i == (totalButtonCount - cols/2 - 1)){
	    		buttonText = PLAYER1_MARKER;
	    		buttonColor = PLAYER1_COLOR;
	    	}
	    	
	    	JButton button = this.createButton(buttonColor, buttonText);
	    	
	    	/** Associate Click event handlers **/
	    	
	    	pane.add(button);
	    }
		
		frmCpenGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frmCpenGame.pack();
		frmCpenGame.setLocationRelativeTo(null); // display frame in center of screen
		frmCpenGame.setVisible(true);
	  
	    
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
	
	private void setButtonColor(JButton button, Color color){}
	private void setButtonText(JButton button, String text){}
}
