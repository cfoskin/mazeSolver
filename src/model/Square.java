package model;

import java.awt.Color;
import java.util.HashMap;

/**
 * @author Colum Foskin version 1.0
 * 26/02/15
 * This Square class is for the square model each object created will represent one square on the maze.
 * Each square will have a character type, ab x and y co-ordinate which will determine its location on the 2d maze array.
 * Each square also has a boolean which determines whether or not the square was visited or not.
 * Each square will also have a color mapped to it when it is created which will depend on the char type it is, eg: wall = black.
 */
public class Square {
	private char squareType;
	private int x;//x co-ordinate 
	private int y;//y co-ordinate
	private boolean visited;
	private Color color;
	private static HashMap<Character, Color> colorsMap = new HashMap<Character, Color>();
	
	static {
		colorsMap.put('o', Color.BLUE);
		colorsMap.put('*', Color.GREEN);
		colorsMap.put('#', Color.BLACK);
		colorsMap.put('.', Color.WHITE);
	   	};
	/**
	 * @param squareType
	 */
	public Square(char squareType, int x, int y) {
		this.squareType = squareType;
		this.visited = false;
		this.x = x;
		this.y = y;
		this.color = Square.colorsMap.get(this.squareType);//mapping the colour to the square depending on the type of char.
	}

	/**
	 * @return returns the colour of the square.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color 
	 * setter for the color attribute
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return squareType 
	 * returns the type of square
	 */
	public char getSquareType() {
		return this.squareType;
	}

	/**
	 * @return
	 * returns the y co-ordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 * setter for the y co-ordinate 
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return
	 * returns the x co-ordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x 
	 * setter for the x co-ordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return
	 * returns the visited attribute.
	 */
	public boolean getVisited() {
		return this.visited;
	}

	/**
	 * @param value
	 * setter for the visited attribute
	 */
	public void setVisited(boolean value) {
		this.visited = value;
	}

	public String toString() {
		return "Square at " + (this.x + 2) + " " + (this.y) + "\n";
	}

}