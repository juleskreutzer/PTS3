/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.GameEngine.OnExecuteTick;
import hackattackfx.enums.MinionType;
import hackattackfx.templates.MinionTemplate;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
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
public class MinionTest {
    MinionTemplate minionTemplate;
    Player enemyPlayer;
    Point point1;
    Minion minion1;
    Wave wave1;
    Timer timer;
    
    public MinionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        try
        {
            minionTemplate = Data.DEFAULT_KILOBYTE;
            point1 = new Point(100, 100);
            enemyPlayer = new Player(100, "enemyPlayer", 0, point1);
            minion1 = new Minion(minionTemplate, 0, enemyPlayer );
            wave1 = new Wave(1, 1, enemyPlayer, 1, 0, 0, 0, 0, 0);
        }
        catch (Exception e)
        {
            System.out.printf("Setup went wrong");
        }
            
    }
    
    @After
    public void tearDown() {
    }

    public void testContructor() {
        
    }
    
    /**
     * Test of activate method, of class Minion.
     */
    @Test
    public void testActivate() {
        //Check if activate works for minion.Move()
        ArrayList<Minion> minionList = wave1.minionsAsList();
        Minion minionTest = minionList.get(0);
        minionTest.setHealth(1);
        
        wave1.startWave();
        Point minionStart = minion1.getPosition();
        timer.schedule(new TimerTask(){
        @Override
        public void run(){
            Point minionLocation;
            minionLocation = minionTest.getPosition();
            assertTrue(minionStart != minionLocation);
        }},4000);
        
        //Check if activate works for minionHeartBeat
        timer.schedule(new TimerTask(){
        @Override
        public void run(){
            minionTest.receiveDamage(100);
            assertTrue(wave1.minionsAsList().isEmpty());
        }},4000);
    }

    /**
     * Test of deactivate method, of class Minion.
     */
    @Test
    public void testDeactivate() {
        //Check if deactivate works for minion.Move()
        //Minion is in wave but shouldn't more because he's not a listener.
        ArrayList<Minion> minionList = wave1.minionsAsList();
        Minion minionTest = minionList.get(0);
        minionTest.deactivate();
        
        wave1.startWave();
        Point minionStart = minion1.getPosition();
        timer.schedule(new TimerTask(){
        @Override
        public void run(){
            Point minionLocation;
            minionLocation = minionTest.getPosition();
            assertTrue(minionStart == minionLocation);
        }},4000);
        
        //Check if deactivate works for minionHeartBeat
        //Minion "dies" but isn't a listener so shouldn't get removed.
        timer.schedule(new TimerTask(){
        @Override
        public void run(){
            minionTest.receiveDamage(100);
            assertFalse(wave1.minionsAsList().isEmpty());
        }},4000);
        
    }

    /**
     * Test of move method, of class Minion.
     */
    @Test
    public void testMove() {
        System.out.println("move");
        double elapsedtime = 0.0;
        Minion instance = null;
        instance.move(elapsedtime);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCollision method, of class Minion.
     */
    @Test
    public void testGetCollision() {
        System.out.println("getCollision");
        Minion instance = null;
        Object expResult = null;
        Object result = instance.getCollision();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHealth method, of class Minion.
     */
    @Test
    public void testGetHealth() {
        System.out.println("getHealth");
        Minion instance = null;
        double expResult = 0.0;
        double result = instance.getHealth();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setHealth method, of class Minion.
     */
    @Test
    public void testSetHealth() {
        System.out.println("setHealth");
        double health = 0.0;
        Minion instance = null;
        instance.setHealth(health);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPosition method, of class Minion.
     */
    @Test
    public void testGetPosition() {
        System.out.println("getPosition");
        Minion instance = null;
        Point expResult = null;
        Point result = instance.getPosition();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPosition method, of class Minion.
     */
    @Test
    public void testSetPosition() {
        System.out.println("setPosition");
        Point position = null;
        Minion instance = null;
        instance.setPosition(position);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSpeed method, of class Minion.
     */
    @Test
    public void testGetSpeed() {
        System.out.println("getSpeed");
        Minion instance = null;
        double expResult = 0.0;
        double result = instance.getSpeed();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSpeed method, of class Minion.
     */
    @Test
    public void testSetSpeed() {
        System.out.println("setSpeed");
        double speed = 0.0;
        Minion instance = null;
        instance.setSpeed(speed);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDamage method, of class Minion.
     */
    @Test
    public void testGetDamage() {
        System.out.println("getDamage");
        Minion instance = null;
        double expResult = 0.0;
        double result = instance.getDamage();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEncrypted method, of class Minion.
     */
    @Test
    public void testGetEncrypted() {
        System.out.println("getEncrypted");
        Minion instance = null;
        boolean expResult = false;
        boolean result = instance.getEncrypted();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEncrypted method, of class Minion.
     */
    @Test
    public void testSetEncrypted() {
        System.out.println("setEncrypted");
        boolean encrypted = false;
        Minion instance = null;
        instance.setEncrypted(encrypted);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReward method, of class Minion.
     */
    @Test
    public void testGetReward() {
        System.out.println("getReward");
        Minion instance = null;
        double expResult = 0.0;
        double result = instance.getReward();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of attack method, of class Minion.
     */
    @Test
    public void testAttack() {
        System.out.println("attack");
        Player player = null;
        Minion instance = null;
        instance.attack(player);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMinionType method, of class Minion.
     */
    @Test
    public void testGetMinionType() {
        System.out.println("getMinionType");
        Minion instance = null;
        MinionType expResult = null;
        MinionType result = instance.getMinionType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of receiveDamage method, of class Minion.
     */
    @Test
    public void testReceiveDamage() {
        System.out.println("receiveDamage");
        double damage = 0.0;
        Minion instance = null;
        instance.receiveDamage(damage);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
