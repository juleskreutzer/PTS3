/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
public class FXMLDocumentControllerTest {
    FXMLDocumentController fxd;
    ImageView imgView;
    Node node1;
    
     public static class AsNonApp extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    public FXMLDocumentControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        Thread t = new Thread("JavaFX Init Thread") {
        public void run() {
        Application.launch(AsNonApp.class, new String[0]);
        }
    };
        t.setDaemon(true);
        t.start();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        fxd = new FXMLDocumentController();
        imgView = new ImageView();
        node1 = (Node) imgView;  
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testConstructor(){
        FXMLDocumentController fxd2 = new FXMLDocumentController();
    }
    /**
     * Test of initialize method, of class FXMLDocumentController.
     */
    @Test
    public void testInitialize() {
     
    }

    /**
     * Test of addNode method, of class FXMLDocumentController.
     */
    @Test
    public void testAddNode() {
        //Idk how to test
        fxd.addNode(node1);
    }

    /**
     * Test of removeNode method, of class FXMLDocumentController.
     */
    @Test
    public void testRemoveNode() {
        //Idk how to test
        fxd.addNode(node1);
        fxd.removeNode(node1);
    }

    /**
     * Test of getInstance method, of class FXMLDocumentController.
     */
    @Test
    public void testGetInstance() {
        assertNotNull(fxd.getInstance());
    }

    /**
     * Test of getNode method, of class FXMLDocumentController.
     */
    @Test
    public void testGetNode() {
        //IDK how to test
    }

    /**
     * Test of getScene method, of class FXMLDocumentController.
     */
    @Test
    public void testGetScene() {
        //IDK how to test
    }

    /**
     * Test of getAllNodes method, of class FXMLDocumentController.
     */
    @Test
    public void testGetAllNodes() {
        //IDK how to test
    }
    
}
