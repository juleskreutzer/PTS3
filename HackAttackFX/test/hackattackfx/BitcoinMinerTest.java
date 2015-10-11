/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

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
public class BitcoinMinerTest {
    
    public BitcoinMinerTest() {
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

    
    @Test
    public void testConstructor(){
        //TODO Write Tests.
    }
    /**
     * Test of upgrade method, of class BitcoinMiner.
     */
    @Test
    public void testUpgrade() {
        System.out.println("upgrade");
        BitcoinMiner instance = null;
        boolean expResult = false;
        boolean result = instance.upgrade();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
