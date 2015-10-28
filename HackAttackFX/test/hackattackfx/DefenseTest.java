/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.enums.DefenseType;
import hackattackfx.enums.Effect;
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
public class DefenseTest {
    GameEngine engine;
    Player player1;
    Wave wave;
    Minion minionTarget;
    
    
    public DefenseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of activate method, of class Defense.
     */
    @Test
    public void testActivate() {
        System.out.println("activate");
        Defense instance = null;
        instance.activate();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deactivate method, of class Defense.
     */
    @Test
    public void testDeactivate() {
        System.out.println("deactivate");
        Defense instance = null;
        instance.deactivate();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDefenceTypeString method, of class Defense.
     */
    @Test
    public void testGetDefenceTypeString() {
        System.out.println("getDefenceTypeString");
        Defense instance = null;
        String expResult = "";
        String result = instance.getDefenceTypeString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEffectString method, of class Defense.
     */
    @Test
    public void testGetEffectString() {
        System.out.println("getEffectString");
        Defense instance = null;
        String expResult = "";
        String result = instance.getEffectString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDefenceType method, of class Defense.
     */
    @Test
    public void testGetDefenceType() {
        System.out.println("getDefenceType");
        Defense instance = null;
        DefenseType expResult = null;
        DefenseType result = instance.getDefenceType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEffect method, of class Defense.
     */
    @Test
    public void testGetEffect() {
        System.out.println("getEffect");
        Defense instance = null;
        Effect expResult = null;
        Effect result = instance.getEffect();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEffect method, of class Defense.
     */
    @Test
    public void testSetEffect() {
        System.out.println("setEffect");
        Effect e = null;
        Defense instance = null;
        instance.setEffect(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDamage method, of class Defense.
     */
    @Test
    public void testGetDamage() {
        System.out.println("getDamage");
        Defense instance = null;
        double expResult = 0.0;
        double result = instance.getDamage();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDamage method, of class Defense.
     */
    @Test
    public void testSetDamage() {
        System.out.println("setDamage");
        double d = 0.0;
        Defense instance = null;
        instance.setDamage(d);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRange method, of class Defense.
     */
    @Test
    public void testGetRange() {
        System.out.println("getRange");
        Defense instance = null;
        int expResult = 0;
        int result = instance.getRange();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRange method, of class Defense.
     */
    @Test
    public void testSetRange() {
        System.out.println("setRange");
        int r = 0;
        Defense instance = null;
        instance.setRange(r);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of upgrade method, of class Defense.
     */
    @Test
    public void testUpgrade() throws Exception {
        System.out.println("upgrade");
        Defense instance = null;
        boolean expResult = false;
        boolean result = instance.upgrade();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findTarget method, of class Defense.
     */
    @Test
    public void testFindTarget() {
        System.out.println("findTarget");
        Defense instance = null;
        Minion expResult = null;
        Minion result = instance.findTarget();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of targetInRange method, of class Defense.
     */
    @Test
    public void testTargetInRange() {
        System.out.println("targetInRange");
        Minion m = null;
        Defense instance = null;
        boolean expResult = false;
        boolean result = instance.targetInRange(m);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasTarget method, of class Defense.
     */
    @Test
    public void testHasTarget() {
        System.out.println("hasTarget");
        Defense instance = null;
        boolean expResult = false;
        boolean result = instance.hasTarget();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fire method, of class Defense.
     */
    @Test
    public void testFire() {
        System.out.println("fire");
        Minion minion = null;
        Defense instance = null;
        instance.fire(minion);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
