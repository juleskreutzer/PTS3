/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.client.templates;

import hack.attack.rmi.ModuleTemplate;
import hack.attack.rmi.ModuleName;

/**
 *
 * @author juleskreutzer
 */
public class CPUUpgradeTemplate extends ModuleTemplate {

    private int minionBonusMultiplier;
    public CPUUpgradeTemplate(double cost, int level, ModuleName name, int multiplier, String desc) {
        super(cost, level, name, desc);
        this.minionBonusMultiplier = multiplier;
    }
    
    public int getMinionBonusMultiplier()
    {
        return this.minionBonusMultiplier;
    }
    
}
