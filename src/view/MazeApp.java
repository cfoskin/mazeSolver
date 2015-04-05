package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Queue;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.Maze;
import model.Square;
import controller.MazeSolver;
import edu.princeton.cs.introcs.StdOut;

/**
* @author Colum Foskin version 1.0
 * 26/02/15
 * This is the GUI class which represents the Maze as a table.
 * The GUI allows the user to interact with the application.
 */
public class MazeApp extends JFrame{

	private JFrame frmMazeSolverApp;
	private JTable table;
	private JPanel panel;
	private JButton btnNewButton_1;
	private JButton btnNewButton;
	private JButton btnLoadMaze;
	private JButton btnNewButton_3;
	private Maze maze;
	private MazeSolver mazeSolver;
	private Stack<Square> path; //this path will change depending on the maze loaded
	private JTextField enterMazeFile;
	private String currentMazeFileName;
	private JTextPane txtpnUnsolved = new JTextPane();
	private JScrollPane scrollPane;
	private JButton btnStepStack;
	private JTextArea txtrNoPath;
	private JTextField txtThePath;
	private JScrollPane scrollPane_1;
	private Queue<Square> quePath;//this path will change depending on the maze loaded
	
	/**
	 * Create the application.
	 * @throws FileNotFoundException 
	 */
	public MazeApp() throws FileNotFoundException {
		this.currentMazeFileName = "largeMaze.txt";//loading up a large maze on start up.
		maze = new Maze(this.currentMazeFileName);
		this.mazeSolver =new MazeSolver(this.maze );
		initialize();
	}

