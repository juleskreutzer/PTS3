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
import hackattackfx.templates.MinionTemplate;
import java.awt.Point;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.shape.Rectangle;
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
public class MinionImageTest {
    Data data;
    Point point1;
    Player p;
    Minion minTest;
    MinionImage miTest;
    MinionTemplate b;
    MinionTemplate kb;
    MinionTemplate mb;
    MinionTemplate gb;
    MinionTemplate tb;
    MinionTemplate pb; 
    
    public MinionImageTest() {
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
        data = new Data(new Data.UpdateProgress() {
            @Override
            public void update(double value) {
            }
        });
    } catch (IOException | InvalidMinionTypeException | InvalidSpellNameException | InvalidDefenseTypeException | InvalidEffectException ex) {
        Logger.getLogger(BitcoinMinerTest.class.getName()).log(Level.SEVERE, null, ex);
    }
        point1 = new Point(1,1);
        p = new Player(1,"player",1, point1);
        b = Data.DEFAULT_BYTE;
        kb = Data.DEFAULT_KILOBYTE;
        mb = Data.DEFAULT_MEGABYTE;
        gb = Data.DEFAULT_GIGABYTE;
        tb = Data.DEFAULT_TERABYTE;
        pb = Data.DEFAULT_PETABYTE;
        
        minTest = new Minion(b, 1, p);
        miTest = new MinionImage(minTest);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testContructor(){
        Minion m1= new Minion(b, 1, p);
        Minion m2= new Minion(kb, 1, p);
        Minion m3= new Minion(mb, 1, p);
        Minion m4= new Minion(gb, 1, p);
        Minion m5= new Minion(tb, 1, p);
        Minion m6= new Minion(pb, 1, p);
       
        MinionImage mi1 = new MinionImage(m1);
        MinionImage mi2 = new MinionImage(m2);
        MinionImage mi3 = new MinionImage(m3);
        MinionImage mi4 = new MinionImage(m4);
        MinionImage mi5 = new MinionImage(m5);
        MinionImage mi6 = new MinionImage(m6);
    }

    /**
     * Test of getMinion method, of class MinionImage.
     */
    @Test
    public void testGetMinion() {
        assertEquals(miTest.getMinion(), minTest);
    }

    /**
     * Test of getHealthBar method, of class MinionImage.
     */
    @Test
    public void testGetHealthBar() {
        assertNotNull(miTest.getHealthBar());
    }
    
}
