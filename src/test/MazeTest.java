package test;

import static org.junit.Assert.*;
import java.awt.Color;
import model.Maze;
import model.Square;
import org.junit.Before;
import org.junit.Test;
/**
* @author Colum Foskin version 1.0
 * 26/02/15 
 * this junit test case tests the Maze model
 */
public class MazeTest {
	private Maze maze;

	@Before
	public void setUp() throws Exception {
		maze = new Maze("Maze1.txt");
	}

	/**
	 * testing that the maze exists
	 */
	@Test
	public void testMaze() {
		assertNotNull(maze);
	}

	/**
	 *  Testing the getMazeLength method using the current array which has a length of 12
	 */
	@Test
	public void testGetMazeWidth() {
		assertEquals(7, maze.getMazeWidth());
		assertFalse(maze.getMazeWidth()== 6);
		assertFalse(maze.getMazeWidth()== 8);
	}

	/**
	 *  Testing the getMazeHeight method using the current array which has a width of 10
	 */
	@Test
	public void testGetMazeHeight() {
		assertEquals(5, maze.getMazeHeight());
		assertFalse(maze.getMazeHeight()== 6);
		assertFalse(maze.getMazeHeight()== 4);
	}

	/**
	 *  Testing the getMazeArray method using the current array which has a width of 10
	 */
	@Test
	public void testGetMazeArray() {
		assertEquals(5, maze.getMazeArray().length);
		assertFalse(maze.getMazeArray().length == 7);
	}

	/**
	 * Testing the getStart method 
	 */
	@Test
	public void testGetStart() {
		assertEquals('o', maze.getStart().getSquareType());
		assertFalse(maze.getStart().getSquareType() == '*');
		assertFalse(maze.getStart().getSquareType() == '.');
	}

	/**
	 * testing the getNeighbours method. The starting square has a neighbour and the 
	 * square [0][0] has no neighbour in the case of the maze I am using for testing purposes.
	 */
	@Test
	public void testGetNeighbours() {
		Square[][] testArray= maze.getMazeArray();
		assertTrue(maze.getNeighbours(maze.getStart())!= null);
		Square noNeighboursSquare = testArray[0][0];//this has no neighbours
		assertTrue(maze.getNeighbours(noNeighboursSquare) == null);
		assertFalse(maze.getNeighbours(noNeighboursSquare) != null);
	}

	@Test
	public void testGetValueAt(){
		assertEquals(Color.BLACK, maze.getValueAt(0, 0));
		assertFalse(maze.getValueAt(0, 0)== Color.GREEN);
		assertEquals(Color.BLUE, maze.getValueAt(1,2));//starting square which i set to blue on creation of maze
	}
}