	/**
	 * @return
	 * This is a simple file chooser which allows the user to select a maze file this way rather than entering the file
	 * path - which is still an option also.
	 */
	private String fileChooser()
	{
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"TXT FILES", "txt", "maze");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(frmMazeSolverApp);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			chooser.getSelectedFile().getAbsolutePath();
		}
		String choice = chooser.getSelectedFile().getAbsolutePath();
		return choice;
	}

	/**
	 * setting up the Jframe
	 */
	private void setUpJFrame()
	{ 
		setFrmMazeSolverApp(new JFrame());
		getFrmMazeSolverApp().setTitle("Maze Solver App Home Page\n");
		getFrmMazeSolverApp().getContentPane().setBackground(new Color(255, 255, 255));
		getFrmMazeSolverApp().setResizable(true);
		getFrmMazeSolverApp().setSize(700, 486);
		getFrmMazeSolverApp().setBounds(200, 200, 700, 500);
		getFrmMazeSolverApp().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrmMazeSolverApp().getContentPane().setPreferredSize(new Dimension(maze.getMazeWidth(), maze.getMazeHeight()));
		getFrmMazeSolverApp().getContentPane().setLayout(null);
	}

	/**
	 * setting up the Jtable
	 */
	private void setUpTable()
	{
		table = new JTable(maze);
		table.setDefaultRenderer(Color.class, new ColorRenderer());//setting the default renderer to a colour renderer
		getFrmMazeSolverApp().getContentPane().setLayout(null);
		table.setBounds(44, 146, 509, 301);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		adjustTable(table);
	}

	/**
	 * @param table
	 * this method is just to ensure that every cell in the table is the same size regardles of the table size.
	 * I am just looping through each cell and setting the specified width
	 */
	private void adjustTable(JTable table)
	{
		table.setRowHeight(20);//no need to loop for the height as table has a set row height method.
		for(int i=0; i< maze.getMazeWidth();i++)
		{
			table.getColumnModel().getColumn(i).setPreferredWidth(20);
		}
	}

	/**
	 * setting up the Jscroll pane to allow the applictaion window to display a maze of any size.
	 */
	private void setUpScrollpane()
	{		
		scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBackground(new Color(255, 255, 255));
		scrollPane.setSize(new Dimension(maze.getMazeWidth(), maze.getMazeHeight()));
		scrollPane.setBounds(43, 134, 612, 350);	
	}

	/**
	 * method which allows the user to step through the Stack solution by popping of one square at a time
	 */
	private void stepStack()
	{
		if (path == null)//check if null then set the path to the one derived from the dfs in the mazeSolver class
		{
			path = mazeSolver.reversePath(table, maze);
		}
		if(!path.isEmpty())
		{
			Square square = path.pop();
			if(!square.equals(maze.getStart()) || !square.getColor().equals(Color.BLUE))//start or finish
			{
				square.setColor(Color.GRAY);
				txtpnUnsolved.setText("Solving using a Stack......");
			}
		}
		else
		{
			txtpnUnsolved.setText("Solved!");
		}
		table.repaint();
	}

	/**
	 * this method allows the user to see the full Stack solution without having to step through it.
	 */
	private void solveStack()
	{
		try {
			maze = new Maze(currentMazeFileName);
			mazeSolver.colorPath(table, maze);
		} catch (FileNotFoundException e1) {
			StdOut.print("incorrect file");
		}
		txtpnUnsolved.setText("Solved!");
		txtrNoPath.setText(mazeSolver.getPathString());
		scrollPane_1.setVisible(true);
		txtThePath.setVisible(true);
		table.setModel(maze);
		adjustTable(table);
	}

	/**
	 * creates a new maze from the text entered in the textfield
	 */
	private void loadMaze()
	{
		try {
			currentMazeFileName = enterMazeFile.getText();
			maze = new Maze(currentMazeFileName);
			mazeSolver = new MazeSolver(maze);
			path  = mazeSolver.reversePath(table, maze);
			txtpnUnsolved.setBackground(Color.WHITE);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null,"Sorry, Incorrect Maze File Name!" + "\n" + "Please try another File name", null, 0);				
		}
		table.setModel(maze);
		adjustTable(table);
		table.repaint();
	}

	/**
	 * this method creates a maze from the file selected using the file chooser above.
	 */
	private void useFileChooser()
	{
		currentMazeFileName = fileChooser();
		try {
			maze = new Maze(currentMazeFileName);
			enterMazeFile.setText(currentMazeFileName);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null,"Sorry, Incorrect Maze File Name!" + "\n" + "Please try another File name", null, 0);				
		}
		table.setModel(maze);
		adjustTable(table);
		table.repaint();
		txtpnUnsolved.setBackground(Color.WHITE);
	}

	/**
	 * clears the solution at any stage.
	 */
	private void clearSolution()
	{
		try {
			maze = new Maze(currentMazeFileName);
			txtpnUnsolved.setBackground(Color.WHITE);
		} catch (FileNotFoundException e1) {
		}
		txtpnUnsolved.setText("Unsolved!");
		table.setModel(maze);
		adjustTable(table);
		scrollPane_1.setVisible(false);
		txtThePath.setVisible(false);
		path = null;
	}

	/**
	 * this method allows the user to step through the queue based solution
	 */
	private void stepQueue()
	{		
		try {
			maze = new Maze(currentMazeFileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (quePath == null)
		{
			table.setModel(maze);
			adjustTable(table);
			quePath = mazeSolver.breadthFirstSearch(table, maze);
		}
		if(!quePath.isEmpty())
		{
			Square square = quePath.remove();
			if(!square.equals(maze.getStart()) || !square.getColor().equals(Color.BLUE))//start or finish
			{
				square.setColor(Color.GRAY);
				txtpnUnsolved.setText("Solving using a queue......");
			}
		}
		else
		{
			txtpnUnsolved.setText("Solved!");
		}
		table.repaint();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setUpJFrame();
		setUpTable();
		setUpScrollpane();
		getFrmMazeSolverApp().getContentPane().add(scrollPane, BorderLayout.CENTER);
		panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(239, 35, 298, 115);
		getFrmMazeSolverApp().getContentPane().add(panel);
		this.btnNewButton = new JButton("Select a new maze file");
		btnNewButton.setBounds(47, 0, 209, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				useFileChooser();
			}
		});
		panel.setLayout(null);
		panel.add(btnNewButton);

		this.btnLoadMaze = new JButton(" Clear Path   ");
		btnLoadMaze.setBounds(12, 37, 124, 25);
		btnLoadMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearSolution();
			}
		});
		panel.add(btnLoadMaze);
		this.btnNewButton_3 = new JButton("Solution (Stack)");
		btnNewButton_3.setBounds(151, 37, 147, 25);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				solveStack();
			}
		});
		panel.add(btnNewButton_3);

		btnStepStack = new JButton("Step Stack");
		btnStepStack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stepStack();
			}
		});
		btnStepStack.setBounds(12, 65, 117, 25);
		panel.add(btnStepStack);

		JButton btnStepQueue = new JButton("Step Queue");
		btnStepQueue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stepQueue();
			}
		});
		btnStepQueue.setBounds(152, 65, 134, 25);
		panel.add(btnStepQueue);
		enterMazeFile = new JTextField();
		enterMazeFile.setToolTipText("Enter Maze File Name Here..");
		enterMazeFile.setBounds(60, 35, 152, 25);
		frmMazeSolverApp.getContentPane().add(enterMazeFile);
		enterMazeFile.setColumns(10);
		this.btnNewButton_1 = new JButton("Load maze file");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				try {
					loadMaze();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null,"Sorry, Incorrect Maze File Name!" + "\n" + "Please try another File name", null, 0);				
				}
			}
		});
		btnNewButton_1.setBounds(60, 72, 157, 25);
		frmMazeSolverApp.getContentPane().add(btnNewButton_1);


		txtpnUnsolved.setText("        UNSOLVED!!\n");
		txtpnUnsolved.setBounds(70, 113, 180, 21);
		frmMazeSolverApp.getContentPane().add(txtpnUnsolved);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(570, 35, 85, 87);
		frmMazeSolverApp.getContentPane().add(scrollPane_1);

		txtrNoPath = new JTextArea();
		scrollPane_1.setViewportView(txtrNoPath);
		scrollPane_1.setVisible(false);

		txtThePath = new JTextField();
		txtThePath.setText("The Path Taken");
		txtThePath.setBounds(552, 12, 114, 19);
		frmMazeSolverApp.getContentPane().add(txtThePath);
		txtThePath.setColumns(10);

		JLabel lblEnterMazeFile = new JLabel("Enter maze file path");
		lblEnterMazeFile.setBounds(52, 11, 181, 15);
		frmMazeSolverApp.getContentPane().add(lblEnterMazeFile);
		txtThePath.setVisible(false);
	}

	public JTextField getEnterMazeFile() {
		return enterMazeFile;
	}

	public void setEnterMazeFile(JTextField enterMazeFile) {
		this.enterMazeFile = enterMazeFile;
	}


	public JFrame getFrmMazeSolverApp() {
		return frmMazeSolverApp;
	}

	public void setFrmMazeSolverApp(JFrame frmMazeSolverApp) {
		this.frmMazeSolverApp = frmMazeSolverApp;
		frmMazeSolverApp.setBackground(Color.LIGHT_GRAY);
	}
}
