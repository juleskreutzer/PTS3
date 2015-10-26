/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.enums.Effect;
import hackattackfx.exceptions.InvalidModuleEnumException;
import hackattackfx.exceptions.NotEnoughBitcoinsException;
import hackattackfx.templates.BitCoinMinerTemplate;
import hackattackfx.templates.CPUUpgradeTemplate;
import hackattackfx.templates.SoftwareInjectorTemplate;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
 * @author Igor
 */
public class PlayerTest {
    Player player1;
    Player player2;
    Player playerWithMoney;
    
    BitcoinMiner bitcoinMiner;
    CPUUpgrade cpuUpgrade;
    SoftwareInjector softwareInjector;
    
    BitCoinMinerTemplate bitcoinTemplate = Data.DEFAULT_MODULE_BITCOINMINER_1;
    CPUUpgradeTemplate cpuUpgradeTemplate = Data.DEFAULT_MODULE_CPUUPGRADE_1;
    SoftwareInjectorTemplate softwareInjectorTemplate = Data.DEFAULT_MODULE_SOFTWAREINJECTOR_1;
    
    Point point1;
    Point point2;
    Point point3;
    Point point4;
    Point point5;
    Point point6;
    
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
        point1 = new Point(100, 100);
        point2 = new Point(200, 200);
        point3 = new Point(300, 300);
        point4 = new Point(400, 400);
        point5 = new Point(500, 500);
        point6 = new Point(600, 600);
        
        player1 = new Player(100, "player1", 999999, point1);
        player2 = new Player(1, "player2", 1, point2);
        playerWithMoney = new Player(1, "playerWithMoney", 999999, point4);
        
