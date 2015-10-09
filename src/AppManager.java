import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

//import com.sun.java.util.jar.pack.Package.File;


public class AppManager {
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
						
						
						
					} else {
						
						/** Prompt to open game file **/
						final JFileChooser fc = new JFileChooser();
						int returnVal = fc.showOpenDialog(dilogParentFrame);

					        if (returnVal == JFileChooser.APPROVE_OPTION) {
					            File file = fc.getSelectedFile();
					            //This is where a real application would open the file.
					        }
						
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
					
					Game.Mode mode = (Game.Mode) arrModes[modeID];

					
					//Game.Mode mode = Game.Mode.valueOf(selectionText); 
					
					BoardFrame window = new BoardFrame(7, 7, mode);
					
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
