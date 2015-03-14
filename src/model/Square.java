package model;

import java.awt.Color;
import java.util.HashMap;

/**
 * @author Colum Foskin version 1.0
 * 26/02/15
 */
public class Square {
	private char squareType;
	private int x;
	private int y;
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
		this.color = Square.colorsMap.get(this.squareType);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return squareType
	 */
	public char getSquareType() {
		return this.squareType;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public boolean isVisited() {
		return this.visited;
	}

	public void setVisited(boolean value) {
		this.visited = value;
	}

	public String toString() {
		return "Square at " + (this.x + 2) + " " + (this.y) + "\n";
	}

}