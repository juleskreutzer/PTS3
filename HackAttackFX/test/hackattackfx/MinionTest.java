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
    Data data;
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
            data = new Data(new Data.UpdateProgress() {
                        @Override
                        public void update(double value) {
                    }
                });
            
            minionTemplate = Data.DEFAULT_BYTE;
            point1 = new Point(100, 100);
            enemyPlayer = new Player(100, "enemyPlayer", 0, point1);
            minion1 = new Minion(minionTemplate, 1, enemyPlayer);
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
        minion1.setPosition(point1);
        Point minionStart = minion1.getPosition();
        minion1.move(1);
        Point minionAfterMove = minion1.getPosition();
        assertFalse(minionStart == minionAfterMove);
    }

    /**
     * Test of getHealth method, of class Minion.
     */
    @Test
    public void testGetSetHealth() {
        minion1.setHealth(1);
        assertEquals(minion1.getHealth(), 1, 0);
    }

    /**
     * Test of getPosition method, of class Minion.
     */
    @Test
    public void testGetSetPosition() {
        minion1.setPosition(point1);
        assertEquals(minion1.getPosition(), point1);
    }

    /**
     * Test of getSpeed method, of class Minion.
     */
    @Test
    public void testGetSetSpeed() {
        minion1.setSpeed(1);
        assertEquals(minion1.getSpeed(), 1, 0);
    }

    /**
     * Test of getDamage method, of class Minion.
     */
    @Test
    public void testGetSetDamage() {
        minion1.set
    }

    /**
     * Test of getEncrypted method, of class Minion.
     */
    @Test
    public void testGetEncrypted() {

    }

    /**
     * Test of setEncrypted method, of class Minion.
     */
    @Test
    public void testSetEncrypted() {
    
    }

    /**
     * Test of getReward method, of class Minion.
     */
    @Test
    public void testGetReward() {

    }

    /**
     * Test of attack method, of class Minion.
     */
    @Test
    public void testAttack() {
     double healthBefore = enemyPlayer.getHealth();
     minion1.attack(enemyPlayer);
     double healthAfter = enemyPlayer.getHealth();
     assertFalse(healthBefore == healthAfter);
    }

    /**
     * Test of getMinionType method, of class Minion.
     */
    @Test
    public void testGetMinionType() {

    }

    /**
     * Test of receiveDamage method, of class Minion.
     */
    @Test
    public void testReceiveDamage() {
   
    }
    
}
