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
public class ModuleImageTest {
    Data data;
    Point point1;
    BitCoinMinerTemplate templateBcm;
    SoftwareInjectorTemplate templateSwi;
    CPUUpgradeTemplate templateCu;
    DefenseTemplate templateDfBc;
    DefenseTemplate templateDfSc;
    DefenseTemplate templateDfSn;
    DefenseTemplate templateDfMu;
    Defense defense;
    Module module;
    ModuleImage moduleImage;
    
    public ModuleImageTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws InvalidModuleEnumException {
    try {
        data = new Data(new Data.UpdateProgress() {
            @Override
            public void update(double value) {
            }
        });
    } catch (IOException | InvalidMinionTypeException | InvalidSpellNameException | InvalidDefenseTypeException | InvalidEffectException ex) {
        Logger.getLogger(BitcoinMinerTest.class.getName()).log(Level.SEVERE, null, ex);
    }
    point1 = new Point(100, 100);
    templateBcm = Data.DEFAULT_MODULE_BITCOINMINER_1;
    templateSwi = Data.DEFAULT_MODULE_SOFTWAREINJECTOR_1;
    templateCu = Data.DEFAULT_MODULE_CPUUPGRADE_1;
    templateDfBc = Data.DEFAULT_MODULE_DEFENSE_BOTTLECAP_1;
    templateDfSc = Data.DEFAULT_MODULE_DEFENSE_SCALE_1;
    templateDfSn = Data.DEFAULT_MODULE_DEFENSE_SNIPER_1;
    templateDfMu = Data.DEFAULT_MODULE_DEFENSE_MUSCLE_1;
    defense = new Defense(templateDfBc, point1, 50, 50);
    module = (Module) defense;
    moduleImage = new ModuleImage(module); 
    }
    
    @After
    public void tearDown() {
    }

    public void testContructor() throws InvalidModuleEnumException{
        Defense defense1 = new Defense(templateDfBc, point1, 50, 50);
        Defense defense2 = new Defense(templateDfSc, point1, 50, 50);
        Defense defense3 = new Defense(templateDfSn, point1, 50, 50);
        Defense defense4 = new Defense(templateDfMu, point1, 50, 50);
        BitcoinMiner bc = new BitcoinMiner(templateBcm, point1, 50, 50);
        SoftwareInjector si = new SoftwareInjector(templateSwi, point1, 50, 50);
        CPUUpgrade cpu = new CPUUpgrade(templateCu, point1, 50, 50);
    }
    /**
     * Test of showRange method, of class ModuleImage.
     */
    @Test
    public void testShowRange() {
        assertEquals(moduleImage.showRange(), defense.getRange());
    }
}
