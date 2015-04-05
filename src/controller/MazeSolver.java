package controller;
import java.awt.Color;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import javax.swing.JTable;
import model.Maze;
import model.Square;

/**
* @author Colum Foskin version 1.0
 * 26/02/15 
 * This class is the controller class which performs the different searchs which were required 
 * using both stacks and queues.
 */
public class MazeSolver {
	
	private String pathString;//this just stores the path taken in a string which i use to display in the GUI when solved

	public MazeSolver(Maze maze) {
		this.pathString=""; 
	}

	/**
	 * @param table
	 * @param maze
	 * @return 
	 * this method takes in the Jtable and maze objects and the performs the depth first search on the maze
	 * to solve it using a stack data structure. this method using one stack for the dfs and one to store every square visited 
	 * for the step through function. it will return the entire path stack for use in the step function as mentioned.
	 */
	public Stack<Square> depthFirstSearch(JTable table,Maze maze) {
		Stack<Square> entirePath = new Stack<Square>();//this path is all the squares visited including the ones which are not 
		//included in the solution. This is needed for the step through function of the application.
		Stack<Square> stackOfSquares = new Stack<Square>();
		Square startingSquare = maze.getStart();
		stackOfSquares.push(startingSquare);
		startingSquare.setVisited(true);
		while (!stackOfSquares.isEmpty()) {
			Square square = stackOfSquares.peek();
			entirePath.push(square);//include in the entire path whether it has a neighbour or not.
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
		return entirePath;
	}


	/**
	 * @param table
	 * @param maze
	 * @return this method reverses the path which is returned from the above dfs method so the step function begins at the 
	 * starting square which is on the bottom of the stack as a result of the dfs method.
	 */
	public Stack<Square> reversePath(JTable table,Maze maze)
	{ 
		Stack<Square> path = depthFirstSearch(table, maze);
		Collections.reverse(path);
		return path;
	}

	/**
	 * @param table
	 * @param maze
	 * @return this method performs the dfs as using a stacka as does the first dfs method above. the raeson this is needed
	 * is i wanted a feature to just display the full solution without having to step through it.
	 * To show the paths which were not valid i am colouring those squares red in this method and then using this 
	 * method as opposed to the above one as this one returns the stack which only contains the correct path.
	 */
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
				pathString+=neighbouringSquare.getSquareType() + "  x:" + (neighbouringSquare.getX()) + " y:" + (neighbouringSquare.getY() + 1) + "\n";
				stackOfSquares.push(neighbouringSquare);
				if (neighbouringSquare.getSquareType() == '*') {
					break;
				}
			} else {
				stackOfSquares.pop();
			}
		}
		startingSquare.setColor(Color.BLUE);//resetting the start blue for display purposes only.
		return stackOfSquares;
	}

	/**
	 * @param table
	 * @param maze
	 * this method colours the correct path which is returned from the coloured dfs above which coloured
	 * everything visited red and then colours the corect path gray so it can be identified on the GUI.
	 */
	public void colorPath(JTable table, Maze maze)
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
	}

	/**
	 * @param table
	 * @param maze
	 * @return
	 * this method perfoms a breadth first search on the maze. this uses a queue data structure to do this.
	 * i have a second queue in this method as i needed one for the step function in the GUI. 
	 * i could not just use the one as the bfs returns an empty queue so i needed a seperate data structure to store the
	 * path taken which can then be used to step.
	 */
	public Queue<Square> breadthFirstSearch(JTable table, Maze maze)
	{
		Queue<Square> path = new LinkedList<Square>();
		Queue<Square> queue = new LinkedList<Square>();
		Square start = maze.getStart();
		queue.add(start);
		start.setVisited(true);
		flag:
			while(!queue.isEmpty()) {
				Square square = queue.remove();
				Square child=null;
				while((child = maze.getNeighbours(square))!=null) {
					path.add(child);
					child.setVisited(true);
					queue.add(child);
					pathString+= child.getSquareType() + "  x:" + (child.getX() + 2) + " y:" + (child.getY() + 1) + "\n";
					if (child.getSquareType() == '*') {
						break flag;
					}
				}
			}
		return path;
	}

	public String getPathString() {
		return pathString;
	}
}
