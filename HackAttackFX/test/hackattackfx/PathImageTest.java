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
public class PathImageTest {
    Path path1;
    Path path2;
    Path path3;
    Path path4;
    Point start;
    
    public PathImageTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        path1 = new Path(start, 10, Path.Direction.Right);
        path2 = new Path(start, 10, Path.Direction.Up);
        path3 = new Path(start, 10, Path.Direction.Left);
        path4 = new Path(start, 10, Path.Direction.Down);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testContructor() {
        PathImage pi1 = new PathImage(path1);
        PathImage pi2 = new PathImage(path2);
        PathImage pi3 = new PathImage(path3);
        PathImage pi4 = new PathImage(path4);
    }
    
}
