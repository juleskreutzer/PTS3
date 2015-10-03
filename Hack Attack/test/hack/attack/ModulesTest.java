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
import hack.attack.exceptions.*;

/**
 *
 * @author juleskreutzer
 */
public class ModulesTest {
    Double cost;
    Point position;
    int level;
    int value;
    int width;
    int height;
    
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
        cost = 1000.0;
        position = new Point(100,50);
        level = 1;
        value = 100;
        width = 100;
        height = 100;
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
        ModuleName name = ModuleName.BITCOIN_MINER;
        BitcoinMiner tempBM = new BitcoinMiner(cost, position, width, height, level);
        assertEquals(cost, tempBM.getCost(), 0.001);
        assertEquals(position, tempBM.getPosition());
        assertEquals(width, tempBM.getWidth());
        assertEquals(height, tempBM.getHeight());
        assertSame("Bitcoin Miner", tempBM.getDisplayName());
    }
    
    @Test
    public void TestBitcoinMinerConstructorCorrupt()
    {
        ModuleName name = ModuleName.CPU_UPGRADE;
        BitcoinMiner tempBM = new BitcoinMiner(cost, position, width, height, level);
        exception.expect(InvalidModuleEnumException.class);
    }
    
    @Test
    public void TestCPUUpgradeConstructor()
    {
        ModuleName name = ModuleName.CPU_UPGRADE;
        double bonus = 10.0;
        CPUUpgrade tempCU = new CPUUpgrade(position, width, height, cost, level);
        
        assertEquals(cost, tempCU.getCost(), 0.001);
        assertEquals(position, tempCU.getPosition());
        assertEquals(width, tempCU.getWidth());
        assertEquals(height, tempCU.getHeight());
        assertSame("CPU Upgrade", tempCU.getDisplayName());
    }
    
    @Test
    public void TestCPUUpgradeConstructorCorrupt()
    {
        ModuleName name = ModuleName.BOTTLECAP_ANTIVIRUS;
        double bonus = 10.0;
        CPUUpgrade tempCU = new CPUUpgrade(position, width, height, cost, level);
        exception.expect(InvalidModuleEnumException.class);
    }
    
    @Test
    public void TestSoftwareInjectorConstructor()
    {
        ModuleName name = ModuleName.SOFTWARE_INJECTOR;
        SoftwareInjector tempSI = new SoftwareInjector(cost, position, width, height, level, name);
        assertEquals(cost, tempSI.getCost(), 0.001);
        assertEquals(position, tempSI.getPosition());
        assertEquals(width, tempSI.getWidth());
        assertEquals(height, tempSI.getHeight());
        assertSame("Software Injector", tempSI.getDisplayName());
    }
    
    @Test 
    public void TestSoftwareInjectorConstructorCorrupt()
    {
        ModuleName name = ModuleName.CPU_UPGRADE;
        SoftwareInjector tempSI = new SoftwareInjector(cost, position, width, height, level, name);
        exception.expect(InvalidModuleEnumException.class);
    }
}
