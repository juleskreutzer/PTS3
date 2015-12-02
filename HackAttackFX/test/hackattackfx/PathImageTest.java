/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import java.awt.Point;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
    
      public static class AsNonApp extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    public PathImageTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws InterruptedException {
        // Initialise Java FX
        System.out.printf("About to launch FX App\n");
        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(SpawnTargetImageTest.AsNonApp.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();
        System.out.printf("FX App thread started\n");
        Thread.sleep(500);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        start = new Point(100,100);
        
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
