/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import java.awt.Point;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Bart van Keersop
 */
public class PathTest {
    Path path1;
    Path path2;
    Path path3;
    Path path4;
    Point start;

    public PathTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        start = new Point(10, 10);
        
        path1 = new Path(start, 10, Path.Direction.Right);
        path2 = new Path(start, 10, Path.Direction.Up);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testContructor() {
        path1 = new Path(start, 10, Path.Direction.Right);
        path2 = new Path(start, 10, Path.Direction.Up);
        path3 = new Path(start, 10, Path.Direction.Left);
        path4 = new Path(start, 10, Path.Direction.Down);
    }
    
    /**
     * Test of getStart method, of class Path.
     */
    @Test
    public void testGetStart() {
        assertEquals(path1.getStart(), start);
    }

    /**
     * Test of getEnd method, of class Path.
     */
    @Test
    public void testGetEnd() {
        Point end = new Point((start.x + 10), start.y);
        assertEquals(path1.getEnd(), end);
    }

    /**
     * Test of getLength method, of class Path.
     */
    @Test
    public void testGetLength() {
        assertEquals(path1.getLength(), 10);
        assertEquals(path2.getLength(), 10);
    }

    /**
     * Test of getDirection method, of class Path.
     */
    @Test
    public void testGetDirection() {
     assertEquals(path1.getDirection(), Path.Direction.Right);
    }
    
}
