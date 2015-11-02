/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.exceptions.FloatingPathException;
import java.awt.Point;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class RoadTest {
    Road road;
    Road road2;
    Path pathBefore;
    Path pathAfter;
    Path pathRandom;
    Point baseLocationA;
    Point baseLocationB;
    Point test;
    Point before;
    Point random;
    
    public RoadTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        try {
            road = new Road();
            road2 = new Road();

            baseLocationA = new Point(135,430);
            baseLocationB = new Point(500,500);
            test = new Point(10, 10);
            before = new Point(0, 10);
            random = new Point (99, 87);
            
            road.addPath(new Path(new Point(baseLocationA.x, baseLocationA.y),50, Path.Direction.Right));
            road.addPath(new Path(road.getEnd(),70, Path.Direction.Up));
            road.addPath(new Path(road.getEnd(),360, Path.Direction.Right));
            road.addPath(new Path(road.getEnd(),140, Path.Direction.Down));
            road.addPath(new Path(road.getEnd(),180, Path.Direction.Right));
            road.addPath(new Path(road.getEnd(),140, Path.Direction.Up));
            road.addPath(new Path(road.getEnd(),95, Path.Direction.Right));
            road.addPath(new Path(road.getEnd(),165, Path.Direction.Up));
            road.addPath(new Path(road.getEnd(),360, Path.Direction.Right));
            road.addPath(new Path(road.getEnd(),70, Path.Direction.Down));
            road.addPath(new Path(road.getEnd(),50, Path.Direction.Right));

            road2.addPath(new Path(test, 1, Path.Direction.Right));
            pathBefore = new Path(before, 10, Path.Direction.Right);
            pathAfter = new Path(road2.getEnd(), 10, Path.Direction.Right);
            pathRandom = new Path(random, 3, Path.Direction.Right);
 
        } catch (FloatingPathException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getBegin method, of class Road.
     */
    @Test
    public void testGetBegin() {
        assertEquals(road.getBegin(), baseLocationA);
    }

    /**
     * Test of getEnd method, of class Road.
     */
    @Test
    public void testGetEnd() {
        Point testPoint = new Point(1230, 265);
        assertEquals(road.getEnd(), testPoint);
    }

    /**
     * Test of getPaths method, of class Road.
     */
    @Test
    public void testGetPaths() {
        List<Path> paths = road.getPaths();
        assertFalse(paths.isEmpty());
    }

    /**
     * Test of addPath method, of class Road.
     */
    @Test
    public void testAddPath() throws Exception {
        assertEquals(road2.getPaths().size(), 1);
        road2.addPath(pathBefore);
        assertEquals(road2.getPaths().size(), 2);
        road2.addPath(pathAfter);
        assertEquals(road2.getPaths().size(), 3);
    }
    
    @Test
    public void testAddPathFail() {
        assertEquals(road2.getPaths().size(), 1);
        try {
            road2.addPath(pathRandom);
        } catch (FloatingPathException ex) {
            Logger.getLogger(RoadTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(road2.getPaths().size(), 1);
    }
    
}
