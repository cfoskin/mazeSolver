package test;

import static org.junit.Assert.*;

import java.awt.Color;

import model.Maze;
import model.Square;

import org.junit.Before;
import org.junit.Test;
public class MazeTest {
	private Maze maze;

	@Before
	public void setUp() throws Exception {
		maze = new Maze("complexMaze.txt");
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
		assertEquals(12, maze.getMazeWidth());
		assertFalse(maze.getMazeWidth()== 11);
		assertFalse(maze.getMazeWidth()== 10);
	}

	/**
	 *  Testing the getMazeHeight method using the current array which has a width of 10
	 */
	@Test
	public void testGetMazeHeight() {
		assertEquals(10, maze.getMazeHeight());
		assertFalse(maze.getMazeHeight()== 11);
		assertFalse(maze.getMazeHeight()== 9);
	}

	/**
	 *  Testing the getMazeArray method using the current array which has a width of 10
	 */
	@Test
	public void testGetMazeArray() {
		assertEquals(10, maze.getMazeArray().length);
		assertFalse(maze.getMazeArray().length == 12);
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
		assertEquals(Color.BLUE, maze.getValueAt(8,1));//starting square which i set to blue on creation of maze
	}
}