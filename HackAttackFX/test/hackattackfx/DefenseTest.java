/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.enums.DefenseType;
import hackattackfx.enums.Effect;
import hackattackfx.exceptions.InvalidDefenseTypeException;
import hackattackfx.exceptions.InvalidEffectException;
import hackattackfx.exceptions.InvalidMinionTypeException;
import hackattackfx.exceptions.InvalidModuleEnumException;
import hackattackfx.exceptions.InvalidSpellNameException;
import hackattackfx.exceptions.NoUpgradeAllowedException;
import hackattackfx.templates.DefenseTemplate;
import hackattackfx.templates.MinionTemplate;
import java.awt.Point;
import java.io.IOException;
import java.util.HashSet;
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
 * @author Bart van Keersop
 */
public class DefenseTest {
    Data data;
    Player enemyPlayer;
    DefenseTemplate defenseTemplate;
    DefenseTemplate defenseTemplateLv2;
    DefenseTemplate defenseTemplateLv3;
    MinionTemplate minionTemplate;
    Minion minionTarget;
    Defense defense;
    Defense defenseLv2;
    Defense defenseLv3;
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
            data = new Data(new Data.UpdateProgress() {
                        @Override
                        public void update(double value) {
                    }
                });
            
        enemyPlayer = new Player();
        defenseTemplate = Data.DEFAULT_MODULE_DEFENSE_BOTTLECAP_1;
        defenseTemplateLv2 = Data.DEFAULT_MODULE_DEFENSE_BOTTLECAP_2;
        defenseTemplateLv3 = Data.DEFAULT_MODULE_DEFENSE_BOTTLECAP_3;
        minionTemplate = Data.DEFAULT_BYTE;
        minionTarget = new Minion(minionTemplate, 0, enemyPlayer);
        point1 = new Point(100, 100);
        point2 = new Point(999, 999);
        defense = new Defense(defenseTemplate, point1, 50, 50);
        defenseLv2 = new Defense(defenseTemplateLv2, point1, 50, 50);
        defenseLv3 = new Defense(defenseTemplateLv3, point1, 50, 50);
        }
        catch (IOException | InvalidMinionTypeException | InvalidSpellNameException | InvalidDefenseTypeException | InvalidEffectException | InvalidModuleEnumException e){
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
     * @throws hackattackfx.exceptions.NoUpgradeAllowedException
     */
    @Test
    public void testUpgradeSucces() throws NoUpgradeAllowedException {
        defense.upgrade();
        defenseLv2.upgrade();
    }

      /**
     * Test of upgrade method, of class Defense.
     * @throws hackattackfx.exceptions.NoUpgradeAllowedException
     */
    @Test
    public void testUpgradeFail() {
        try{
        defenseLv3.upgrade();
        } catch (NoUpgradeAllowedException ex) {
            Logger.getLogger(DefenseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        //In range
        minionTarget.setPosition(point1);
        assertTrue(defense.targetInRange(minionTarget));
        
        //Out of range
        minionTarget.setPosition(point2);
        assertFalse(defense.targetInRange(minionTarget));
    }

    /**
     * Test of hasTarget method, of class Defense.
     */
    @Test
    public void testHasTarget() {
        //Has target
        defense.setTarget(minionTarget);
        assertTrue(defense.hasTarget());
        
        //No target
        defense.setTarget(null);
        assertFalse(defense.hasTarget());
    }

    /**
     * Test of fire method, of class Defense.
     */
    @Test
    public void testFire() {
       //Damage minion
       defense.setDamage(1);
       minionTarget.setHealth(100);
       defense.fire(minionTarget);
       assertEquals("Fire didn't do one damage", 99, minionTarget.getHealth(), 0);
       
       //Kill minion
       defense.setDamage(100);
       minionTarget.setHealth(1);
       defense.fire(minionTarget);
       assertNull("Fire didn't kill the minion", minionTarget);
    }
    
}
