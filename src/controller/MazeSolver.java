package controller;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Stack;

import javax.swing.JTable;
import javax.swing.text.html.HTMLDocument.Iterator;

import view.MazeApp;
import model.Maze;
import model.Square;
import edu.princeton.cs.introcs.StdOut;
import view.MazeApp;
public class MazeSolver {
	private String pathString;
	public MazeSolver(Maze maze) {
		this.pathString=""; 
	}

	public Stack<Square> depthFirstSearch(JTable table,Maze maze) {
		Stack<Square> stackOfSquares = new Stack<Square>();
		Square startingSquare = maze.getStart();
		stackOfSquares.push(startingSquare);
		startingSquare.setVisited(true);
		while (!stackOfSquares.isEmpty()) {
			Square square = stackOfSquares.peek();
			Square neighbouringSquare = maze.getNeighbours(square);
			if (neighbouringSquare != null) {
				neighbouringSquare.setVisited(true);
				pathString+=neighbouringSquare.getSquareType() + "  x:" + (neighbouringSquare.getX()) + " y:" + (neighbouringSquare.getY() + 1) + "\n";
				stackOfSquares.push(neighbouringSquare);
				if (neighbouringSquare.getSquareType() == '*') {
					break;
				}
			} else {
				stackOfSquares.pop();
			}
		}
		startingSquare.setColor(Color.BLUE);
		return stackOfSquares;
	}

	public String getPathString() {
		return pathString;
	}

	public Stack<Square> reversePath(JTable table,Maze maze)
	{ 
		Stack<Square> path = depthFirstSearch(table, maze);
		Collections.reverse(path);
		return path;
	}

	public Stack<Square> depthFirstSearchColored(JTable table,Maze maze) {
		Stack<Square> stackOfSquares = new Stack<Square>();
		Square startingSquare = maze.getStart();
		stackOfSquares.push(startingSquare);
		startingSquare.setVisited(true);
		while (!stackOfSquares.isEmpty()) {
			Square square = stackOfSquares.peek();
			square.setColor(Color.red);
			Square neighbouringSquare = maze.getNeighbours(square);
			if (neighbouringSquare != null) {
				neighbouringSquare.setVisited(true);
				stackOfSquares.push(neighbouringSquare);
				if (neighbouringSquare.getSquareType() == '*') {
					break;
				}
			} else {
				stackOfSquares.pop();
			}
		}
		startingSquare.setColor(Color.BLUE);
		table.repaint();
		return stackOfSquares;
	}


	public void colorStackPathComplete(JTable table, Maze maze)
	{
		Stack<Square> path = depthFirstSearchColored(table, maze);
		while (!path.isEmpty()) {
			Square square = path.peek();
			if(square.getColor().equals(Color.GREEN) ||square.getColor().equals(Color.BLUE))
			{
				path.pop();
			}
			else
			{
				square.setColor(Color.GRAY);
				path.pop();
			}
		}
		table.repaint();
	}

	public void breadthFirstSearch(JTable table, Maze maze)
	{
		Queue<Square> queue = new LinkedList<Square>();
		Square start = maze.getStart();
		queue.add(start);
		start.setVisited(true);
		while(!queue.isEmpty()) {
			Square square = queue.remove();
			Square child=null;
			while((child = maze.getNeighbours(square))!=null) {
				child.setVisited(true);
				child.setColor(Color.GRAY);
				queue.add(child);
				StdOut.print(child.getSquareType() + "  x:" + (child.getX() + 2) + " y:" + (child.getY() + 1) + "\n");
				if (child.getSquareType() == '*') {
					StdOut.print("easy maze" + "\n");
					StdOut.print(child);
					break;
				}
			}
		}
		table.repaint();
	}
	public Queue<Square> breadthFirstSearch(Maze maze)
	{
		// BFS uses Queue data structure
		Queue<Square> queue = new LinkedList<Square>();
		Square start = maze.getStart();
		queue.add(start);
		start.setVisited(true);
		while(!queue.isEmpty()) {
			Square square = queue.peek();
			Square child=null;
			while((child = maze.getNeighbours(square))!=null) {
				child.setVisited(true);
				queue.add(child);
				//StdOut.print(child.getSquareType() + "  x:" + (child.getX() + 2) + " y:" + (child.getY() + 1) + "\n");
				if (child.getSquareType() == '*') {
//					StdOut.print("easy maze" + "\n");
//					StdOut.print(child);
					break;
				}
			}
		}
		return queue;
	}
	

}
