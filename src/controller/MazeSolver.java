package controller;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Stack;

import javax.swing.JTable;
import javax.swing.text.html.HTMLDocument.Iterator;

import view.MazeHomeView;
import model.Maze;
import model.Square;
import edu.princeton.cs.introcs.StdOut;
import view.MazeHomeView;
public class MazeSolver implements Runnable {
	public MazeSolver(Maze maze) {

	}

	public Stack<Square> depthFirstSearch(JTable table,Maze maze) {
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

	public Stack<Square> reversePath(JTable table,Maze maze)
	{ 
		Stack<Square> path = depthFirstSearch(table, maze);
		Stack<Square> reversePath = new Stack<Square>();
		while (!path.isEmpty())
		{
	         Square sq = path.pop();
             reversePath.push(sq);
		}
		return reversePath;
	}
//
//	public void colorStackPath(JTable table, Maze maze)
//	{
//		Stack<Square> path = depthFirstSearch(table, maze);
//		while (!path.isEmpty()) {
//			Square square = path.peek();
//			if(square.getColor().equals(Color.GREEN) ||square.getColor().equals(Color.BLUE))
//			{
//				path.pop();
//			}
//			else
//			{
//				square.setColor(Color.GRAY);
//				path.pop();
//			}
//		}
//		table.repaint();
//	}


	public void colorStackPath(JTable table, Maze maze)
	{
		Stack<Square> path = depthFirstSearch(table, maze);
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

	public Queue<Square> breadthFirstSearch(JTable table, Maze maze)
	{
		// BFS uses Queue data structure
		Queue<Square> queue = new LinkedList<Square>();
		Square start = maze.getStart();
		queue.add(start);
		start.setVisited(true);
		while(!queue.isEmpty()) {
			Square square = queue.remove();
			Square child=null;
			while((child = maze.getNeighbours(square))!=null) {
				child.setVisited(true);
				//child.setColor(Color.RED);
				queue.add(child);
				StdOut.print(child.getSquareType() + "  x:" + (child.getX() + 2) + " y:" + (child.getY() + 1) + "\n");
				if (child.getSquareType() == '*') {
					StdOut.print("easy maze" + "\n");
					return queue;
				}
			}
		}
		table.repaint();
		return queue;
	}

	public void colorQueue(JTable table, Maze maze)
	{
		Queue<Square> path = breadthFirstSearch(table, maze);
		while (!path.isEmpty()) {
			Square square = path.peek();
			if(square.getColor().equals(Color.GREEN))
			{
				path.poll();
			}
			else
			{
				square.setColor(Color.GRAY);
				path.poll();
			}
		}
		table.repaint();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
