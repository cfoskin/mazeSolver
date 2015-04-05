package app;
import java.awt.EventQueue;
import java.io.FileNotFoundException;
import view.MazeApp;

/**
 * @author Colum Foskin version 1.0
 * 26/02/15 
 * main class which runs the application
 */
public class Main {

	/**
	 * Launch the application.
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MazeApp window = new MazeApp();
					window.getFrmMazeSolverApp().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
