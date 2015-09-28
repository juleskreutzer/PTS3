/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack;

import java.awt.Point;

/**
 *
 * @author juleskreutzer
 */
public class CPUUpgrade extends Module {
    private double minionBonusMultiplier;
    
    public CPUUpgrade(Point position, double cost, int level, ModuleName name, double minionBonusMultiplier)
    {
        super(cost,position, name, level);
        this.minionBonusMultiplier = minionBonusMultiplier;
    }
    
    
}
