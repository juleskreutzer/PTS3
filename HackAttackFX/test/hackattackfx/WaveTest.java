/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.exceptions.InvalidDefenseTypeException;
import hackattackfx.exceptions.InvalidEffectException;
import hackattackfx.exceptions.InvalidMinionTypeException;
import hackattackfx.exceptions.InvalidSpellNameException;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
public class WaveTest {
    Data data;
    Wave wave1;
    Wave wave2;
    Player player1;
    Point point1;

    public WaveTest(){
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws InvalidMinionTypeException, IOException, InvalidSpellNameException, InvalidDefenseTypeException, InvalidEffectException {
        data = new Data(new Data.UpdateProgress() {
                        @Override
                        public void update(double value) {
                    }
                });
         
        point1 = new Point(100, 100);
        player1 = new Player(1, "Player1", 1, point1);
        wave1 = new Wave(1, 1, player1, 1, 0, 0, 0, 0, 0);
        wave2 = new Wave(1, 1, player1, 0, 0, 0, 0, 0, 0);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testConstructor(){
        Wave waveTest = new Wave(1, 1, player1, 1, 1, 1, 1, 1, 1);
        assertEquals(waveTest.minionsAsList().size(), 6);
    }
    
    /**
     * Test of startWave method, of class Wave.
     */
    @Test
    public void testStartWave() {
        //IDK how to test
    }

    /**
     * Test of minions method, of class Wave.
     */
    @Test
    public void testMinions() {
        int counter = 0;
        Iterator <Minion> minions = wave1.minions();
         while (minions.hasNext()) {
            minions.next();
            counter++;
        }
        assertEquals(counter, 1);   
    }

    /**
     * Test of minionsAsList method, of class Wave.
     */
    @Test
    public void testMinionsAsList() {
        assertEquals(wave1.minionsAsList().size(), 1);
    }

    /**
     * Test of waveActive method, of class Wave.
     */
    @Test
    public void testWaveActive() {
        ArrayList<Minion> wave = wave1.minionsAsList();
        assertTrue(wave1.waveActive());
        assertFalse(wave2.waveActive());
    }

    /**
     * Test of removeMinion method, of class Wave.
     */
    @Test
    public void testRemoveMinionDamage() {
        //To Test this method, make Wave.removeMinion public.
        /*
        Minion minion1 = wave1.minionsAsList().get(0);
        double damage = minion1.getDamage();
        double healthBefore = player1.getHealth();
        wave1.removeMinion(minion1, true);
        assertTrue(wave1.minionsAsList().isEmpty());
        assertTrue(player1.getHealth() == healthBefore - damage);
        */
    }
    
    /**
     * Test of removeMinion method, of class Wave.
     */
    @Test
    public void testRemoveMinionMoney() {
        //To Test this method, make Wave.removeMinion public.
        /*
        Minion minion1 = wave1.minionsAsList().get(0);
        double worth = minion1.getReward();
        double money = player1.getBitcoins();
        wave1.removeMinion(minion1, false);
        assertTrue(wave1.minionsAsList().isEmpty());
        assertTrue(player1.getBitcoins() == money + worth);
        */
    }

    /**
     * Test of getWaveNr method, of class Wave.
     */
    @Test
    public void testGetWaveNr() {
        assertEquals(wave1.getWaveNr(), 1);
    }
    
}
