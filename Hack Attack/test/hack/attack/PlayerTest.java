/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Igor
 */
public class PlayerTest {
    
    public PlayerTest() {
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
     * Test of buildBitcoinMiner method, of class Player.
     */
    @Test
    public void testBuildBitcoinMiner() {
        System.out.println("buildBitcoinMiner");
        Player instance = new Player();
        instance.buildBitcoinMiner();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of upgradeBitcoinMiner method, of class Player.
     */
    @Test
    public void testUpgradeBitcoinMiner() {
        System.out.println("upgradeBitcoinMiner");
        Player instance = new Player();
        instance.upgradeBitcoinMiner();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buildCpuUpgrade method, of class Player.
     */
    @Test
    public void testBuildCpuUpgrade() {
        System.out.println("buildCpuUpgrade");
        Player instance = new Player();
        instance.buildCpuUpgrade();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setHealth and getHealth method, of class Player.
     */
    @Test
    public void testSetGetHealth() {
        System.out.println("setHealth");
        double Health = 99.88;
        Player instance = new Player();
        instance.setHealth(Health);
        double expResult = Health;
        double result = instance.getHealth();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setName and getName method, of class Player.
     */
    @Test
    public void testSetGetName() {
        System.out.println("setName");
        String Name = "SetGetNameTest";
        Player instance = new Player();
        instance.setName(Name);
        String expResult = Name;
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getBitcoins method, of class Player.
     */
    @Test
    public void testGetBitcoins() {
        System.out.println("getBitcoins");
        Player instance = new Player();
        double expResult = 0.0;
        double result = instance.getBitcoins();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBitcoins method, of class Player.
     */
    @Test
    public void testSetBitcoins() {
        System.out.println("setBitcoins");
        double Bitcoins = 0.0;
        Player instance = new Player();
        instance.setBitcoins(Bitcoins);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
