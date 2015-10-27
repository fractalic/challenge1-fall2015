import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

//import com.sun.java.util.jar.pack.Package.File;


public class AppManager {

    private static boolean loadFile = false;
    private static File file;
    private static int dimension = 1;
    private static Game.Mode mode = Game.Mode.TWO_PLAYER;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Object[] options = {"Resume a Saved Game", "New Game"};
					
					JFrame dilogParentFrame = new JFrame();
					int optionNum = JOptionPane.showOptionDialog(dilogParentFrame,
							null,
							"Save/Load Game",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.PLAIN_MESSAGE,
							null,
							options,
							options[1]);
		
					if(optionNum == JOptionPane.CLOSED_OPTION) {
						dilogParentFrame.dispose();
						return; 
					}
		
					boolean isNewGame = (optionNum == 1);
					
					int modeID = 0;
					
					Object[] arrModes = Game.Mode.values();
					
					if(isNewGame) {
						
						modeID = JOptionPane.showOptionDialog(
											dilogParentFrame,
											null,
						                    "Select Play Mode",
						                    JOptionPane.YES_NO_CANCEL_OPTION,
											JOptionPane.PLAIN_MESSAGE,
											null,
						                    arrModes,
						                    arrModes[0]);
						
						
						loadFile = false;
					} else {
						
						/** Prompt to open game file **/
						final JFileChooser fc = new JFileChooser();
						int returnVal = fc.showOpenDialog(dilogParentFrame);

				        if (returnVal == JFileChooser.APPROVE_OPTION) {
				            file = fc.getSelectedFile();
				            //This is where a real application would open the file.
				        }
				        
				        loadFile = true;
						
					}
					
					
//					Object[] possibilities = Game.Mode.values();
//					Game.Mode mode = (Game.Mode)JOptionPane.showInputDialog(
//										dilogParentFrame,
//					                    "Select Play Mode",
//					                    "Mode Selection",
//					                    JOptionPane.PLAIN_MESSAGE,
//					                    null,
//					                    possibilities,
//					                    possibilities[0]);

					dilogParentFrame.dispose();
					
					if (modeID == JOptionPane.CLOSED_OPTION) { 
						return; 
					}

                    BoardFrame window;
                    
					if (loadFile == true) {
					    dimension = Game.getDimensionFromFile(file.getAbsolutePath());
					    mode = Game.getModeFromFile(file.getAbsolutePath());
					    window = new BoardFrame(dimension, mode, file.getAbsolutePath());
					} else {
					    dimension = 7;
					    mode = (Game.Mode) arrModes[modeID];
					    window = new BoardFrame(dimension, mode, null);
					}
					
					window.setVisible(true);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
