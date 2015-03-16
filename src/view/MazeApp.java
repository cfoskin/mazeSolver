package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import model.Maze;
import model.Square;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;

import controller.MazeSolver;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.princeton.cs.introcs.StdOut;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public class MazeApp extends JFrame{

	private JFrame frmMazeSolverApp;
	private JTable table;
	private JPanel panel;
	private JButton btnNewButton_1;
	private JButton btnNewButton;
	private JButton btnNewButton_2;
	private JButton btnLoadMaze;
	private JButton btnNewButton_3;
	private Maze maze;
	private MazeSolver mazeSolver;
	private Stack<Square> path; 
	private JTextField enterMazeFile;
	private String currentMazeFileName;
	JTextPane txtpnUnsolved = new JTextPane();
	private JScrollPane scrollPane;
	private JButton btnStepStack;
	private JTextArea txtrNoPath;
	private JTextField txtThePath;
	/**
	 * Create the application.
	 * @throws FileNotFoundException 
	 */
	public MazeApp() throws FileNotFoundException {
		this.currentMazeFileName = "largeMaze.txt";
		maze = new Maze(this.currentMazeFileName);
		this.mazeSolver =new MazeSolver(this.maze );
		this.path = new Stack<Square>();
		initialize();
	}

	/* Launch the application.
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

	private void setUpJFrame()
	{ 
		setFrmMazeSolverApp(new JFrame());
		getFrmMazeSolverApp().setTitle("Maze Solver App Home Page\n");
		getFrmMazeSolverApp().getContentPane().setBackground(new Color(255, 255, 255));
		getFrmMazeSolverApp().setResizable(true);
		getFrmMazeSolverApp().setSize(700, 476);
		getFrmMazeSolverApp().setBounds(200, 200, 700, 450);
		getFrmMazeSolverApp().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrmMazeSolverApp().getContentPane().setPreferredSize(new Dimension(maze.getMazeWidth(), maze.getMazeHeight()));
		getFrmMazeSolverApp().getContentPane().setLayout(null);
	}

	private void setUpTable()
	{
		table = new JTable(maze);
		table.setDefaultRenderer(Color.class, new ColorRenderer());
		getFrmMazeSolverApp().getContentPane().setLayout(null);
		table.setBounds(25, 24, 494, 306);
		//	table.setPreferredScrollableViewportSize(table.getPreferredSize());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}

	private void setUpScrollpane()
	{		
		scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBackground(new Color(255, 255, 255));
		scrollPane.setSize(maze.getMazeWidth(), maze.getMazeHeight());
		scrollPane.setBounds(44, 146, 509, 301);
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setUpJFrame();
		setUpTable();
		setUpScrollpane();
		path = mazeSolver.reversePath(table, maze);
		getFrmMazeSolverApp().getContentPane().add(scrollPane, BorderLayout.CENTER);
		panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(239, 35, 298, 115);
		getFrmMazeSolverApp().getContentPane().add(panel);
		this.btnNewButton = new JButton("Select a File");
		btnNewButton.setBounds(20, 5, 120, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentMazeFileName = fileChooser();
				try {
					maze = new Maze(currentMazeFileName);
					enterMazeFile.setText(currentMazeFileName);
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null,"Sorry, Incorrect Maze File Name!" + "\n" + "Please try another File name", null, 0);				
				}
				table.setModel(maze);
				table.repaint();
				txtpnUnsolved.setBackground(Color.WHITE);
			}
		});
		panel.setLayout(null);
		panel.add(btnNewButton);

		this.btnNewButton_2 = new JButton("Solve (Queue)");
		btnNewButton_2.setBounds(145, 5, 141, 25);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//mazeSolver.breadthFirstSearch(table, maze);
				txtpnUnsolved.setText("Solved using a Queue!");
			}
		});
		panel.add(btnNewButton_2);

		this.btnLoadMaze = new JButton(" Clear Path   ");
		btnLoadMaze.setBounds(22, 35, 124, 25);
		btnLoadMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					maze = new Maze(currentMazeFileName);
					txtpnUnsolved.setBackground(Color.WHITE);
				} catch (FileNotFoundException e1) {
				}
				txtpnUnsolved.setText("Unsolved!");
				table.setModel(maze);
				path = null;
			}
		});
		panel.add(btnLoadMaze);
		this.btnNewButton_3 = new JButton("Solve (Stack)");
		btnNewButton_3.setBounds(151, 35, 125, 25);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					maze = new Maze(currentMazeFileName);
					mazeSolver.colorStackPathComplete(table, maze);
				} catch (FileNotFoundException e1) {
					StdOut.print("incorrect file");
					}
				txtpnUnsolved.setText("Solved!");
				txtrNoPath.setText(mazeSolver.getPathString());
				txtThePath.setVisible(true);
				table.setModel(maze);

			}
		});
		panel.add(btnNewButton_3);

		btnStepStack = new JButton("step stack");
		btnStepStack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (path == null)
					path = mazeSolver.reversePath(table, maze);
				if(!path.isEmpty())
				{
					Square square = path.pop();
					if(square.getColor().equals(Color.GREEN) ||square.getColor().equals(Color.BLUE))
					{
					}
					else
					{
						square.setColor(Color.GRAY);
					}
				}
				else
				{
					txtpnUnsolved.setText("Solved!");
				}
				table.repaint();
			}
		});
		btnStepStack.setBounds(20, 78, 117, 25);
		panel.add(btnStepStack);
		enterMazeFile = new JTextField();
		enterMazeFile.setToolTipText("Enter Maze File Name Here..");
		enterMazeFile.setBounds(60, 35, 152, 25);
		frmMazeSolverApp.getContentPane().add(enterMazeFile);
		enterMazeFile.setColumns(10);
		this.btnNewButton_1 = new JButton("Load New Maze");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				try {
					currentMazeFileName = enterMazeFile.getText();
					maze = new Maze(currentMazeFileName);
					mazeSolver = new MazeSolver(maze);
					path  = mazeSolver.reversePath(table, maze);
					txtpnUnsolved.setBackground(Color.WHITE);
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null,"Sorry, Incorrect Maze File Name!" + "\n" + "Please try another File name", null, 0);				
				}
				table.setModel(maze);
				table.repaint();
			}
		});
		btnNewButton_1.setBounds(60, 72, 157, 25);
		frmMazeSolverApp.getContentPane().add(btnNewButton_1);


		txtpnUnsolved.setText("        UNSOLVED!!\n");
		txtpnUnsolved.setBounds(70, 113, 147, 21);
		frmMazeSolverApp.getContentPane().add(txtpnUnsolved);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(570, 146, 85, 301);
		frmMazeSolverApp.getContentPane().add(scrollPane_1);
		
		txtrNoPath = new JTextArea();
		scrollPane_1.setViewportView(txtrNoPath);
		
		txtThePath = new JTextField();
		txtThePath.setText("The Path Taken");
		txtThePath.setBounds(549, 99, 114, 19);
		frmMazeSolverApp.getContentPane().add(txtThePath);
		txtThePath.setColumns(10);
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
