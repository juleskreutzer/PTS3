/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack;

import hack.attack.enums.ModuleName;
import java.awt.Point;
import hack.attack.exceptions.*;
/**
 * The CPUUpgrade is a module that's used to increase the minions hp and damage.
 * The higher this module is upgraded, the higher the multiplier.
 * @author juleskreutzer, Jasper Rouwhorst
 */
public class CPUUpgrade extends Module {
    private double minionBonusMultiplier;
    
    /**
     * 
     * @param position The position this module is build(upleft corner).
     * @param width The width of this module.
     * @param height The height of this module.
     * @param cost The costs to build this module(cannot be < 1).
     * @param level The initial level of this module(cannot be < 1).
     * @param name The {@link ModuleName} of this module.
     * @param minionBonusMultiplier The multiplier this module gives to the minions.
     */
    public CPUUpgrade(Point position, int width, int height, double cost, int level, ModuleName name)
    {
        super(cost,position, width, height, name, level);
        minionBonusMultiplier = level * 10;
    }
   
    /**
     * Upgrades this module
     * @return Whether the module was successfully upgraded.
     */
    public boolean upgrade(){
        level++;
        minionBonusMultiplier = level*10;
        return true;
    }
    
    
}
