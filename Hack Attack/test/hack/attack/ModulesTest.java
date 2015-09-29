package hack.attack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import hack.attack.enums.ModuleName;
import java.awt.Point;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author juleskreutzer
 */
public class ModulesTest {
    private BitcoinMiner bm;
    private BitcoinMiner bm_corrupt;
    
    public ModulesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        Double cost = 1000.0;
        Point position = new Point(100,50);
        int level = 1;
        int width = 100;
        int height = 100;
        
        
        
        bm = new hack.attack.BitcoinMiner(cost, position, width, height, level, ModuleName.BITCOIN_MINER, 100);
        bm_corrupt = new hack.attack.BitcoinMiner(cost, position, width, height, level, ModuleName.CPU_UPGRADE, 250);
    }
    
    @After
    public void tearDown() {
    }
    
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void TestBitcoinMinerConstructor()
    {
        Double cost = 1000.0;
        Point position = new Point(100,50);
        int level = 1;
        ModuleName name = ModuleName.BITCOIN_MINER;
        int value = 100;
        int width = 100;
        int height = 100;
        
        BitcoinMiner tempBM = new BitcoinMiner(cost, position, width, height, level, name, value);
        assertEquals(cost, tempBM.getCost(), 0.001);
        assertEquals(position, tempBM.getPosition());
        assertEquals(width, tempBM.getWidth());
        assertEquals(height, tempBM.getHeight());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void TestBitcoinMinerConstructorCorrupt()
    {
        Double cost = 1000.0;
        Point position = new Point(100,50);
        int level = 1;
        ModuleName name = ModuleName.CPU_UPGRADE;
        int value = 100;
        int width = 100;
        int height = 100;
        
        BitcoinMiner tempBM = new BitcoinMiner(cost, position, width, height, level, name, value);
        exception.expect(IllegalArgumentException.class);
        
    }
}
