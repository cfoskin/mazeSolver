package view;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import model.Maze;

import java.awt.Color;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;

import controller.MazeSolver;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MazeHomeView extends JFrame{

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
	private JTextField enterMazeFile;
	private String currentMazeFileName;
	JTextPane txtpnUnsolved = new JTextPane();
	private JScrollPane scrollPane;
	
	/**
	 * Create the application.
	 * @throws FileNotFoundException 
	 */
	public MazeHomeView() throws FileNotFoundException {
		this.currentMazeFileName = "test50x50.maze";
		this.maze = new Maze(this.currentMazeFileName);
	 	this.mazeSolver =new MazeSolver(this.maze );

			initialize();
	}

	/* Launch the application.
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MazeHomeView window = new MazeHomeView();
					window.getFrmMazeSolverApp().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public String fileChooser()
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
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setFrmMazeSolverApp(new JFrame());
		getFrmMazeSolverApp().setTitle("Maze Solver App Home Page\n");
		getFrmMazeSolverApp().getContentPane().setBackground(Color.WHITE);
		getFrmMazeSolverApp().setResizable(true);
		getFrmMazeSolverApp().setSize(maze.getMazeWidth(), maze.getMazeHeight());
		getFrmMazeSolverApp().setBounds(150, 150, 600, 500);
		getFrmMazeSolverApp().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrmMazeSolverApp().getContentPane().setPreferredSize(new Dimension(maze.getMazeWidth(), maze.getMazeHeight()));//.setLayout(null);
		table = new JTable(maze);
//		table.setCellSelectionEnabled(true);
//		table.setColumnSelectionAllowed(true);
		table.setDefaultRenderer(Color.class, new ColorRenderer());
		getFrmMazeSolverApp().getContentPane().setLayout(null);
		//table.setBounds(25, 24, 494, 306);
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setSize(maze.getMazeWidth(), maze.getMazeHeight());
		scrollPane.setBounds(44, 146, 509, 301);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		getFrmMazeSolverApp().getContentPane().add(scrollPane);
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(225, 35, 298, 64);
		getFrmMazeSolverApp().getContentPane().add(panel);

		this.btnNewButton = new JButton("Select a File");
		btnNewButton.setBackground(Color.WHITE);
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
		panel.add(btnNewButton);

		this.btnNewButton_2 = new JButton("Solve (Queue)");
		btnNewButton_2.setBackground(Color.WHITE);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mazeSolver.colorQueue(table, maze);
				//mazeSolver.breadthFirstSearch(table, maze);
				txtpnUnsolved.setText("Solved using a Queue!");
			}
		});
		panel.add(btnNewButton_2);

		this.btnLoadMaze = new JButton(" Clear Path   ");
		btnLoadMaze.setBackground(Color.WHITE);
		btnLoadMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					maze = new Maze(currentMazeFileName);
					txtpnUnsolved.setBackground(Color.GREEN);
				} catch (FileNotFoundException e1) {
				}
				txtpnUnsolved.setText("Unsolved!");
				table.setModel(maze);
			}
		});
		panel.add(btnLoadMaze);
		this.btnNewButton_3 = new JButton("Solve (Stack)");
		btnNewButton_3.setBackground(Color.WHITE);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mazeSolver.colorStackPath(table, maze);
				//mazeSolver.paintPath(table, maze);
			//	mazeSolver.depthFirstSearchColored(table, maze);
				txtpnUnsolved.setBackground(Color.GREEN);
				txtpnUnsolved.setText("Solved using a Stack!");
			}
		});
		panel.add(btnNewButton_3);
		enterMazeFile = new JTextField();
		enterMazeFile.setToolTipText("Enter Maze File Name Here..");
		enterMazeFile.setBounds(60, 35, 152, 25);
		frmMazeSolverApp.getContentPane().add(enterMazeFile);
		enterMazeFile.setColumns(10);
				this.btnNewButton_1 = new JButton("Load New Maze");
				btnNewButton_1.setBackground(Color.WHITE);
				btnNewButton_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {	
						try {
							currentMazeFileName = enterMazeFile.getText();
							maze = new Maze(currentMazeFileName);
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
	}
}
