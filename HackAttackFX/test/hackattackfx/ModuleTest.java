/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.exceptions.InvalidDefenseTypeException;
import hackattackfx.exceptions.InvalidEffectException;
import hackattackfx.exceptions.InvalidMinionTypeException;
import hackattackfx.exceptions.InvalidModuleEnumException;
import hackattackfx.exceptions.InvalidSpellNameException;
import hackattackfx.templates.BitCoinMinerTemplate;
import hackattackfx.templates.CPUUpgradeTemplate;
import hackattackfx.templates.DefenseTemplate;
import hackattackfx.templates.SoftwareInjectorTemplate;
import java.awt.Point;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ModuleTest {
    Data data;
    Player enemyPlayer;

    Point point;
    Point point2;
    
    Module module1;
    Module module2;
    Module module3;
    Module module4;
    Module module5;
    Module module6;
    Module module7;
    
    DefenseTemplate templateDfBc;
    DefenseTemplate templateDfSc;
    DefenseTemplate templateDfSn;
    DefenseTemplate templateDfMu;
    BitCoinMinerTemplate templateBcm;
    SoftwareInjectorTemplate templateSwi;
    CPUUpgradeTemplate templateCu;
    
    Defense defense1;
    Defense defense2;
    Defense defense3;
    Defense defense4;
    BitcoinMiner bc;
    SoftwareInjector si;
    CPUUpgrade cpu;

    
    public ModuleTest() {
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
            
            point = new Point(100, 100);
            point2 = new Point(200, 200);
            
            templateDfBc = Data.DEFAULT_MODULE_DEFENSE_BOTTLECAP_1;
            templateDfSc = Data.DEFAULT_MODULE_DEFENSE_SCALE_1;
            templateDfSn = Data.DEFAULT_MODULE_DEFENSE_SNIPER_1;
            templateDfMu = Data.DEFAULT_MODULE_DEFENSE_MUSCLE_1;
            templateBcm = Data.DEFAULT_MODULE_BITCOINMINER_1;
            templateSwi = Data.DEFAULT_MODULE_SOFTWAREINJECTOR_1;
            templateCu = Data.DEFAULT_MODULE_CPUUPGRADE_1;
            
            defense1 = new Defense(templateDfBc, point, 50, 50);
            defense2 = new Defense(templateDfSc, point, 50, 50);
            defense3 = new Defense(templateDfSn, point, 50, 50);
            defense4 = new Defense(templateDfMu, point, 50, 50);
            bc = new BitcoinMiner(templateBcm, point, 50, 50);
            si = new SoftwareInjector(templateSwi, point, 50, 50);
            cpu = new CPUUpgrade(templateCu, point, 50, 50);
            
            module1 = (Module) defense1;
            module2 = (Module) defense2;
            module3 = (Module) defense3;
            module4 = (Module) defense4;
            module5 = (Module) bc;
            module6 = (Module) si;
            module7 = (Module) cpu;
            
        } catch (IOException | InvalidMinionTypeException | InvalidSpellNameException | InvalidDefenseTypeException | InvalidEffectException | InvalidModuleEnumException ex) {
            Logger.getLogger(ModuleTest.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getDisplayName method, of class Module.
     */
    @Test
    public void testGetDisplayName() {
        //This method is not implemented yet.
        assertEquals(module1.getDisplayName(), null);
    }

    /**
     * Test of getPosition method, of class Module.
     */
    @Test
    public void testSetGetPosition() {
        module1.setPosition(point2);
        assertEquals(module1.getPosition(), point2);
    }
    /**
     * Test of getWidth method, of class Module.
     */
    @Test
    public void testGetWidth() {
        assertEquals(module1.getWidth(), 50);
    }

    /**
     * Test of getHeight method, of class Module.
     */
    @Test
    public void testGetHeight() {
        assertEquals(module1.getHeight(), 50);
    }

    /**
     * Test of getCost method, of class Module.
     */
    @Test
    public void testGetCost() {
        assertEquals(module1.getCost(), templateDfBc.getCost(), 0);
    }

    /**
     * Test of getLevel method, of class Module.
     */
    @Test
    public void testGetSetLevel() {
        module1.setLevel(3);
        assertEquals(module1.getLevel(), 3);
    }

    /**
     * Test of getAllowBuild method, of class Module.
     */
    @Test
    public void testGetSetAllowBuild() {
        module1.setAllowBuild(true);
        assertTrue(module1.getAllowBuild());
    }

    /**
     * Test of getModuleName method, of class Module.
     */
    @Test
    public void testGetModuleName() {
        assertEquals(module1.getModuleName(), templateDfBc.getModuleName());
    }

    /**
     * Test of getName method, of class Module.
     */
    @Test
    public void testGetName() {
        assertEquals(module1.getName(), "Bottlecap Antivirus");
        assertEquals(module2.getName(), "Scale Antivirus");
        assertEquals(module3.getName(), "Sniper Antivirus");
        assertEquals(module4.getName(), "Muscle Antivirus");
        assertEquals(bc.getName(), "Bitcoin Miner");
        assertEquals(si.getName(), "Software Injector");
        assertEquals(cpu.getName(), "CPU Upgrade");
    }
}
