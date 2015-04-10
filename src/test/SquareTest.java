package test;

import static org.junit.Assert.*;
import java.awt.Color;
import model.Square;
import org.junit.Before;
import org.junit.Test;

/**
* @author Colum Foskin version 1.0
 * 26/02/15 
 * this junit test case tests the Square model.
 */
public class SquareTest {
	private Square pathSquare;
	private Square wallSquare;

	@Before
	public void setUp() throws Exception {
		pathSquare = new Square('.', 2, 2);
		pathSquare.setVisited(true);		
		wallSquare = new Square('#', 2, 3);
	}

	/**
	 * Test for the get colour method
	 */
	@Test
	public void testGetColour() {
		assertTrue(pathSquare.getColor()== Color.WHITE);
		assertTrue(wallSquare.getColor() == Color.BLACK);
		assertFalse(pathSquare.getColor() == Color.BLACK);
		assertFalse(wallSquare.getColor() == Color.WHITE);
	}

	/**
	 * Test for the set colour method
	 */
	@Test
	public void testSetColour() {
		pathSquare.setColor(Color.PINK);
		assertTrue(pathSquare.getColor() == Color.PINK);
		assertFalse(pathSquare.getColor() == Color.WHITE);
	}

	/**
	 * Test for the get char type method
	 */
	@Test
	public void testGetCharType()
	{
		assertTrue(pathSquare.getSquareType() == '.');
		assertFalse(pathSquare.getSquareType() == '#');
	}

	/**
	 * Test for the get x & y coordinates
	 */
	@Test
	public void testGetXandY()
	{
		assertTrue(pathSquare.getX() == 2);
		assertFalse(pathSquare.getX() == 1);
		assertTrue(wallSquare.getY() == 3);
	}

	/**
	 * Test for the getvisited method
	 */
	@Test
	public void testGetVisited()
	{
		assertTrue(pathSquare.getVisited() == true);
		assertFalse(pathSquare.getVisited() == false);
	}

	/**
	 * Test for the set colour method
	 */
	@Test
	public void testSetVisited()
	{
		pathSquare.setVisited(false);
		assertTrue(pathSquare.getVisited() == false);
	}
}