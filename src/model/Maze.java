package model;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
/**
 * @author Colum Foskin version 1.0
 * 26/02/15
 * This is the Maze model class. This is where the maze txt file is read in which can then be used
 * to create a 2d array which will represent the maze object. This class also implements TableModel which i had
 * to do in order to represent the maze on a GUI.The maze was represented as a table and by implementing TableModel it allowed me 
 * to be able to set the colour of each cell in the table which will represent a square. It also allowed me to easily change the maze
 * which was displayed on the GUI when a new maze is loaded. This class also does the work to check if a square has a neighbour or not.
 * There is a few methods which are not being used at any stage in the application but i had to implement them anyway as is necessary 
 * when implementing an interface.
 * */
public class Maze implements TableModel{
	private Square[][] mazeArray;
	private Square startingSquare;
	private int mazeWidth;
	private int mazeHeight;

	/**
	 * @param filename
	 *            maze constructor
	 * @throws FileNotFoundException
	 */
	public Maze(String filename) throws FileNotFoundException {
		this.mazeArray = createMaze(filename);
	}

	/**
	 * @param fileName
	 * @return takes in a maze.txt file using a scanner and returns the scanner
	 *         object for use in the create maze method
	 * 
	 */
	private Scanner readMazeFile(String fileName) {
		File mazeFile = new File(fileName);
		Scanner in = null;
		try {
			in = new Scanner(mazeFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return in;
	}

	/**
	 * @param filename
	 * @return
	 * @throws FileNotFoundException
	 *             creates a 2d array from the scanner object returned in the
	 *             readMazeFile method.
	 */
	public Square[][] createMaze(String filename) throws FileNotFoundException {
		Scanner in = readMazeFile(filename);
		int numColumns = in.nextInt();
		this.mazeWidth = numColumns;
		int numRows = in.nextInt();
		this.mazeHeight = numRows;
		Square[][] result = new Square[numRows][numColumns];
		for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
			String line = in.next();
			for (int columnIndex = 0; columnIndex < numColumns; columnIndex++) {
				Square s = new Square(line.charAt(columnIndex), rowIndex,
						columnIndex);
				result[rowIndex][columnIndex] = s;
				if (s.getSquareType() == 'o') {//setting the starting square at the time of creating the maze
					this.startingSquare = s;
				}
			}
		}
		in.close();
		return result;
	}

	/**
	 * @return returns the starting square which is defined as the char 'o' in
	 *         the create maze method above.
	 */
	public Square getStart() {
		return this.startingSquare;
	}

	/**
	 * @param x
	 * @param y
	 * @return takes in two cordinates and checks if the square at those
	 *         coordinates are in fact a valid path to take and returns true if
	 *         so.
	 */
	private boolean isValidPath(int x, int y) {
		Square sq = this.mazeArray[x][y];
		return sq.getSquareType() == '.' || sq.getSquareType() == '*';
	}

	/**
	 * @param sq
	 * @return takes in a Square object and then checks all possible
	 *         neighbouring squares to see if they are a valid path and returns
	 *         it if so.
	 */
	public Square getNeighbours(Square sq) {
		Square neighbor = null;
		int x = sq.getX();
		int y = sq.getY();
		if ((x < this.mazeArray.length) && (y + 1 < this.mazeArray[x].length)
				&& isValidPath(x, y + 1)) {

			if (!this.mazeArray[x][y + 1].getVisited()) {// checking the square
														// above
				neighbor = this.mazeArray[x][y + 1];
				return neighbor;
			}
		}
		if ((x + 1 < this.mazeArray.length) && isValidPath(x + 1, y)) {
			if (!this.mazeArray[x + 1][y].getVisited()) {// checking the square
														// to right
				neighbor = this.mazeArray[x + 1][y];
				return neighbor;
			}
		}
		if ((x - 1 >= 0) && isValidPath(x - 1, y)) {
			if (!this.mazeArray[x - 1][y].getVisited()) {// checking the square
														// to the left
				neighbor = this.mazeArray[x - 1][y];
				return neighbor;
			}
		}
		if ((x - 1 >= 0) && (y - 1 >= 0) && isValidPath(x, y - 1)) {
			if (!this.mazeArray[x][y - 1].getVisited()) {// checking the square
														// below
				neighbor = this.mazeArray[x][y - 1];
				return neighbor;
			}
		}
		return neighbor;
	}

	/**
	 * @return
	 * returns the maxe array.
	 */
	public Square[][] getMazeArray() {
		return mazeArray;
	}

	/**
	 * @return
	 * returns the maze width.
	 */
	public int getMazeWidth() {
		return mazeWidth;
	}

	/**
	 * @return
	 * returns the maze height.
	 */
	public int getMazeHeight() {
		return mazeHeight;
	}

	public String toString() {
		String mazeToString = "";
		int numRows = this.mazeArray.length;
		for (int i = 0; i < numRows; i++) {
			Square[] row = this.mazeArray[i];
			int numColumns = row.length;
			for (int j = 0; j < numColumns; j++) {
				mazeToString += row[j].getSquareType();
			}
			mazeToString += "\n";
		}
		return mazeToString;
	}

	//below are the implemented methods from tablemodel.
	@Override
	public int getRowCount() {
		return this.mazeHeight;
	}

	@Override
	public int getColumnCount() {
		return this.mazeWidth;
	}

	@Override
	public String getColumnName(int columnIndex) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		return Color.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return this.mazeArray[rowIndex][columnIndex].getColor();//returns the value (colour) for the cell at the specified co-ordinate.
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		this.mazeArray[rowIndex][columnIndex] = (Square) aValue;
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}
}
