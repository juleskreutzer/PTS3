/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.enums.Effect;
import java.awt.Point;
import java.util.List;
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
        double expResult = 1;
        double result = 0;
        instance.buildBitcoinMiner();
        for (Module module : instance.getModules())
            if(module instanceof BitcoinMiner)
                result++;
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of upgradeBitcoinMiner method, of class Player.
     */
    @Test
    public void testUpgradeBitcoinMiner() {
        System.out.println("upgradeBitcoinMiner");
        Player instance = new Player();
        assertTrue(instance.upgradeBitcoinMiner());
    }

    /**
     * Test of buildCpuUpgrade method, of class Player.
     */
    @Test
    public void testBuildCPUUpgrade() {
        System.out.println("buildCPUUpgrade");
        Player instance = new Player();
        double expResult = 1;
        double result = 0;
        instance.buildCPUUpgrade();
        for (Module module : instance.getModules())
            if(module instanceof CPUUpgrade)
                result++;
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setHealth and getHealth method, of class Player.
     */
    @Test
    public void testSetGetHealth() {
        System.out.println("setHealth & getHealth");
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
        System.out.println("setName & getName");
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
    public void testSetGetBitcoins() {
        System.out.println("setBitcoins & getBitcoins");
        Player instance = new Player();
        double Bitcoins = 20.0;
        instance.setBitcoins(Bitcoins);
        double expResult = 20.0;
        double result = instance.getBitcoins();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of buildSoftwareInjector method, of class Player.
     */
    @Test
    public void testBuildSoftwareInjector() {
        System.out.println("buildSoftwareInjector");
        Player instance = new Player();
        double expResult = 1;
        double result = 0;
        instance.buildSoftwareInjector();
        for (Module module : instance.getModules())
            if(module instanceof SoftwareInjector)
                result++;
        assertEquals(expResult, result, 0.0);

    }

    /**
     * Test of upgradeSoftwareInjector method, of class Player.
     */
    @Test
    public void testUpgradeSoftwareInjector() {
        System.out.println("upgradeSoftwareInjector");
        Player instance = new Player();
        assertTrue(instance.upgradeSoftwareInjector());
    }
    
    /**
     * Test of receiveDamage method, of class Player.
     */
    @Test
    public void testReceiveDamage() {
        System.out.println("receiveDamage");
        double damage = 10.0;
        Player instance = new Player();
        instance.setHealth(20.0);
        instance.receiveDamage(damage);
        double result = instance.getHealth();
        double expResult = 10.0;
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getSpells method, of class Player.
     */
    @Test
    public void testGetSpells() {
        System.out.println("getSpells");
        Player instance = new Player();
        List<Spell> expResult = null;
        List<Spell> result = instance.getSpells();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of upgradeCPUUpgrade method, of class Player.
     */
    @Test
    public void testUpgradeCPUUpgrade() {
        System.out.println("upgradeCPUUpgrade");
        CPUUpgrade cpu = null;
        Player instance = new Player();
        boolean expResult = false;
        boolean result = instance.upgradeCPUUpgrade(cpu);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buildDefense method, of class Player.
     */
    @Test
    public void testBuildDefense() throws Exception {
        System.out.println("buildDefense");
        Defense defense = null;
        Player instance = new Player();
        Defense expResult = null;
        Defense result = instance.buildDefense(defense);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHealth method, of class Player.
     */
    @Test
    public void testGetHealth() {
        System.out.println("getHealth");
        Player instance = new Player();
        double expResult = 0.0;
        double result = instance.getHealth();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setHealth method, of class Player.
     */
    @Test
    public void testSetHealth() {
        System.out.println("setHealth");
        double Health = 0.0;
        Player instance = new Player();
        instance.setHealth(Health);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class Player.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Player instance = new Player();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class Player.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String Name = "";
        Player instance = new Player();
        instance.setName(Name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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

    /**
     * Test of getModules method, of class Player.
     */
    @Test
    public void testGetModules() {
        System.out.println("getModules");
        Player instance = new Player();
        List<Module> expResult = null;
        List<Module> result = instance.getModules();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addBitcoins method, of class Player.
     */
    @Test
    public void testAddBitcoins() {
        System.out.println("addBitcoins");
        double amount = 0.0;
        Player instance = new Player();
        instance.addBitcoins(amount);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeBitcoins method, of class Player.
     */
    @Test
    public void testRemoveBitcoins() throws Exception {
        System.out.println("removeBitcoins");
        double amount = 0.0;
        Player instance = new Player();
        instance.removeBitcoins(amount);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of upgradeDefense method, of class Player.
     */
    @Test
    public void testUpgradeDefense() throws Exception {
        System.out.println("upgradeDefense");
        Defense defense = null;
        Effect effect = null;
        Player instance = new Player();
        boolean expResult = false;
        boolean result = instance.upgradeDefense(defense, effect);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBaseLocation method, of class Player.
     */
    @Test
    public void testGetBaseLocation() {
        System.out.println("getBaseLocation");
        Player instance = new Player();
        Point expResult = null;
        Point result = instance.getBaseLocation();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
