/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

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
    
}
