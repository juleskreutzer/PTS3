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
import hackattackfx.templates.BitCoinMinerTemplate;
import hackattackfx.templates.CPUUpgradeTemplate;
import hackattackfx.templates.DefenseTemplate;
import hackattackfx.templates.MinionTemplate;
import hackattackfx.templates.SoftwareInjectorTemplate;
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
    

    DefenseTemplate templateDfBc;
    DefenseTemplate templateDfSc;
    DefenseTemplate templateDfSn;
    DefenseTemplate templateDfMu;
    Defense defense1;
    Defense defense2;
    Defense defense3;
    Defense defense4;
    
    
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

        templateDfBc = Data.DEFAULT_MODULE_DEFENSE_BOTTLECAP_1;
        templateDfSc = Data.DEFAULT_MODULE_DEFENSE_SCALE_1;
        templateDfSn = Data.DEFAULT_MODULE_DEFENSE_SNIPER_1;
        templateDfMu = Data.DEFAULT_MODULE_DEFENSE_MUSCLE_1;
        
        defense1 = new Defense(templateDfBc, point1, 50, 50);
        defense2 = new Defense(templateDfSc, point1, 50, 50);
        defense3 = new Defense(templateDfSn, point1, 50, 50);
        defense4 = new Defense(templateDfMu, point1, 50, 50);
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
        assertEquals(defense1.getDefenceTypeString(), templateDfBc.getDefenseType().toString());
        assertEquals(defense2.getDefenceTypeString(), templateDfSc.getDefenseType().toString());
        assertEquals(defense3.getDefenceTypeString(), templateDfSn.getDefenseType().toString());
        assertEquals(defense4.getDefenceTypeString(), templateDfMu.getDefenseType().toString());
    }

    /**
     * Test of getEffectString method, of class Defense.
     */
    @Test
    public void testGetSetEffectString() {
        defense1.setEffect(Effect.SLOWED);
        defense1.setEffect(Effect.POISENED);
        defense1.setEffect(Effect.SPLASH);
        defense1.setEffect(Effect.DECRYPTED);
        
        assertEquals(defense1.getEffectString(), Effect.SLOWED.toString());
        assertEquals(defense2.getEffectString(), Effect.POISENED.toString());
        assertEquals(defense3.getEffectString(), Effect.SPLASH.toString());
        assertEquals(defense4.getEffectString(), Effect.DECRYPTED.toString());    
    }

    /**
     * Test of getDefenceType method, of class Defense.
     */
    @Test
    public void testGetDefenceType() {
        assertEquals(defense1.getDefenceType(), DefenseType.CHEAP);
    }

    /**
     * Test of getEffect method, of class Defense.
     */
    @Test
    public void testGetSetEffect() {
        defense1.setEffect(Effect.SLOWED);
        assertEquals(defense1.getEffect(), Effect.SLOWED);
    }

    /**
     * Test of getDamage method, of class Defense.
     */
    @Test
    public void testGetSetDamage() {
        defense1.setDamage(1);
        assertEquals(defense1.getDamage(), 1, 0);
    }

    /**
     * Test of getRange method, of class Defense.
     */
    @Test
    public void testGetSetRange() {
        defense1.setRange(1);
        assertEquals(defense1.getRange(), 1, 0);
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
        //IDK how to test
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
