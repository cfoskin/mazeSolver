package test;

import static org.junit.Assert.assertTrue;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import model.Maze;
import model.Square;
import org.junit.Before;
import org.junit.Test;
import controller.MazeSolver;
/**
* @author Colum Foskin version 1.0
 * 26/02/15 
 * this Junit test case tests the depth first search and breadth first search by creating the known correct path for
 * the given maze and then checking if the respective paths are correct.
 */
public class TestMazeSolver {
	private MazeSolver mazeSolver;
	private Maze maze;
	private Stack<Square> correctPathStack;
	private Queue<Square> correctPathQueue;//this path will change depending on the maze loaded

	@Before
	public void setUp() throws Exception {
		maze = new Maze("Maze1.txt");
		mazeSolver = new MazeSolver(maze);

		correctPathStack = new Stack<Square>();
		correctPathQueue = new LinkedList<Square>();
		Square sq1 = new Square('.', 1, 2);//creating squares which are the expected path for the loaded maze.
		Square sq2 = new Square('.', 1, 3);
		Square sq3 = new Square('.', 1, 4);
		Square sq4 = new Square('.', 1, 5);
		Square sq5 = new Square('.', 2, 5);
		Square sq6 = new Square('.', 3, 5);
		Square sq7 = new Square('.', 3, 4);
		Square sq8 = new Square('.', 3, 3);
		Square sq9 = new Square('*', 3, 2);
		Square sq0 = new Square('.', 1,1);
		Square sq10 = new Square('.', 3,1);


		correctPathStack.push(sq9);
		correctPathStack.push(sq8);
		correctPathStack.push(sq7);
		correctPathStack.push(sq6);
		correctPathStack.push(sq5);
		correctPathStack.push(sq4);
		correctPathStack.push(sq3);
		correctPathStack.push(sq2);
		correctPathStack.push(sq1);

		correctPathQueue.add(sq2);
		correctPathQueue.add(sq0);
		correctPathQueue.add(sq3);
		correctPathQueue.add(sq4);
		correctPathQueue.add(sq5);
		correctPathQueue.add(sq6);
		correctPathQueue.add(sq7);
		correctPathQueue.add(sq8);
		correctPathQueue.add(sq9);
		correctPathQueue.add(sq10);
	}

	/**
	 * This test check if the co-ordinates for the Squares from the depth first search
	 * match up with that of the known correct path.
	 */
	@Test
	public void testDepthFirstSearch() {
		Stack<Square> dfsPath = mazeSolver.reversePath(maze);
		assertTrue(dfsPath.pop().getX() == correctPathStack.pop().getX());
		assertTrue(dfsPath.pop().getY() == correctPathStack.pop().getY());
		assertTrue(dfsPath.pop().getX() == correctPathStack.pop().getX());
		assertTrue(dfsPath.pop().getY() == correctPathStack.pop().getY());
		assertTrue(dfsPath.pop().getX() == correctPathStack.pop().getX());
		assertTrue(dfsPath.pop().getY() == correctPathStack.pop().getY());
		assertTrue(dfsPath.pop().getX() == correctPathStack.pop().getX());
		assertTrue(dfsPath.pop().getY() == correctPathStack.pop().getY());
		assertTrue(dfsPath.pop().getY() == correctPathStack.pop().getY());//last element in the paths
		assertTrue(dfsPath.isEmpty());
	}
	/**
	 * This test check if the co-ordinates for the Squares from the breadth first search
	 * match up with that of the known correct path.
	 */
	@Test
	public void testBreadthFirstSearch() {
		Queue<Square> bfsPath = mazeSolver.breadthFirstSearch(maze);
		assertTrue(correctPathQueue.poll().getX() == bfsPath.poll().getX());
		assertTrue(correctPathQueue.poll().getY() == bfsPath.poll().getY());
		assertTrue(correctPathQueue.poll().getX() == bfsPath.poll().getX());
		assertTrue(correctPathQueue.poll().getY() == bfsPath.poll().getY());
		assertTrue(correctPathQueue.poll().getX() == bfsPath.poll().getX());
		assertTrue(correctPathQueue.poll().getY() == bfsPath.poll().getY());
		assertTrue(correctPathQueue.poll().getX() == bfsPath.poll().getX());
		assertTrue(correctPathQueue.poll().getY() == bfsPath.poll().getY());
		assertTrue(correctPathQueue.poll().getX() == bfsPath.poll().getX());
		assertTrue(correctPathQueue.poll().getY() == bfsPath.poll().getY());
		assertTrue(bfsPath.isEmpty());
	}
}