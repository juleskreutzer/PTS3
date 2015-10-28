/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.BitcoinMiner.OnMineComplete;
import hackattackfx.exceptions.InvalidDefenseTypeException;
import hackattackfx.exceptions.InvalidEffectException;
import hackattackfx.exceptions.InvalidMinionTypeException;
import hackattackfx.exceptions.InvalidModuleEnumException;
import hackattackfx.exceptions.InvalidSpellNameException;
import hackattackfx.exceptions.NoUpgradeAllowedException;
import hackattackfx.exceptions.NotEnoughBitcoinsException;
import hackattackfx.templates.BitCoinMinerTemplate;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
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
public class BitcoinMinerTest {
    Data data;
    Player player1;
    Player player2;
    BitCoinMinerTemplate template1;
    BitCoinMinerTemplate template2;
    BitCoinMinerTemplate template3;
    Point point1;
    Point point2;
    Point point3;
    Point point4;
    Point point5;
    Point point6;
    BitcoinMiner bitcoinMinerLv1;
    BitcoinMiner bitcoinMinerLv2;
    BitcoinMiner bitcoinMinerLv3;
    Timer timer;
    
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
        try {
            data = new Data(new Data.UpdateProgress() {
                @Override
                public void update(double value) {
                }
            });
        } catch (IOException | InvalidMinionTypeException | InvalidSpellNameException | InvalidDefenseTypeException | InvalidEffectException ex) {
            Logger.getLogger(BitcoinMinerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        timer = new Timer();
        
        //Store the templates for each level.
        template1 = Data.DEFAULT_MODULE_BITCOINMINER_1;
        template2 = Data.DEFAULT_MODULE_BITCOINMINER_2;
        template3 = Data.DEFAULT_MODULE_BITCOINMINER_3;
        point1 = new Point(100, 100);
        point2 = new Point(200, 200);
        point3 = new Point(300, 300);
        point4 = new Point(400, 400);
        point5 = new Point(500, 500);
        point6 = new Point(600, 600);
        
        //Create an instance of the three bitcoinminers.
        try {
            bitcoinMinerLv1 = new BitcoinMiner(template1, point1, 50, 50);
            bitcoinMinerLv2 = new BitcoinMiner(template2, point2, 50, 50);
            bitcoinMinerLv3 = new BitcoinMiner(template3, point3, 50, 50);
            
            player1 = new Player(1, "player1", 0, point4);
            player2 = new Player(1, "player2", 0, point5);
            player1.buildBitcoinMiner(bitcoinMinerLv1);
            
        } catch (InvalidModuleEnumException | NotEnoughBitcoinsException ex) {
            Logger.getLogger(BitcoinMinerTest.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Setup went wrong!");
        }
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    public void testConstructor(){
        //Cannot test for invalid module name, this data is stored in a template loaded from a database.
        try {
            BitcoinMiner bitcoinminer1 = new BitcoinMiner(template1, point6, 50, 50);
        } catch (InvalidModuleEnumException ex) {
            Logger.getLogger(BitcoinMinerTest.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("bitCoinMiner1 could not be created.");
        }
    }
    /**
     * Test of upgrade method, of class BitcoinMiner.
     * @throws hackattackfx.exceptions.NoUpgradeAllowedException
     */
    @Test
    public void testUpgrade() throws NoUpgradeAllowedException {
        bitcoinMinerLv1.upgrade();
        assertEquals("bitcoinMinerLv1 has not been upgraded",bitcoinMinerLv1.getLevel(), 2);
        bitcoinMinerLv2.upgrade();
        assertEquals("bitcoinMinerLv2 has not been upgraded", bitcoinMinerLv2.getLevel(), 3);
    }
    
    public void NoUpgradeAllowedException() {
        try {
            bitcoinMinerLv3.upgrade();
        } catch (NoUpgradeAllowedException ex) {
            Logger.getLogger(BitcoinMinerTest.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("bitCoinMiner3 can't be upgraded.");
        }
    }

    /**
     * Test of activate method, of class BitcoinMiner.
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testActivate() throws InterruptedException {
        bitcoinMinerLv1.activate();
        
        timer.schedule(new TimerTask(){
        @Override
        public void run(){
        assertEquals("BitcoinMinerLv1 geeft niet het juiste aantal bitcoins", player1.getBitcoins(), bitcoinMinerLv1.getValuePerSecond(), 0);
        }},4000);
    }

    /**
     * Test of setOnMineListener method, of class BitcoinMiner.
     * @throws hackattackfx.exceptions.NotEnoughBitcoinsException
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testSetOnMineListener() throws NotEnoughBitcoinsException, InterruptedException {
        player2.setBitcoins(bitcoinMinerLv1.cost);
        player2.buildBitcoinMiner(bitcoinMinerLv1);
                
        timer.schedule(new TimerTask(){
        @Override
        public void run(){
        assertTrue(player2.getBitcoins() > 1);
        }},4000);
    }
    
}
