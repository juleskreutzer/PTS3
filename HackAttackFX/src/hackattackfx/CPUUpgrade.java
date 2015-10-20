/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.exceptions.InvalidModuleEnumException;
import hackattackfx.enums.ModuleName;
import java.awt.Point;
import hackattackfx.exceptions.*;
import hackattackfx.templates.*;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * The CPUUpgrade is a module that's used to increase the minions hp and damage.
 * The higher this module is upgraded, the higher the multiplier.
 * @author juleskreutzer, Jasper Rouwhorst
 */
public class CPUUpgrade extends Module {
    private double minionBonusMultiplier;
    
    /**
     * Constructor for CPUUpgrade based on the CPUUpgradeTemplate
     * @param template Instance of the CPUUpgradeTemplate that has been constructed in the data class
     * @param position position of the module on the map
     * @param width width of the module
     * @param height height of the module
     * @throws InvalidModuleEnumException when this is not a valid {@link ModuleName}
     */
    public CPUUpgrade(CPUUpgradeTemplate template, Point position, int width, int height) throws InvalidModuleEnumException
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
    public boolean upgrade(Iterator<Minion> minions) throws NoUpgradeAllowedException{
        CPUUpgradeTemplate newCPU;
        if(this.level == 1)
        {
           newCPU = Data.DEFAULT_MODULE_CPUUPGRADE_2;
           this.level = newCPU.getLevel();
           super.setLevel(newCPU.getLevel());
           this.minionBonusMultiplier = newCPU.getMinionBonusMultiplier();
           
           // Increase the stats of all minions working for the player
           while(minions.hasNext())
           {
               Minion m = minions.next();
               m.applyMultiplier(this.minionBonusMultiplier);
           }
           return true;
        }
        else if(this.level == 2)
        {
            newCPU = Data.DEFAULT_MODULE_CPUUPGRADE_3;
            this.level = newCPU.getLevel();
            super.setLevel(newCPU.getLevel());
            this.minionBonusMultiplier = newCPU.getMinionBonusMultiplier();
            
            // Increase the stats of all minions working for the player
            while(minions.hasNext())
            {
                Minion m = minions.next();
                m.applyMultiplier(this.minionBonusMultiplier);
            }
            return true;
        }
        else if(this.level == 3)
        {
            throw new NoUpgradeAllowedException("This CPU Upgrade has already reached it's maximal level. An upgrade is not available.");
        }
        else
        {
            throw new NoUpgradeAllowedException("Something is wrong, we can't upgrade your CPU Upgrade at this time.");
        }
        
    }
    
    public void operation()
    {
        
    }
    
    
    
}
