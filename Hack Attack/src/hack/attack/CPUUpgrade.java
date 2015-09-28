/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack;

import hack.attack.enums.ModuleName;
import java.awt.Point;

/**
 *
 * @author juleskreutzer, Jasper Rouwhorst
 */
public class CPUUpgrade extends Module {
    private double minionBonusMultiplier;
    
    public CPUUpgrade(Point position, int width, int height, double cost, int level, ModuleName name, double minionBonusMultiplier)
    {
        super(cost,position, width, height, name, level);
        this.minionBonusMultiplier = minionBonusMultiplier;
    }
    
    public boolean upgrade(){
        return false;
    }
    
    
}
