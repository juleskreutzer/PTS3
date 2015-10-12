/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.enums.ModuleName;
import java.awt.Point;
import hackattackfx.exceptions.*;
import hackattackfx.templates.*;
/**
 * The CPUUpgrade is a module that's used to increase the minions hp and damage.
 * The higher this module is upgraded, the higher the multiplier.
 * @author juleskreutzer, Jasper Rouwhorst
 */
public class CPUUpgrade extends Module {
    private double minionBonusMultiplier;
    
    /**
     * 
     * @param position The position this module should be builded(upleft corner), must fall within the X and Y values of the singleton class Map.
     * @param width The width of this module.
     * @param height The height of this module.
     * @param cost The costs to build this module(must be > 0).
     * @param level The initial level of this module(must be > 0).
     * @param name The {@link ModuleName} of this module.
     * @param minionBonusMultiplier The multiplier this module gives to the minions.
     */
    public CPUUpgrade(Point position, int width, int height, double cost, int level)
    {
        super(cost,position, width, height, ModuleName.CPU_UPGRADE, level);
        minionBonusMultiplier = level * 10;
    }
    
    /**
     * Constructor for CPUUpgrade based on the CPUUpgradeTemplate
     * @param template Instance of the CPUUpgradeTemplate that has been constructed in the data class
     * @param position position of the module on the map
     * @param width width of the module
     * @param height height of the module
     */
    public CPUUpgrade(CPUUpgradeTemplate template, Point position, int width, int height)
    {
        super(template.getCost(), position, width, height, template.getModuleName(), template.getLevel());
        if(template.getModuleName() != ModuleName.CPU_UPGRADE)
        {
            throw new InvalidModuleEnumException();
        }
        
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
