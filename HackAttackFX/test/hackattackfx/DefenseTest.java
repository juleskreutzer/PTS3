/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.enums.DefenseType;
import hackattackfx.enums.Effect;
import hackattackfx.templates.DefenseTemplate;
import hackattackfx.templates.MinionTemplate;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;
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
public class DefenseTest {
    Data data;
    DefenseTemplate defenseTemplate;
    MinionTemplate minionTemplate;
    Minion minionTarget;
    Defense defense;
    Point point1;
    Point point2;
    
    
    public DefenseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        try{
        defenseTemplate = Data.DEFAULT_MODULE_DEFENSE_BOTTLECAP_1;
        minionTemplate = Data.DEFAULT_BYTE;
        minionTarget = new Minion(minionTemplate, 0);
        point1 = new Point(100, 100);
        point2 = new Point(99999, 99999);
        defense = new Defense(defenseTemplate, point1, 50, 50);
        }
        catch (Exception e){
            System.out.printf("Set up went wrong: " + e.toString());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of activate method, of class Defense.
     */
    @Test
    public void testActivate1() {
        defense.activate();
    }
    
    @Test
    public void testActivate2() {
        //In range
        minionTarget.setPosition(point1);
        defense.activate();
    }

    @Test
    public void testActivate3() {
        //Out of range
        minionTarget.setPosition(point2);
        defense.setTarget(minionTarget);
        defense.activate();
    }
    /**
     * Test of deactivate method, of class Defense.
     */
    @Test
    public void testDeactivate() {
        defense.activate();
        defense.deactivate();
        
        try {
        defense.deactivate();
        }
        catch (Exception e) {
        System.out.printf("Defense isn't activated yet: " + e.toString());
        }
    }

    /**
     * Test of getDefenceTypeString method, of class Defense.
     */
    @Test
    public void testGetDefenceTypeString() {

    }

    /**
     * Test of getEffectString method, of class Defense.
     */
    @Test
    public void testGetEffectString() {

    }

    /**
     * Test of getDefenceType method, of class Defense.
     */
    @Test
    public void testGetDefenceType() {

    }

    /**
     * Test of getEffect method, of class Defense.
     */
    @Test
    public void testGetEffect() {

    }

    /**
     * Test of setEffect method, of class Defense.
     */
    @Test
    public void testSetEffect() {

    }

    /**
     * Test of getDamage method, of class Defense.
     */
    @Test
    public void testGetDamage() {

    }

    /**
     * Test of setDamage method, of class Defense.
     */
    @Test
    public void testSetDamage() {

    }

    /**
     * Test of getRange method, of class Defense.
     */
    @Test
    public void testGetRange() {

    }

    /**
     * Test of setRange method, of class Defense.
     */
    @Test
    public void testSetRange() {

    }

    /**
     * Test of upgrade method, of class Defense.
     */
    @Test
    public void testUpgrade() throws Exception {

    }

    /**
     * Test of findTarget method, of class Defense.
     */
    @Test
    public void testFindTarget() {

    }

    /**
     * Test of targetInRange method, of class Defense.
     */
    @Test
    public void testTargetInRange() {

    }

    /**
     * Test of hasTarget method, of class Defense.
     */
    @Test
    public void testHasTarget() {

    }

    /**
     * Test of fire method, of class Defense.
     */
    @Test
    public void testFire() {
       defense.setDamage(1);
       minionTarget.setHealth(100);
       defense.fire(minionTarget);
       assertEquals("Fire didn't do one damage", 99, minionTarget.getHealth());
       
       defense.setDamage(100);
       minionTarget.setHealth(1);
       defense.fire(minionTarget);
       assertEquals("Fire didn't kill the minion", 99, minionTarget.getHealth());
        
    }
    
}
