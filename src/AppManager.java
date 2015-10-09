import java.awt.EventQueue;

import javax.swing.JOptionPane;


public class AppManager {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Object[] possibilities = Game.Mode.values();
					Game.Mode mode = (Game.Mode)JOptionPane.showInputDialog(
					                    null,
					                    "Select Play Mode",
					                    "Mode Selection",
					                    JOptionPane.PLAIN_MESSAGE,
					                    null,
					                    possibilities,
					                    possibilities[0]);

					
					if (mode == null) { return; }
					
					//Game.Mode mode = Game.Mode.valueOf(selectionText); 
					
					BoardFrame window = new BoardFrame(16, 16, mode);
					
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