        try {
            bitcoinMiner = new BitcoinMiner(bitcoinTemplate, point3, 50, 50);
            cpuUpgrade = new CPUUpgrade(cpuUpgradeTemplate, point4, 50, 50);
            softwareInjector = new SoftwareInjector(softwareInjectorTemplate, point5, 50, 50);
            player1.buildBitcoinMiner(bitcoinMiner);
            player2.buildCPUUpgrade(cpuUpgrade);
        } catch (InvalidModuleEnumException | NotEnoughBitcoinsException ex) {
            Logger.getLogger(PlayerTest.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Setup went wrong!");
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of buildBitcoinMiner method, of class Player.
     */
    @Test
    public void testBuildBitcoinMiner(){       
        //This player has no bitcoins and cannot build.
        try {
            player2.buildBitcoinMiner(bitcoinMiner);
        } catch (NotEnoughBitcoinsException ex) {
            Logger.getLogger(PlayerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //This player has bitcoins and can build.
        try {
            playerWithMoney.buildBitcoinMiner(bitcoinMiner);
        } catch (NotEnoughBitcoinsException ex) {
            Logger.getLogger(PlayerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Create a list
         List<Module> modules;
         
         //Check if player2 has no modules
         modules = player2.getModules();
         assertTrue(modules.isEmpty());
         
         //Check if playerWithMoney has the bitcoinminr module.
         modules = playerWithMoney.getModules();
         assertEquals(modules.get(0).getModuleName(), bitcoinMiner.moduleName);
    }

    /**
     * Test of upgradeBitcoinMiner method, of class Player.
     */
    @Test
    public void testUpgradeBitcoinMinerSucces() throws NotEnoughBitcoinsException {
        List<Module> modules;
        
        //Can upgrade
        modules = player1.getModules();
        BitcoinMiner bitcoinMiner1 = (BitcoinMiner) modules.get(0);
        player1.upgradeBitcoinMiner(bitcoinMiner1);
        
        assertEquals("Levels aren't equal", bitcoinMiner.getLevel(), 2);
    }
    
      /**
     * Test of upgradeBitcoinMiner method, of class Player.
     */
    @Test
    public void testUpgradeBitcoinMinerFail() throws NotEnoughBitcoinsException {
        List<Module> modules;
        
        player1.setBitcoins(0);
        modules = player1.getModules();
        BitcoinMiner bitcoinMiner = (BitcoinMiner) modules.get(0);
        player1.upgradeBitcoinMiner(bitcoinMiner);
        
        assertEquals("Levels aren't equal", bitcoinMiner.getLevel(), 1);
    }

    /**
     * Test of buildCpuUpgrade method, of class Player.
     */
    @Test
    public void testBuildCPUUpgrade() {
        //This player has no bitcoins and cannot build.
        try {
            player2.buildCPUUpgrade(cpuUpgrade);
        } catch (NotEnoughBitcoinsException ex) {
            Logger.getLogger(PlayerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //This player has bitcoins and can build.
        try {
            playerWithMoney.buildCPUUpgrade(cpuUpgrade);
        } catch (NotEnoughBitcoinsException ex) {
            Logger.getLogger(PlayerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Create a list
         List<Module> modules;
         
         //Check if player2 has no modules
         modules = player2.getModules();
         assertTrue(modules.isEmpty());
         
         //Check if playerWithMoney has the CPUUpgrade module.
         modules = playerWithMoney.getModules();
         assertEquals(modules.get(0).getModuleName(), cpuUpgrade.moduleName);
    }

        /**
     * Test of upgradeCPUUpgrade method, of class Player.
     */
    @Test
    public void testUpgradeCPUUpgradeSucces() {
         List<Module> modules;
        
        //Can upgrade
        modules = player1.getModules();
        CPUUpgrade cpuUpgrade1 = (CPUUpgrade) modules.get(0);
        player1.upgradeCPUUpgrade(cpuUpgrade1);
        
        assertEquals("Levels aren't equal", cpuUpgrade.getLevel(), 2);
    }
    
        /**
     * Test of upgradeCPUUpgrade method, of class Player.
     */
    @Test
    public void testUpgradeCPUUpgradeFail() {
        List<Module> modules;
        
        player1.setBitcoins(0);
        modules = player1.getModules();
        CPUUpgrade cpuUpgrade1 = (CPUUpgrade) modules.get(0);
        player1.upgradeCPUUpgrade(cpuUpgrade1);
        
        assertEquals("Levels aren't equal", bitcoinMiner.getLevel(), 1);
    }
    
    
    /**
     * Test of setHealth and getHealth method, of class Player.
     */
    @Test
    public void testSetGetHealth() {
        //Test getHealth
        assertEquals("getHealth error.", 100, player1.getHealth());
        
        //Test setHealth
        player1.setHealth(101);
        assertEquals("setHealth error.", 101, player1.getHealth());
    }

    /**
     * Test of setName and getName method, of class Player.
     */
    @Test
    public void testSetGetName() {
        //Test getName
        assertEquals("getName error.", "player1", player1.getName());
        
        //Test setName
        player1.setName("Jaap");
        assertEquals("setName error.", "Jaap", player1.getName());
    }

    /**
     * Test of getBitcoins method, of class Player.
     */
    @Test
    public void testSetGetBitcoins() {
       //Test getBitcoins
        assertEquals("getBitcoins error.", 0, player2.getBitcoins());
        
        //Test setBitcoins
        player1.setBitcoins(100);
        assertEquals("setBitcoins error.", 100, player1.getBitcoins());
    }

    /**
     * Test of buildSoftwareInjector method, of class Player.
     */
    @Test
    public void testBuildSoftwareInjector() {
        //This player has no bitcoins and cannot build.
        try {
            player2.buildSoftwareInjector(softwareInjector);
        } catch (NotEnoughBitcoinsException ex) {
            Logger.getLogger(PlayerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //This player has bitcoins and can build.
        try {
            playerWithMoney.buildSoftwareInjector(softwareInjector);
        } catch (NotEnoughBitcoinsException ex) {
            Logger.getLogger(PlayerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Create a list
         List<Module> modules;
         
         //Check if player2 has no modules
         modules = player2.getModules();
         assertTrue(modules.isEmpty());
         
         //Check if playerWithMoney has the bitcoinminr module.
         modules = playerWithMoney.getModules();
         assertEquals(modules.get(0).getModuleName(), softwareInjector.moduleName);
    }

    /**
     * Test of upgradeSoftwareInjector method, of class Player.
     */
    @Test
    public void testUpgradeSoftwareInjectorSucces() throws NotEnoughBitcoinsException {
         List<Module> modules;
        
        //Can upgrade
        modules = player1.getModules();
        SoftwareInjector softwareInjector1 = (SoftwareInjector) modules.get(0);
        player1.upgradeSoftwareInjector(softwareInjector1);
        
        assertEquals("Levels aren't equal", softwareInjector.getLevel(), 2);
    }
    
    /**
     * Test of upgradeSoftwareInjector method, of class Player.
     */
    @Test
    public void testUpgradeSoftwareInjectorFail() throws NotEnoughBitcoinsException {
        List<Module> modules;
        
        player1.setBitcoins(0);
        modules = player1.getModules();
        SoftwareInjector softwareInjector1 = (SoftwareInjector) modules.get(0);
        player1.upgradeSoftwareInjector(softwareInjector1);
        
        assertEquals("Levels aren't equal", bitcoinMiner.getLevel(), 1);
    }
    
    /**
     * Test of receiveDamage method, of class Player.
     */
    @Test
    public void testReceiveDamage() {
        player1.receiveDamage(10);
        assertEquals("recieveDamage error", 90, player1.getHealth());
    }

    /**
     * Test of getSpells method, of class Player.
     */
    @Test
    public void testGetSpells() {
        List<Spell> spells = softwareInjector.getSpells();
        List<Spell> otherSpells = player1.getSpells();
        assertEquals("Not the same ammount of spells", spells.size(), otherSpells.size());
        
        List<Spell> noSpells = player2.getSpells();
        assertNull("player2 somehow has spells", noSpells);
    }

    /**
     * Test of buildDefense method, of class Player.
     */
    @Test
    public void testBuildDefense() throws Exception {

    }

    /**
     * Test of getModules method, of class Player.
     */
    @Test
    public void testGetModules() {
        assertNull(player2.getModules());
        assertEquals(player1.getModules().size(), 2);
    }

    /**
     * Test of removeBitcoins method, of class Player.
     */
    @Test
    public void testRemoveBitcoins() throws Exception {
        player2.removeBitcoins(1);
        assertEquals("error removeBitcoins", 0, player2.getBitcoins());
    }

    /**
     * Test of getBaseLocation method, of class Player.
     */
    @Test
    public void testGetBaseLocation() {
        assertEquals("error get baseLocation", point1, player1.getBaseLocation());
    }
    
}
