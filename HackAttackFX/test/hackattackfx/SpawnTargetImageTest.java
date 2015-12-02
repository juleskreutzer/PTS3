/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.exceptions.InvalidDefenseTypeException;
import hackattackfx.exceptions.InvalidEffectException;
import hackattackfx.exceptions.InvalidMinionTypeException;
import hackattackfx.exceptions.InvalidSpellNameException;
import hackattackfx.templates.ModuleTemplate;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.image.Image;
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
public class SpawnTargetImageTest {
    Data data;
    Image available;
    Image unavailable;
    SpawnTargetImage spawn;
    ModuleTemplate template;
    File file;
    
    public static class AsNonApp extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    
    public SpawnTargetImageTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws InterruptedException {
    // Initialise Java FX
    System.out.printf("About to launch FX App\n");
    Thread t = new Thread("JavaFX Init Thread") {
        public void run() {
            Application.launch(AsNonApp.class, new String[0]);
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
        try {
        data = new Data(new Data.UpdateProgress() {
            @Override
            public void update(double value) {
            }
        });
    } catch (IOException | InvalidMinionTypeException | InvalidSpellNameException | InvalidDefenseTypeException | InvalidEffectException ex) {
        Logger.getLogger(BitcoinMinerTest.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        template = Data.DEFAULT_MODULE_DEFENSE_BOTTLECAP_1;
        file = new File("src/hackattackfx/resources/error.png");
        available = new Image(file.toURI().toString());
        unavailable = new Image(file.toURI().toString());
        spawn = new SpawnTargetImage(template, available, unavailable);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testConstructor() {
        SpawnTargetImage spawn2 = new SpawnTargetImage(template, available, unavailable);
    }
    /**
     * Test of getUnavailable method, of class SpawnTargetImage.
     */
    @Test
    public void testGetUnavailable() {
        //Checks if the width of the image matches, no other way to compare images.
        assertEquals(spawn.getUnavailable().getWidth(), unavailable.getWidth(), 0);
    }

    /**
     * Test of getAvailable method, of class SpawnTargetImage.
     */
    @Test
    public void testGetAvailable() {
        //Checks if the width of the image matches, no other way to compare images.
        assertEquals(spawn.getAvailable().getWidth(), available.getWidth(), 0);
    }
    
}
